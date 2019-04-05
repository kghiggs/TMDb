/*
 *
 * Author		: Kieron Higgs
 * Date			: Jan. 25, 2019
 * Project		: Assignment 1
 * File			: Movie.java
 * Description	: Models a movie onto a Java object, including methods for different types of queries for movies.
 */

package com.example.k13r0.TMDb_Project.Utilities;

import android.os.Parcelable;
import android.os.Parcel;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.k13r0.TMDb_Project.R;
import static com.example.k13r0.TMDb_Project.Utilities.MakeSnackbar.ShowSnackbar;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/*
 * Class		: Movie
 * Description	: This class is used to model a movie using object-oriented programming principles.
 */
public class Movie implements Parcelable
{

    private int ID;
    public int GetID() { return this.ID; }

    private String title;
    public String GetTitle() { return this.title; }
    public void SetTitle(String newTitle) { this.title = newTitle; }

    private String overview;
    public String GetOverview() { return this.overview; }
    public void SetOverview(String newOverview) {this.overview = newOverview; }

    private String posterPath;
    public String GetPosterPath() { return this.posterPath; }
    public void SetPosterPath(String newPosterPath) {this.posterPath = newPosterPath; }

    private String backdropPath;
    public String GetBackdropPath() { return this.backdropPath; }
    public void SetBackdropPath(String newBackdropPath) { this.backdropPath = newBackdropPath; }

    private Date releaseDate;
    public Date GetReleaseDate() { return this.releaseDate; }
    public void SetReleaseDate(Date newDate) { this.releaseDate = newDate; }

    /*
     * Constructor	: Movie()
     * Description	: Default constructor for the movie class.
     */
    public Movie()
    {
        ID = -1;
        title = null;
        overview = null;
        posterPath = null;
        backdropPath = null;
        releaseDate = null;
    }

    /*
     * Constructor	: Movie()
     * Description	: Instantiates a movie object based on a JSON string containing the data which will fill its attributes
     * Parameters   : JSONObject details - the JSON which contains the movie details
     */
    public Movie(JSONObject details, Context context)
    {
        try
        {
            ID = details.getInt("id");
            title = details.getString("title");
            overview = details.getString("overview");
            posterPath = details.getString("poster_path");
            backdropPath = details.getString("backdrop_path");
            releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(details.getString("release_date"));
        }
        catch (JSONException exception)
        {
            ShowSnackbar(context.getString(R.string.movie_detail_json_ERR), ((Activity)context).getWindow().findViewById(android.R.id.content));
            Log.d(context.getString(R.string.movie_detail_json_ERR), exception.toString());
        }
        catch (ParseException exception)
        {
            Log.d(context.getString(R.string.movie_detail_parse_ERR), exception.toString());
        }
    }

    /*
     * Constructor	: RetrieveUpcomingMovies()
     * Description	: Requests the list of new and upcoming movies from the database
     * Parameters   : final Session guestSession - The session object containing the API key
     *                final RequestQueue requestQueue - The Volley RequestQueue used for sending HTTP messages
     * Return:      : N/A
     */
    public static void RetrieveUpcomingMovies(final Session guestSession, final RequestQueue requestQueue, final Context context)
    {
        String LatestMovieURL = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + guestSession.GetAPIKey() + "&language=en-US&page=1";

        JsonObjectRequest requestLatestMovie = new JsonObjectRequest(Request.Method.GET, LatestMovieURL, null,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject latestMovies)
                    {
                        guestSession.SetLatestMoviesString(latestMovies);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        ShowSnackbar(context.getString(R.string.latest_movie_conn_ERR), ((Activity)context).getWindow().findViewById(android.R.id.content));
                        Log.d(context.getString(R.string.latest_movie_conn_ERR), error.toString());
                    }
                }
        );
        requestQueue.add(requestLatestMovie);
    }

    /*
     * Constructor	: RetrieveSearchResults()
     * Description	: Submits a search query to the database for a response containing a list of related movies.
     * Parameters   : final Session guestSession - The session object containing the API key
     *                final RequestQueue requestQueue - The Volley RequestQueue used for sending HTTP messages
     * Return:      : N/A
     */
    public static void RetrieveSearchResults(final Session guestSession, final RequestQueue requestQueue, final Context context, String query)
    {
        String HTMLquery = query.replaceAll(" ", "%20");
        String LatestMovieURL = "https://api.themoviedb.org/3/search/movie?api_key=" + guestSession.GetAPIKey() + "&language=en-US&page=1" + "&query=" + HTMLquery + "&include_adult=false";

        JsonObjectRequest requestLatestMovie = new JsonObjectRequest(Request.Method.GET, LatestMovieURL, null,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject searchResults)
                    {
                        guestSession.SetSearchMoviesString(searchResults);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        ShowSnackbar(context.getString(R.string.latest_movie_conn_ERR), ((Activity)context).getWindow().findViewById(android.R.id.content));
                        Log.d(context.getString(R.string.latest_movie_conn_ERR), error.toString());
                    }
                }
        );
        requestQueue.add(requestLatestMovie);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(posterPath);
        parcel.writeString(backdropPath);
        parcel.writeLong(releaseDate.getTime());
    }

    protected Movie(Parcel in) {
        ID = in.readInt();
        title = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        releaseDate = new Date(in.readLong());
    }


    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
