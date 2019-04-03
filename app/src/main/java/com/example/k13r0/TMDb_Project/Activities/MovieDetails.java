package com.example.k13r0.TMDb_Project.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.k13r0.TMDb_Project.R;
import com.example.k13r0.TMDb_Project.Utilities.Session;
import com.example.k13r0.TMDb_Project.Utilities.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;

import static com.example.k13r0.TMDb_Project.Utilities.MakeSnackbar.ShowSnackbar;

public class MovieDetails extends AppCompatActivity
{
    private Context context;
    private RequestQueue requestQueue;
    private Session guestSession;

    private TextView txtTitle;
    private TextView txtDescription;
    private ImageView imgPoster;
    private ImageView imgBackground;

    private ConstraintLayout screenBackground;

    private int backgroundColour;

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

        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);
        imgPoster = findViewById(R.id.imgPoster);
        imgBackground = findViewById(R.id.imgBackground);

        screenBackground = findViewById(R.id.mainActivity);

        LoadRandomMovie(selectedMovie);
    }


    /*
     * Function		:
     * Description	:
     * Parameters	: Movie movie : the movie to load the details of into the page
     * Returns		: N/A
     */
    private void LoadRandomMovie(Movie movie){
        txtTitle.setText(movie.GetTitle());
        txtDescription.setText(movie.GetOverview());

        // Release Date
        if (movie.GetReleaseDate() != null)
        {
            SimpleDateFormat year = new SimpleDateFormat("yyyy");
            String titleAndYear = movie.GetTitle() + " (" + year.format(movie.GetReleaseDate()) + ")";
            txtTitle.setText(titleAndYear);
        }

        String imageToGetColourFrom = null;

        // Poster Image
        String posterPath = movie.GetPosterPath();
        if (!TextUtils.equals(posterPath, "null"))
        {
            Picasso.with(context).load(getString(R.string.image_URL) + posterPath).into(imgPoster);
            imageToGetColourFrom = posterPath;
        }

        // Background Image
        String backdropPath = movie.GetBackdropPath();
        if (!TextUtils.equals(backdropPath, "null"))
        {
            Picasso.with(context).load(getString(R.string.image_URL) + backdropPath).into(imgBackground);
            imageToGetColourFrom = backdropPath;
        }


        if (imageToGetColourFrom != null){
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    backgroundColour = GetAverageColour(bitmap);
                    screenBackground.setBackgroundColor(backgroundColour);
                    boolean blackText = GetContrastColor(Color.valueOf(backgroundColour));

                    if (!blackText){
                        txtTitle.setTextColor(Color.WHITE);
                        txtDescription.setTextColor(Color.WHITE);
                    }
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) { }
                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) { }
            };

            Picasso.with(context).load(getString(R.string.image_URL) + imageToGetColourFrom).into(target);
        }
    }


    // https://stackoverflow.com/questions/4672271/reverse-opposing-colors
    public static boolean GetContrastColor(Color color) {
        double y = (299 * color.red() + 587 * color.green() + 114 * color.blue()) / 1000;
        return y >= 128 ? true : false;
    }


    private int GetAverageColour (Bitmap bitmap){
        int r = 0;
        int g = 0;
        int b = 0;

        int count = 0;

        for (int i = 0; i < bitmap.getWidth(); i++){
            for (int j = 0; j < bitmap.getHeight(); j++){
                int curr = bitmap.getPixel(i, j);
                r += Color.red(curr);
                g += Color.green(curr);
                b += Color.blue(curr);
                count++;
            }
        }

        r = (int)(r / count);
        g = (int)(g / count);
        b = (int)(b / count);

        return Color.rgb(r, g, b);
    }
}
