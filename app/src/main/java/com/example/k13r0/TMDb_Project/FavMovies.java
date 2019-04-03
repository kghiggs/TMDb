package com.example.k13r0.TMDb_Project;

import android.provider.BaseColumns;

public class FavMovies {

    private FavMovies(){}

    public static final class MovieEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "favMovies";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
