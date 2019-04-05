/*
 *
 * Author		: Arie Kraayenbrink
 * Date			: Apr. 5th, 2019
 * Project		: Assignment 2
 * File			: FavMoviesDBHelper.java
 * Description	: This class enables the SQLite database for Favorite movies.
 */

package com.example.k13r0.TMDb_Project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.k13r0.TMDb_Project.FavMovies.*;
import com.example.k13r0.TMDb_Project.Utilities.Movie;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FavMoviesDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movies.db";
    public static final int DATABASE_VERSION = 1;

    /*
     * Function		: FavMoviesDBHelper(Context context)
     * Description	:
     * Author       : Arie
     * Parameters	: Context context : The contect
     * Returns		: N/A
     */
    public FavMoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
     * Function		: onCreate(SQLiteDatabase db)
     * Description	: Creates the database
     * Author       : Arie
     * Parameters	: SQLiteDatabase db : The database
     * Returns		: N/A
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVMOVIES_TABLE = "CREATE TABLE " +
                MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL" +
                ");";

        db.execSQL((SQL_CREATE_FAVMOVIES_TABLE));
    }

    /*
     * Function		: onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
     * Description	: Drops the database and creates a new one. All old data is discarded.
     * Author       : Arie
     * Parameters	: SQLiteDatabase db : The database
     *              : int oldVersion    :
     *              : int newVersion    :
     * Returns		: N/A
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }

    public void loadFavorites(Context context, ArrayList<Movie> favoritesArray)
    {
        String query = "SELECT name, overview, posterPath, backdropPath, releaseDate FROM favMovies";
        Cursor cursor = getReadableDatabase().rawQuery(query,null);

        try
        {
            while (cursor.moveToNext()){
                Movie favorite = new Movie();
                favorite.SetTitle(cursor.getString(cursor.getColumnIndex("name")));
                favorite.SetOverview(cursor.getString(cursor.getColumnIndex("overview")));
                favorite.SetPosterPath(cursor.getString(cursor.getColumnIndex("posterPath")));
                favorite.SetBackdropPath(cursor.getString(cursor.getColumnIndex("backdropPath")));
                favorite.SetReleaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("releaseDate"))));
                favoritesArray.add(favorite);
            }
        }
        catch (
                ParseException exception)
        {
            Log.d(context.getString(R.string.movie_detail_parse_ERR), exception.toString());
        }
    }
}
