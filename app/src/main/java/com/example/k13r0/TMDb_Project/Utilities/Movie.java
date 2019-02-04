/*
 *
 * Author		: Kieron Higgs
 * Date			: Jan. 25, 2019
 * Project		: Assignment 1
 * File			: Movie.java
 * Description	: Models a movie onto a Java object, including methods for different types of queries for movies.
 */

package com.example.k13r0.TMDb_Project.Utilities;

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
public class Movie
{

    public int ID;
    public int GetID() { return this.ID; }

    public String title;
    public String GetTitle() { return this.title; }

    public String overview;
    public String GetOverview() { return this.overview; }

    public String posterPath;
    public String GetPosterPath() { return this.posterPath; }

    public String backdropPath;
    public String GetBackdropPath() { return this.backdropPath; }

    public Date releaseDate;
    public Date GetReleaseDate() { return this.releaseDate; }

    public boolean videoAvailable;
    public boolean VideoAvailable() { return this.videoAvailable; }

    public double popularity;
    public double GetPopularity() { return this.popularity; }

    public int voteCount;
    public int GetVoteCount() { return this.voteCount; }

    public double voteAverage;
    public double GetVoteAverage() { return this.voteAverage; }

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
        videoAvailable = false;
        popularity = 0;
        voteCount = 0;
        voteAverage = 0;
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
            releaseDate = new SimpleDateFormat("yyyy-MM-DD").parse(details.getString("release_date"));
            videoAvailable = details.getBoolean("video");
            popularity = details.getDouble("popularity");
            voteCount = details.getInt("vote_count");
            voteAverage = details.getDouble("vote_average");
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
     * Constructor	: GetRandomMovie()
     * Description	: Queries the database for a random movie based on a randomly generated movie ID.
     * Parameters   : final Session guestSession - The session object containing the API key
     *                final RequestQueue requestQueue - The Volley RequestQueue used for sending HTTP messages
     * Return:      : N/A
     */
    public static void GetRandomMovie(final Session guestSession, final RequestQueue requestQueue, final Context context)
    {
        String LatestMovieURL = context.getString(R.string.latest_movie_URL) + guestSession.GetAPIKey() + context.getString(R.string.lang_ENG_URL);

        JsonObjectRequest requestLatestMovie = new JsonObjectRequest(Request.Method.GET, LatestMovieURL, null,

            new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        int latestMovieID = response.getInt("id");
                        Random rand = new Random();
                        int randomMovieID;
                        String RandomMovieURL = "";

                        randomMovieID = rand.nextInt(latestMovieID + 1);

                        RandomMovieURL = context.getString(R.string.base_db_URL) + Integer.toString(randomMovieID) + "?api_key=" + guestSession.GetAPIKey() + context.getString(R.string.lang_ENG_URL);

                        JsonObjectRequest requestRandomMovie = new JsonObjectRequest(Request.Method.GET, RandomMovieURL, null,

                            new Response.Listener<JSONObject>()
                            {
                                @Override
                                public void onResponse(JSONObject randomDetails)
                                {
                                    guestSession.SetRandomMovieString(randomDetails);
                                    Log.d(context.getString(R.string.random_movie_SUCCESS), randomDetails.toString());
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    ShowSnackbar(context.getString(R.string.random_movie_conn_ERR), ((Activity)context).getWindow().findViewById(android.R.id.content));
                                    Log.d(context.getString(R.string.random_movie_conn_ERR), error.toString());
                                }
                            }
                        );
                        requestQueue.add(requestRandomMovie);

                    }
                    catch (JSONException exception)
                    {
                        ShowSnackbar(context.getString(R.string.random_movie_json_ERR), ((Activity)context).getWindow().findViewById(android.R.id.content));
                        Log.d(context.getString(R.string.random_movie_json_ERR), exception.toString());
                    }
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
    public static void RetrieveSearchResults(final Session guestSession, final RequestQueue requestQueue, String query)
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
                        Log.d("LatestMoviesCONNERR", error.toString());
                    }
                }
        );
        requestQueue.add(requestLatestMovie);
    }
}
