/*
 *
 * Author		: Kieron Higgs
 * Date			: Jan. 25, 2019
 * Project		: Assignment 1
 * File			: MainActivity.java
 * Description	: The main page of the TMDb app. Displays buttons to test methods for finding movies based on different criteria.
 */

package com.example.k13r0.TMDb_Project.Activities;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.view.View;

import android.widget.Button;

import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;

import com.example.k13r0.TMDb_Project.Utilities.Movie;
import com.example.k13r0.TMDb_Project.Utilities.Session;
import com.example.k13r0.TMDb_Project.R;

/*
 * Class		: MainActivity
 * Description	: This class is used as a launchpad for trying out the prototype's database functions.
 */
public class MainActivity extends AppCompatActivity
{

    public Activity mainActivity;
    public Context mainContext;
    public View mainActivityView;

    /*
     * Function		: onCreate
     * Description	: Connects to TMDb and connects the XML with the code-behind for the MainActivity.
     * Parameters	: Bundle savedInstanceState
     * Returns		: N/A
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;
        mainContext = this;
        mainActivityView = findViewById(R.id.mainActivity);

        final Resources resources = getResources();


        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final Context context = getApplicationContext();
        final Session guestSession = new Session(context);

        guestSession.SetAPIKey(getString(R.string.api_key));
        guestSession.GetGSID(requestQueue, context);
        Movie.RetrieveUpcomingMovies(guestSession, requestQueue, mainContext);

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