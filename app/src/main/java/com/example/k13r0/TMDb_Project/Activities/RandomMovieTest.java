/*
 *
 * Author		: Kieron Higgs
 * Date			: Jan. 25, 2019
 * Project		: Assignment 1
 * File			: RandomMovieTest.java
 * Description	: Contains the code which generates a page for a single random movie, including title, overview, and poster image.
 */

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

import com.example.k13r0.TMDb_Project.Utilities.Movie;
import com.example.k13r0.TMDb_Project.Utilities.Session;
import com.example.k13r0.TMDb_Project.R;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

/*
 * Class		: RandomMovieTest
 * Description	: This class is used to query the database for a random movie based on a random ID.
 */
public class RandomMovieTest extends AppCompatActivity
{
    private Context context;
    private RequestQueue requestQueue;
    private Session guestSession;

    private TextView randomMovieTitle;
    private TextView randomOverview;
    private ImageView randomPoster;
    private Button randomButton;

    /*
     * Function		: onCreate
     * Description	: Requests a random page from the database and displays information relating to it; allows user to
     *                repeat the process with the press of a button.
     * Parameters	: Bundle savedInstanceState
     * Returns		: N/A
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        context = this;
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

                Movie.GetRandomMovie(guestSession, requestQueue, context);
                Movie randomMovie = guestSession.GetRandomMovieObject(context);

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
                    Picasso.with(context).load(getString(R.string.image_URL) + randomMovie.posterPath).into(randomPoster);
                }
            }
        });
    }
}
