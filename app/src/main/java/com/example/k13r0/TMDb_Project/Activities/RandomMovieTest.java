package com.example.k13r0.TMDb_Project.Activities;

import android.content.Context;
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
import com.example.k13r0.TMDb_Project.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class RandomMovieTest extends AppCompatActivity
{
    private Context context;
    private RequestQueue requestQueue;
    private Session guestSession;
    private TextView randomMovieTitle;
    private TextView randomOverview;
    private ImageView randomPoster;
    private Button randomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        context = getApplicationContext();
        requestQueue = Volley.newRequestQueue(this);
        guestSession = new Session(context);

        randomMovieTitle = findViewById(R.id.randomTitle);
        randomOverview = findViewById(R.id.randomOverview);
        randomPoster = findViewById(R.id.randomPoster);

        randomButton = findViewById(R.id.randomButton);
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
