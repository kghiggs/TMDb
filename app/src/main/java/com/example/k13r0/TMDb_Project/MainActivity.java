package com.example.k13r0.TMDb_Project;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;

import com.example.k13r0.TMDb_Project.Activities.About;
import com.example.k13r0.TMDb_Project.Activities.SearchMovies;
import com.example.k13r0.TMDb_Project.Activities.UpcomingMovies;
import com.example.k13r0.TMDb_Project.Activities.RandomMovieTest;

import com.example.k13r0.TMDb_Project.Classes.Session;


public class MainActivity extends AppCompatActivity
{

    public Activity mainActivity;
    public Context mainContext;
    public View mainActivityView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;
        mainContext = getApplicationContext();
        mainActivityView = findViewById(R.id.mainActivity);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final Context context = getApplicationContext();
        final Session guestSession = new Session(context);

        guestSession.SetAPIKey("27fc45ada92ace137689b2c3ddb1c6d0");
        guestSession.GetGSID(requestQueue);




        // final Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/image.jpg");

        /*
        String MovieDetailURL= "https://api.themoviedb.org/3/search/movie?api_key=" + guestSession.GetAPIKey() + "&language=en-US&query=Blade%20Runner&page=1&include_adult=false";
        JsonObjectRequest requestMovieDetails = new JsonObjectRequest(Request.Method.GET, MovieDetailURL, null,

            new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        JSONArray results = response.getJSONArray("results");
                        Movie movie = new Movie(results, false);

                        Log.d("MovieDetailSUCCESS", response.toString());
                    }
                    catch(JSONException exception)
                    {
                        Log.d("MovieDetailJSONFAIL", exception.toString());
                    }
                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Log.d("MovieDetailConnErr", error.toString());
                }
            }
        );
        requestQueue.add(requestMovieDetails);
        */


        //TextView randomReleaseDate = findViewById(R.id.randomReleaseDate);
        //randomReleaseDate.setText(randomMovie.releaseDate.toString());


        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, SearchMovies.class));
            }
        });

        Button upcomingButton = findViewById(R.id.upcomingButton);
        upcomingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, UpcomingMovies.class));
            }
        });

        Button aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, About.class));
            }
        });

        Button randomMovieButton = findViewById(R.id.randomMovieButton);
        randomMovieButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, RandomMovieTest.class));
            }
        });
    }
}