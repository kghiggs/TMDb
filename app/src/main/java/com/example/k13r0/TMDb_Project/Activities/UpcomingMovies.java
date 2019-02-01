/*
 *
 * Author		: Kieron Higgs
 * Date			: Jan. 24, 2019
 * Project		: Assignment 1
 * File			: UpcomingMovies.java
 * Description	: Contains the code used to display upcoming movies in a ListView with text and images.
 * Credit		:
 */

package com.example.k13r0.TMDb_Project.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.k13r0.TMDb_Project.Classes.MovieAdapter;
import com.example.k13r0.TMDb_Project.Classes.Session;
import com.example.k13r0.TMDb_Project.Classes.Movie;

import java.util.ArrayList;

public class UpcomingMovies extends AppCompatActivity
{
    private Context context;
    private RequestQueue requestQueue;
    private Session guestSession;

    private ListView upcomingList;
    private ArrayList<Movie> upcomingArray;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
        context = getApplicationContext();
        requestQueue = Volley.newRequestQueue(this);
        guestSession = new Session(context);

        Movie.RetrieveUpcomingMovies(guestSession, requestQueue);

        upcomingArray = guestSession.GetUpcomingMovies();
        movieAdapter = new MovieAdapter(context, R.layout.upcoming_row, upcomingArray);

        upcomingList = findViewById(android.R.id.list);
        upcomingList.setAdapter(movieAdapter);
    }
}

