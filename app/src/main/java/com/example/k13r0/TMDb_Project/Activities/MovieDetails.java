package com.example.k13r0.TMDb_Project.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.k13r0.TMDb_Project.R;
import com.example.k13r0.TMDb_Project.Utilities.Session;
import com.example.k13r0.TMDb_Project.Utilities.Movie;

import static com.example.k13r0.TMDb_Project.Utilities.MakeSnackbar.ShowSnackbar;

public class MovieDetails extends AppCompatActivity
{
    private Context context;
    private RequestQueue requestQueue;
    private Session guestSession;

    private TextView txtTitle;
    private TextView txtDetails;
    private ImageView imgPoster;
    private ImageView imgBackground;

    /*
     * Function		: onCreate
     * Description	: 
     * Parameters	: Bundle savedInstanceState
     * Returns		: N/A
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Bundle data = getIntent().getExtras();
        Movie selectedMovie = (Movie) data.getParcelable("selectedMovie");
    }


    /*
     * Function		: onCreate
     * Description	: Requests a random page from the database and displays information relating to it; allows user to
     *                repeat the process with the press of a button.
     * Parameters	: View v : the view to create the snackbar notification for
     * Returns		: N/A
     */
    private void LoadRandomMovie(View v){
        Movie.GetRandomMovie(guestSession, requestQueue, context);
        Movie randomMovie = guestSession.GetRandomMovieObject(context);

        

    }

}
