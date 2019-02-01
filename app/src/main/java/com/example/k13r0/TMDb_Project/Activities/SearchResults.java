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

import org.json.JSONArray;

import java.util.ArrayList;

public class SearchResults extends AppCompatActivity
{
    private Context context;
    private RequestQueue requestQueue;
    private Session guestSession;

    private ListView resultsList;
    private ArrayList<Movie> resultsArray;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        context = getApplicationContext();
        requestQueue = Volley.newRequestQueue(this);
        guestSession = new Session(context);

        resultsArray = guestSession.GetSearchResults();
        movieAdapter = new MovieAdapter(context, R.layout.upcoming_row, resultsArray);

        resultsList = findViewById(android.R.id.list);
        resultsList.setAdapter(movieAdapter);
    }
}

