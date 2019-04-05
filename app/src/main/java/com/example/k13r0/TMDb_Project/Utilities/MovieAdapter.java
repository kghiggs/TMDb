/*
 *
 * Author		: Kieron Higgs
 * Date			: Jan. 25, 2019
 * Project		: Assignment 1
 * File			: MovieAdapter.java
 * Description	: The adapter used to convert JSON into Movie objects held within ArrayLists and display them.
 */

package com.example.k13r0.TMDb_Project.Utilities;
import com.example.k13r0.TMDb_Project.Activities.MovieDetails;
import com.example.k13r0.TMDb_Project.R;
import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
 * Class		: MovieAdapter
 * Description	: This class is used to adapt the data within a Movie object to an ArrayList for display in a ListView.
 */
public class MovieAdapter extends ArrayAdapter<Movie>
{
    Context context;
    int resource;
    ArrayList<Movie> movies;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /*
     * Adapter  	: MovieAdapter()
     * Description	: This class is used to adapt the data within a Movie object to an ArrayList for display in a ListView.
     * Parameters   : Context context - The context object passed from the current activity
     *                int resource - The XML resource used to format the data via the adapter
     *                ArrayList<Movie> movies - The list of movies to be placed in the list
     */
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

    /*
     * Function  	: getCount()
     * Description	: Returns the number of movies in the ArrayList being used.
     * Return       : int - the number of movies in the ArrayList
     */
    @Override
    public int getCount()
    {
        return movies!=null ? movies.size() : 0;
    }

    /*
     * Function  	: getView()
     * Description	: Populates the given View with the movies ArrayList data.
     * Parameters   : int position - The index within the ArrayList of the current movie whose data is being used to populate the list
     *                View convertView - The View to which the ListView belongs
     *                ViewGroup parent - The parent view to the layout containing the ListView; used on the first call to create the convertView
     * Return       : View - the View originally passed to the method; to be re-used for subsequent elements to populate the list with
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Movie movie = movies.get(position);

        // Will be null the first time the view is instantiated; prevents re-instantiation (resource waste)
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
        }
        TextView titleAndYear = convertView.findViewById(R.id.titleAndYear);
        TextView moreInfo = convertView.findViewById(R.id.moreInfo);
        ImageView thumbnail = convertView.findViewById(R.id.thumbnail);

        titleAndYear.setText(movie.GetTitle());

        LinearLayout listRow = convertView.findViewById(R.id.list_row);
        listRow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent detailsIntent = new Intent(context, MovieDetails.class);
                        detailsIntent.putExtra("selectedMovie", movies.get(position));
                        context.startActivity(detailsIntent);
                    }
                }
        );

        if (movie.GetReleaseDate() != null)
        {
            moreInfo.setText(dateFormat.format(movie.GetReleaseDate()));
        }

        if (movie.GetPosterPath() != null)
        {
            Picasso.with(context).load(context.getString(R.string.image_URL) + movie.GetPosterPath()).into(thumbnail);
        }
        return convertView;
    }
}