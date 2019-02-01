package com.example.k13r0.TMDb_Project.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie>
{
    Context context;
    int resource;
    ArrayList<Movie> movies;

    public MovieAdapter(Context context, int resource, ArrayList<Movie> movies)
    {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.movies = new ArrayList<>();

        for (int i = 0; i < movies.size(); i++)
        {
            this.movies.add(movies.get(i));
        }
    }

    @Override
    public int getCount()
    {
        return movies!=null ? movies.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Movie movie = movies.get(position);

        // Will be null the first time the view is instantiated; prevents re-instantiation (resource waste)
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.upcoming_row, parent, false);
        }
        TextView titleAndYear = convertView.findViewById(R.id.titleAndYear);
        TextView moreInfo = convertView.findViewById(R.id.moreInfo);
        ImageView thumbnail = convertView.findViewById(R.id.thumbnail);

        titleAndYear.setText(movie.title);

        if (movie.releaseDate != null)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE. MMMM dd");
            String dateString = dateFormat.format(movie.releaseDate);
            moreInfo.setText(dateString);
        }

        //TextView randomReleaseDate = findViewById(R.id.randomReleaseDate);
        //randomReleaseDate.setText(randomMovie.releaseDate.toString());
        if (movie.posterPath != null)
        {
            Picasso.with(context).load("https://image.tmdb.org/t/p/w500/" + movie.posterPath).into(thumbnail);
        }
        return convertView;
    }
}