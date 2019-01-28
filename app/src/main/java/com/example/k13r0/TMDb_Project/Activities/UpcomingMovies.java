package com.example.k13r0.TMDb_Project.Activities;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.k13r0.TMDb_Project.Classes.MovieAdapter;
import com.example.k13r0.TMDb_Project.R;
import com.example.k13r0.TMDb_Project.Classes.Session;
import com.example.k13r0.TMDb_Project.Classes.Movie;

import java.util.ArrayList;

public class UpcomingMovies extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
        Context context = getApplicationContext();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final Session guestSession = new Session(context);

        Movie.RetrieveUpcomingMovies(guestSession, requestQueue);
        Movie[] upcomingMovies = guestSession.GetUpcomingMovies();

        ListView upcomingList = findViewById(android.R.id.list);

        ArrayList<Movie> upcomingArray = new ArrayList<>();

        int upcomingIndex = 0;
        for (upcomingIndex = 0; upcomingIndex < upcomingMovies.length; upcomingIndex++)
        {
            upcomingArray.add(upcomingMovies[upcomingIndex]);;
        }

        int test1 = upcomingIndex;
        int test2 = upcomingArray.size();
        MovieAdapter movieAdapter = new MovieAdapter(context, R.layout.upcoming_row, upcomingArray);
        int test = movieAdapter.getCount();
        upcomingList.setAdapter(movieAdapter);

    }

}

