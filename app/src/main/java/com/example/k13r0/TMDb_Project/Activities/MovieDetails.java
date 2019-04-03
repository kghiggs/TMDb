package com.example.k13r0.TMDb_Project.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.k13r0.TMDb_Project.FavMovies;
import com.example.k13r0.TMDb_Project.FavMoviesDBHelper;
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
    private SQLiteDatabase mDatabase;
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

        FavMoviesDBHelper dbHelper = new FavMoviesDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        Button buttonAddFav = findViewById(R.id.btnFav);
        buttonAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        LoadRandomMovie(selectedMovie);
    }



    /*
     * Function		: addItem()
     * Description	: Adds a movie title to the database
     * Author       : Arie
     * Parameters	: N/A
     * Returns		: N/A
     */
    private void addItem()
    {
        Context context = getApplicationContext();
        CharSequence saveMessage = "Movie saved to favourites";
        int duration = Toast.LENGTH_SHORT;

        String name = txtTitle.getText().toString();

        //Check if movie is already in database.
        //boolean exists = CheckDataExists(FavMovies.MovieEntry.TABLE_NAME, FavMovies.MovieEntry.COLUMN_NAME, name);
        //Toast.makeText(context, String.valueOf(exists), duration).show();

        if(1 == 2)  //fix this
        {
            saveMessage = "Movie already in favourites";
            duration = Toast.LENGTH_LONG;
        }
        else if (name.trim().length() != 0){
            ContentValues cv = new ContentValues();
            cv.put(FavMovies.MovieEntry.COLUMN_NAME, name);

            long result = mDatabase.insert(FavMovies.MovieEntry.TABLE_NAME, null, cv);

            if (result < 0)
            {
                saveMessage = "Error: Failed to save movie to favourites";
                duration = Toast.LENGTH_LONG;
            }
        }
        else
        {
            saveMessage = "Error: Failed to save, no movie name provided";
            duration = Toast.LENGTH_LONG;
        }

        Toast.makeText(context, saveMessage, duration).show();
    }

    private boolean CheckDataExists(String TableName, String dbField, String fieldValue)
    {
        String query = "SELECT " + dbField +
                " FROM " + TableName +
                " WHERE " +
                dbField + " = '" + fieldValue + "'" +
                ";";

        Cursor cursor = mDatabase.rawQuery(query, null);
        if (cursor.getCount() <= 0)
        {
            cursor.close();
            return false;
        }

        cursor.close();
        return true;
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
