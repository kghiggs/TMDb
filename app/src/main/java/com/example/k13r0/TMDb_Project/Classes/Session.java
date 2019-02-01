/*
 *
 * Author		: Kieron Higgs
 * Date			: Jan. 24, 2019
 * Project		: Assignment 1
 * File			: Session.java
 * Description	: Code used to instigate guest sessions on TMDb.org, using a freely available developer API key.
 */


package com.example.k13r0.TMDb_Project.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Session extends Activity
{
    private SharedPreferences sharedPreferences;

    public Session(Context context)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void StoreGSID(String GSID)
    {
        sharedPreferences.edit().putString("GSID", GSID).commit();
    }

    public String GetGSID()
    {
        String GSID = sharedPreferences.getString("GSID","");
        return GSID;
    }

    public void SetAPIKey(String APIKey)
    {
        sharedPreferences.edit().putString("APIKey", APIKey).commit();
    }

    public String GetAPIKey()
    {
        String APIKey = sharedPreferences.getString("APIKey","");
        return APIKey;
    }

    public void GetGSID(RequestQueue requestQueue)
    {
        String GSID_URL = "https://api.themoviedb.org/3/authentication/guest_session/new?api_key=" + GetAPIKey();

        JsonObjectRequest requestGSID = new JsonObjectRequest(Request.Method.GET, GSID_URL, null,

            new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        StoreGSID(response.getString("guest_session_id"));
                        Log.d("GSIDResponseSUCCESS", response.toString());
                    }
                    catch(JSONException exception)
                    {
                        Log.d("GSIDResponseJSONFAIL", exception.toString());
                    }
                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Log.d("GSIDConnErr", error.toString());
                }
            }
        );

        requestQueue.add(requestGSID);
    }

    public Movie GetRandomMovieObject()
    {
        Movie currentMovie = new Movie();
        try
        {
            currentMovie = new Movie(new JSONObject(sharedPreferences.getString("RandomMovie","")));
        }
        catch (JSONException exception)
        {
            Log.d("CurrentMovieJSONFAIL", exception.toString());
        }
        return currentMovie;
    }

    public void SetRandomMovieString(JSONObject movieDetails)
    {
        sharedPreferences.edit().putString("RandomMovie", movieDetails.toString()).commit();
    }

    public ArrayList<Movie> GetUpcomingMovies()
    {
        int numMovies = 0;
        JSONObject latestMoviesObject;
        JSONArray latestMoviesArray;
        ArrayList latestMovies = new ArrayList();

        try
        {
            latestMoviesObject = new JSONObject(sharedPreferences.getString("UpcomingMovies",""));
            latestMoviesArray = latestMoviesObject.getJSONArray("results");
            numMovies = latestMoviesArray.length();

            for (int upcomingCounter = 0; upcomingCounter < numMovies; upcomingCounter++)
            {
                latestMovies.add(new Movie(latestMoviesArray.getJSONObject(upcomingCounter)));
            }
        }
        catch (JSONException exception)
        {
            Log.d("CurrentMovieJSONFAIL", exception.toString());
        }

        return latestMovies;
    }

    public void SetLatestMoviesString(JSONObject latestMovies)
    {
        sharedPreferences.edit().putString("UpcomingMovies", latestMovies.toString()).commit();
    }

    public void SetSearchMoviesString(JSONObject searchResults)
    {
        sharedPreferences.edit().putString("SearchMovies", searchResults.toString()).commit();
    }

    public ArrayList<Movie> GetSearchResults()
    {
        int numMovies = 0;
        JSONObject latestMoviesObject;
        JSONArray latestMoviesArray;
        ArrayList latestMovies = new ArrayList();

        try
        {
            latestMoviesObject = new JSONObject(sharedPreferences.getString("SearchMovies",""));
            latestMoviesArray = latestMoviesObject.getJSONArray("results");
            numMovies = latestMoviesArray.length();

            for (int upcomingCounter = 0; upcomingCounter < numMovies; upcomingCounter++)
            {
                latestMovies.add(new Movie(latestMoviesArray.getJSONObject(upcomingCounter)));
            }
        }
        catch (JSONException exception)
        {
            Log.d("CurrentMovieJSONFAIL", exception.toString());
        }

        return latestMovies;
    }
}