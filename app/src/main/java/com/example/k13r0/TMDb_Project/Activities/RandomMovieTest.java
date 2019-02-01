package com.example.k13r0.TMDb_Project.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.k13r0.TMDb_Project.Classes.Movie;
import com.example.k13r0.TMDb_Project.Classes.Session;
import com.example.k13r0.TMDb_Project.MainActivity;
import com.example.k13r0.TMDb_Project.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class RandomMovieTest extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        final Context context = getApplicationContext();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final Session guestSession = new Session(context);

        final TextView randomMovieTitle = findViewById(R.id.randomTitle);
        final TextView randomOverview = findViewById(R.id.randomOverview);
        final ImageView randomPoster = findViewById(R.id.randomPoster);
        String titleAndYear = "";

        Button randomButton = findViewById(R.id.randomButton);
        randomButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Movie.GetRandomMovie(guestSession, requestQueue);
                Movie randomMovie = guestSession.GetRandomMovieObject();

                randomMovieTitle.setText(randomMovie.title);
                randomOverview.setText(randomMovie.overview);

                if (randomMovie.releaseDate != null)
                {
                    SimpleDateFormat year = new SimpleDateFormat("yyyy");
                    String titleAndYear = randomMovie.title + " (" + year.format(randomMovie.releaseDate) + ")";
                    randomMovieTitle.setText(titleAndYear);
                }


                //TextView randomReleaseDate = findViewById(R.id.randomReleaseDate);
                //randomReleaseDate.setText(randomMovie.releaseDate.toString());
                if (randomMovie.posterPath != null)
                {
                    Picasso.with(context).load("https://image.tmdb.org/t/p/w500/" + randomMovie.posterPath).into(randomPoster);
                }
            }
        });
    }
}
