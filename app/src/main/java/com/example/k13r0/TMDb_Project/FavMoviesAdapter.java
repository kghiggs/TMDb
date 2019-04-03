package com.example.k13r0.TMDb_Project;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FavMoviesAdapter extends RecyclerView.Adapter<FavMoviesAdapter.FavMovieViewHolder> {

    private Context mContect;
    private Cursor mCursor;

    public FavMoviesAdapter(Context context, Cursor cursor)
    {
        mContect = context;
        mCursor = cursor;
    }
    public class FavMovieViewHolder extends RecyclerView.ViewHolder
    {
        public TextView nameText;

        public FavMovieViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public FavMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FavMovieViewHolder favMovieViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
