/*
 *
 * Author		: Kieron Higgs, Bailey Mills
 * Date			: Jan. 25, 2019
 * Project		: Assignment 1
 * File			: FavoriteMovies.java
 * Description	: Contains the code which generates a page for a single random movie, including title, overview, and poster image.
 */

package com.example.k13r0.TMDb_Project.Activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.k13r0.TMDb_Project.FavMoviesDBHelper;
import com.example.k13r0.TMDb_Project.Utilities.Movie;
import com.example.k13r0.TMDb_Project.Utilities.MovieAdapter;
import com.example.k13r0.TMDb_Project.R;
import java.util.ArrayList;

/*
 * Class		: FavoriteMovies
 * Description	:
 */
public class FavoriteMovies extends AppCompatActivity {
    private Context context;
    private SQLiteDatabase database;
    private ListView favoritesList;
    private MovieAdapter movieAdapter;

    /*
     * Function		: onCreate
     * Description	: Sets up the random movie button function call and the view references
     *                required to complete the operations of the button.
     * Parameters	: Bundle savedInstanceState
     * Returns		: N/A
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        context = this;

        FavMoviesDBHelper dbHelper = new FavMoviesDBHelper(this);
        database = dbHelper.getReadableDatabase();
        ArrayList<Movie> favoritesArray = new ArrayList<>();
        String query = "SELECT name FROM favMovies";
        Cursor cursor = database.rawQuery(query,null);
        while (cursor.moveToNext()){
            Movie favorite = new Movie();
            favorite.SetTitle(cursor.getString(cursor.getColumnIndex("name")));
            favoritesArray.add(favorite);
        }

        movieAdapter = new MovieAdapter(context, R.layout.list_row, favoritesArray);
        favoritesList = findViewById(android.R.id.list);
        favoritesList.setAdapter(movieAdapter);
    }
}