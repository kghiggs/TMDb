/*
 *
 * Author		: Arie Kraayenbrink, Kieron Higgs
 * Date			: Jan. 31, 2019
 * Project		: Assignment 1
 * File			: SearchMovies.java
 * Description	: This file contains the SearchMovies class. It allows the user to search for a movie.
 */

package com.example.k13r0.TMDb_Project.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import com.example.k13r0.TMDb_Project.Classes.JsonObjectRequest;
import com.example.k13r0.TMDb_Project.Classes.Movie;
import com.example.k13r0.TMDb_Project.Classes.MovieAdapter;
import com.example.k13r0.TMDb_Project.Classes.Session;
import com.example.k13r0.TMDb_Project.MainActivity;
import com.example.k13r0.TMDb_Project.R;


import org.json.JSONObject;

import java.util.ArrayList;


/*
 * Class		: SearchMovies
 * Description	: This class is used to search for a specified movie.
 */
public class SearchMovies extends AppCompatActivity implements View.OnClickListener
{
    private Context context;
    private RequestQueue requestQueue;
    private Session guestSession;

    private EditText txtMovieSearch;
    private Button btnSearch;
    private TextView txtResults;
    private String query;

    private ListView resultsList;
    private ArrayList<Movie> resultsArray;
    private MovieAdapter movieAdapter;

    /*
     * Function		: onCreate
     * Description	: This function is called when the activity is created.
     * Parameters	: Bundle savedInstanceState
     * Returns		: N/A
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        context = getApplicationContext();
        guestSession = new Session(context);
        requestQueue = Volley.newRequestQueue(this);

        txtMovieSearch = findViewById(R.id.txtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        txtResults = findViewById(R.id.txtSearchResults);
        resultsList = findViewById(R.id.list);
        resultsArray = new ArrayList();
        txtMovieSearch.addTextChangedListener(searchTextWatcher);

        btnSearch.setOnClickListener(this);
    }



    /*
     * Function		: onCreateOptionsMenu
     * Description	:
     * Parameters	: Menu menu
     * Returns		: true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }
    /*
     * Function		: onOptionsItemSelected
     * Description	:
     * Parameters	: MenuItem item
     * Returns		: super.onOptionsItemSelected(item)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.back:
                goToMain();
                break;
            case R.id.info:
                goToAbout();

        }

        return super.onOptionsItemSelected(item);
    }



    private TextWatcher searchTextWatcher = new TextWatcher() {

        /*
         * Function		: beforeTextChanged
         * Description	:
         * Parameters	: CharSequence s, int start, int count, int after
         * Returns		: N/A
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }



        /*
         * Function		: onTextChanged
         * Description	:
         * Parameters	: CharSequence s, int start, int count, int after
         * Returns		: N/A
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String searchInput = txtMovieSearch.getText().toString().trim();

            btnSearch.setEnabled(!searchInput.isEmpty());
        }



        /*
         * Function		: afterTextChanged
         * Description	:
         * Parameters	: CharSequence s, int start, int count, int after
         * Returns		: N/A
         */
        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    /*
     * Function		: onClick
     * Description	:
     * Parameters	: View v
     * Returns		: N/A
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSearch:
                query = txtMovieSearch.getText().toString();
                String HTMLquery = query.replaceAll(" ", "%20");
                String LatestMovieURL = "https://api.themoviedb.org/3/search/movie?api_key=" + guestSession.GetAPIKey() + "&language=en-US&page=1" + "&query=" + HTMLquery + "&include_adult=false";

                JsonObjectRequest requestLatestMovie = new JsonObjectRequest(Request.Method.GET, LatestMovieURL, null,

                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject searchResults)
                            {
                                guestSession.SetSearchMoviesString(searchResults);
                                Movie.RetrieveSearchResults(guestSession, requestQueue, query);
                                startActivity(new Intent(SearchMovies.this, SearchResults.class));
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Log.d("SearchMoviesCONNERR", error.toString());
                            }
                        }
                );
                requestQueue.add(requestLatestMovie);
                break;
        }
    }



    /*
     * Function		: goToMain
     * Description	:
     * Parameters	: N/A
     * Returns		: N/A
     */
    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    /*
     * Function		: goToAbout
     * Description	:
     * Parameters	: N/A
     * Returns		: N/A
     */
    private void goToAbout() {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }
}
