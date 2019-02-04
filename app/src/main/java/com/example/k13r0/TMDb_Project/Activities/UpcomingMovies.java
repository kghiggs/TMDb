/*
 *
 * Author		: Kieron Higgs
 * Date			: Jan. 24, 2019
 * Project		: Assignment 1
 * File			: UpcomingMovies.java
 * Description	: Contains the code used to display upcoming movies in a ListView with text and images.
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
 * Class		: Upcoming Movies
 * Description	: This class is used to query the database for upcoming movies and display them.
 */
public class UpcomingMovies extends AppCompatActivity
{
    private Context context;
    private RequestQueue requestQueue;
    private Session guestSession;

    private ListView upcomingList;
    private ArrayList<Movie> upcomingArray;
    private MovieAdapter movieAdapter;

    /*
     * Function		: onCreate
     * Description	: Requests a list of new and upcoming movies from the database and displays them in a ListView.
     * Parameters	: Bundle savedInstanceState
     * Returns		: N/A
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
        context = this;
        requestQueue = Volley.newRequestQueue(this);
        guestSession = new Session(context);

        Movie.RetrieveUpcomingMovies(guestSession, requestQueue, context);

        upcomingArray = guestSession.GetUpcomingMovies(context);
        movieAdapter = new MovieAdapter(context, R.layout.list_row, upcomingArray);

        upcomingList = findViewById(android.R.id.list);
        upcomingList.setAdapter(movieAdapter);
    }
}

