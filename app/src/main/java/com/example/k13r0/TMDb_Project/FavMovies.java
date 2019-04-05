/*
 *
 * Author		: Arie Kraayenbrink
 * Date			: Apr. 5th, 2019
 * Project		: Assignment 2
 * File			: FavMovies.java
 * Description	: This is where the column names for the favourite movies database are listed.
 */

package com.example.k13r0.TMDb_Project;

import android.provider.BaseColumns;

public class FavMovies {

    private FavMovies(){}

    public static final class MovieEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "favMovies";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_BACKDROP_PATH = "backdropPath";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
    }
}
