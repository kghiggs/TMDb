/*
 *
 * Author		: Kieron Higgs, Bailey Mills, Arie Kraayenbrink
 * Date			: Apr. 5th, 2019
 * Project		: Assignment 2
 * File			: MovieDetails.java
 * Description	: The "Detail" page used in the TMDb app to display a single movie's attributes.
 */

package com.example.k13r0.TMDb_Project.Activities;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.example.k13r0.TMDb_Project.FavMovies;
import com.example.k13r0.TMDb_Project.FavMoviesDBHelper;
import com.example.k13r0.TMDb_Project.R;
import com.example.k13r0.TMDb_Project.Utilities.Session;
import com.example.k13r0.TMDb_Project.Utilities.Movie;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/*
 * Class		: MovieDetails
 * Description	: This class is used to generate movie detail pages.
 */
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
    Button buttonAddFav;

    /*
     * Function		: onCreate()
     * Description	: Creates the MovieDetails page and activity.
     * Parameters	: Bundle savedInstanceState
     * Returns		: N/A
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Bundle data = getIntent().getExtras();
        final Movie selectedMovie = (Movie) data.getParcelable("selectedMovie");

        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);
        imgPoster = findViewById(R.id.imgPoster);
        imgBackground = findViewById(R.id.imgBackground);

        screenBackground = findViewById(R.id.mainActivity);

        FavMoviesDBHelper dbHelper = new FavMoviesDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        buttonAddFav = findViewById(R.id.btnFav);

        // Update button text on load
        UpdateButtonText(CheckDataExists(FavMovies.MovieEntry.TABLE_NAME, FavMovies.MovieEntry.COLUMN_NAME, selectedMovie.GetTitle()));

        buttonAddFav.setOnClickListener(new View.OnClickListener() {

        /*
         * Function		: onClick()
         * Description	: Allows the user to add/remove a movie from the Favorites table/list.
         * Parameters	: View v
         * Returns		: N/A
         */
        @Override
        public void onClick(View v) {

            boolean inDB = CheckDataExists(FavMovies.MovieEntry.TABLE_NAME, FavMovies.MovieEntry.COLUMN_NAME, selectedMovie.GetTitle());

            // Todo: check if in database
            //Check if movie is already saved.
            if (inDB)
            {
                //remove the movie
                deleteItem(selectedMovie);

            }
            else
            {
                //add the movie
                addItem(selectedMovie);
            }

            UpdateButtonText(CheckDataExists(FavMovies.MovieEntry.TABLE_NAME, FavMovies.MovieEntry.COLUMN_NAME, selectedMovie.GetTitle()));
        }
        });

        LoadMovie(selectedMovie);
    }

    /*
     * Function		: UpdateButtonText
     * Description	: Changes whether the MovieDetails button adds or removes the given movie from Favorites depending on whether it is pre-existing.
     * Parameters	: Bundle savedInstanceState
     * Returns		: N/A
     */
    private void UpdateButtonText(boolean inDB)
    {
        if (inDB){
            buttonAddFav.setText("Remove from favourites");
        }
        else{
            //Reset button text
            buttonAddFav.setText(getString(R.string.add_to_favourites));
        }
    }

    /*
     * Function		: deleteItem()
     * Description	: Removes a movie from the database
     * Author       : Arie
     * Parameters	: Movie movie : The movie object
     * Returns		: N/A
     */
    private boolean deleteItem(Movie selectedMovie) {
        boolean result = false;

        String query = "SELECT " +
                FavMovies.MovieEntry._ID +
                ", " + FavMovies.MovieEntry.COLUMN_NAME +
                ", " + FavMovies.MovieEntry.COLUMN_OVERVIEW +
                ", " + FavMovies.MovieEntry.COLUMN_POSTER_PATH +
                ", " + FavMovies.MovieEntry.COLUMN_BACKDROP_PATH +
                ", " + FavMovies.MovieEntry.COLUMN_RELEASE_DATE +
                " FROM " + FavMovies.MovieEntry.TABLE_NAME +
                " WHERE " +
                FavMovies.MovieEntry.COLUMN_NAME + " = '" +
                selectedMovie.GetTitle() + "';";

        Cursor cursor = mDatabase.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            result = mDatabase.delete(FavMovies.MovieEntry.TABLE_NAME, FavMovies.MovieEntry._ID  + " = " + Integer.parseInt(cursor.getString(0)), null) > 0;    //hope this is right, untested. TODO: check this!
        }

        cursor.close();

        return result;
    }

    /*
     * Function		: addItem()
     * Description	: Adds a movie title to the database
     * Author       : Arie
     * Parameters	: Movie movie : The movie object with the data to add to the database.
     * Returns		: N/A
     */
    private void addItem(Movie movie)
    {
        Context context = getApplicationContext();
        CharSequence saveMessage = "Movie saved to favourites";
        int duration = Toast.LENGTH_SHORT;

        String name = movie.GetTitle();
        String overview = movie.GetOverview();
        String posterPath = movie.GetPosterPath();
        String backdropPath = movie.GetBackdropPath();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String releaseDate = dateFormat.format(movie.GetReleaseDate());

        //Check if movie is already in database.
        if(1 == 2)  // Todo: fix this
        {
            saveMessage = "Movie already in favourites";
            duration = Toast.LENGTH_LONG;
        }
        else if (name.trim().length() != 0){
            ContentValues cv = new ContentValues();
            cv.put(FavMovies.MovieEntry.COLUMN_NAME, name);
            cv.put(FavMovies.MovieEntry.COLUMN_OVERVIEW, overview);
            cv.put(FavMovies.MovieEntry.COLUMN_POSTER_PATH, posterPath);
            cv.put(FavMovies.MovieEntry.COLUMN_BACKDROP_PATH, backdropPath);
            cv.put(FavMovies.MovieEntry.COLUMN_RELEASE_DATE, releaseDate);

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



    /*
     * Function		: CheckDataExists(String TableName, String dbField, String fieldValue)
     * Description	: Checks if a movie is saved in the database
     * Author       : Arie
     * Parameters	: String TableName      : The table to check
     *              : String dbField        : The field column to check
     *              : String fieldValue     : The field value to look for
     * Returns		: N/A
     */
    private boolean CheckDataExists(String TableName, String dbField, String fieldValue)
    {
        String query = "SELECT " + dbField +
                " FROM " + TableName +
                " WHERE " +
                dbField + " = '" + fieldValue + "';";

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
     * Function		: LoadMovie()
     * Description	: Loads a movie's details into a movie object, including the images.
     * Parameters	: Movie movie : the movie to load the details of into the page
     * Returns		: N/A
     */
    private void LoadMovie(Movie movie){
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
        else {
            imgPoster.setImageDrawable(null);
        }

        // Background Image
        String backdropPath = movie.GetBackdropPath();
        if (!TextUtils.equals(backdropPath, "null"))
        {
            Picasso.with(context).load(getString(R.string.image_URL) + backdropPath).into(imgBackground);
            imageToGetColourFrom = backdropPath;
        }
        else {
            imgBackground.setImageDrawable(null);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 20, 0, 0);
            lp.width = ActionBar.LayoutParams.MATCH_PARENT;
            imgPoster.setLayoutParams(lp);
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

    /*
     * Function		: GetContrastColor()
     * Description	: Retrieves the contrasting color for displaying movie details pages properly. (https://stackoverflow.com/questions/4672271/reverse-opposing-colors)
     * Parameters	: Bundle savedInstanceState
     * Returns		: N/A
     */
    public static boolean GetContrastColor(Color color) {
        double y = (299 * color.red() + 587 * color.green() + 114 * color.blue()) / 1000;
        return y >= 128 ? true : false;
    }

    /*
     * Function		: GetAverageColor()
     * Description	: Generates the average color of a poster image to make the UI more fitting.
     * Parameters	: Bitmap bitmap
     * Returns		: N/A
     */
    public static int GetAverageColour (Bitmap bitmap){
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

        float modifier = 0.6f;
        r = (int)(r / count * modifier);
        g = (int)(g / count * modifier);
        b = (int)(b / count * modifier);

        return Color.rgb(r, g, b);
    }
}
