package com.example.k13r0.TMDb_Project.Classes;

import android.util.Log;
import android.view.View;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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

    public interface ServerCallback
    {
        void onSuccess(JSONObject result);
    }

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

    // Get movie based on an array of search results, using data from the first (most relevant) result at index 0.
    public Movie(JSONObject details)
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
            Log.d("MovieDetailJSONFAIL", exception.toString());
        }
        catch (ParseException exception)
        {
            Log.d("MovieDetailsPARSEFAIL", exception.toString());
        }

    }

    // Get random movie
    public static void GetRandomMovie(final Session guestSession, final RequestQueue requestQueue)
    {
        String LatestMovieURL = "https://api.themoviedb.org/3/movie/latest?api_key=" + guestSession.GetAPIKey() + "&language=en-US";

        JsonObjectRequest requestLatestMovie = new JsonObjectRequest(Request.Method.GET, LatestMovieURL, null,

            new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        int latestMovieID = response.getInt("id");
                        Log.d("RandomMovieSUCCESS", response.toString());

                        Random rand = new Random();
                        int randomMovieID;
                        String RandomMovieURL = "";

                        randomMovieID = rand.nextInt(latestMovieID + 1);

                        RandomMovieURL = "https://api.themoviedb.org/3/movie/" + Integer.toString(randomMovieID) + "?api_key=" + guestSession.GetAPIKey() + "&language=en-US";

                        JsonObjectRequest requestRandomMovie = new JsonObjectRequest(Request.Method.GET, RandomMovieURL, null,

                            new Response.Listener<JSONObject>()
                            {
                                @Override
                                public void onResponse(JSONObject randomDetails)
                                {
                                    guestSession.SetRandomMovieString(randomDetails);
                                    Log.d("RandomMovieSUCCESS", randomDetails.toString());
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    Log.d("RandomMovieCONNERR", error.toString());
                                }
                            }
                        );
                        requestQueue.add(requestRandomMovie);

                    }
                    catch (JSONException exception)
                    {
                        Log.d("RandomMovieJSONFAIL", exception.toString());
                    }
                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Log.d("RandomMovieCONNERR", error.toString());
                }
            }
        );
        requestQueue.add(requestLatestMovie);
    }
    // Get random movie
    public static void RetrieveUpcomingMovies(final Session guestSession, final RequestQueue requestQueue)
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
                        Log.d("LatestMoviesCONNERR", error.toString());
                    }
                }
        );
        requestQueue.add(requestLatestMovie);
    }
}