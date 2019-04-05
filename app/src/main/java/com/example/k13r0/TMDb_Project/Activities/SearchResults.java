/*
 *
 * Author		: Kieron Higgs
 * Date			: Apr. 5th, 2019
 * Project		: Assignment 2
 * File			: SearchResults.java
 * Description	: Contains the code which facilitates displaying a list of search results.
 */

package com.example.k13r0.TMDb_Project.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.k13r0.TMDb_Project.Utilities.MovieAdapter;
import com.example.k13r0.TMDb_Project.Utilities.Session;
import com.example.k13r0.TMDb_Project.Utilities.Movie;
import com.example.k13r0.TMDb_Project.R;
import java.util.ArrayList;

/*
 * Class		: SearchResults
 * Description	: This class is used to display the results of a search for a movie.
 */
public class SearchResults extends AppCompatActivity
{
    private Context context;
    private RequestQueue requestQueue;
    private Session guestSession;
    private ListView resultsList;
    private ArrayList<Movie> resultsArray;
    private MovieAdapter movieAdapter;

    /*
     * Function		: onCreate
     * Description	: Displays the results of a search in a ListView using the MovieAdapter.
     * Parameters	: Bundle savedInstanceState
     * Returns		: N/A
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        context = this;
        requestQueue = Volley.newRequestQueue(this);
        guestSession = new Session(context);

        resultsArray = guestSession.GetSearchResults(context);
        movieAdapter = new MovieAdapter(context, R.layout.list_row, resultsArray);

        resultsList = findViewById(android.R.id.list);
        resultsList.setAdapter(movieAdapter);
    }
}

