/*
 *
 * Author		: Kieron Higgs
 * Date			: Jan. 24, 2019
 * Project		: Assignment 1
 * File			: Session.java
 * Description	: Code used to instigate guest sessions on TMDb.org, using a freely available developer API key.
 */

package com.example.k13r0.TMDb_Project.Utilities;

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

/*
 * Class		: Session
 * Description	: This class is used to request a session with the database, receive a session token, and store it.
 */
public class Session extends Activity
{
    private SharedPreferences sharedPreferences;

    /*
     * Constructor	: Session()
     * Description	: Creates a new session for storing data in SharedPreferences.
     * Parameters   : Context context - The Context object passed from the main activity for reference
     */
    public Session(Context context)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /*
     * Function	: SetAPIKey()
     * Description	: Takes a String object containing a TMDb API key and stores it in SharedPreferences.
     * Parameters   : String APIKey - The API key, a series of letters and numbers in ASCII
     * Return:      : N/A
     */
    public void SetAPIKey(String APIKey)
    {
        sharedPreferences.edit().putString("APIKey", APIKey).commit();
    }

    /*
     * Function	: StoreGSID()
     * Description	: Takes a String Object containing a TMDb guest session ID (a series of numbers) and stores it in SharedPreferences.
     * Parameters   : JSONObject movieDetails - The JSONObject containing details pertaining to a random movie.
     * Return:      : N/A
     */
    public void StoreGSID(String GSID)
    {
        sharedPreferences.edit().putString("GSID", GSID).commit();
    }

    /*
     * Function	: SetRandomMovieString()
     * Description	: Takes a JSONObject and converts it to a string, storing it in the SharedPrefereneces.
     * Parameters   : JSONObject movieDetails - The JSONObject containing details pertaining to a random movie.
     * Return:      : N/A
     */
    public void SetRandomMovieString(JSONObject movieDetails)
    {
        sharedPreferences.edit().putString("RandomMovie", movieDetails.toString()).commit();
    }

    /*
     * Function	: SetLatestMoviesString()
     * Description	: Takes a JSONObject containing an upcoming movies list and converts it to a string, storing it in the SharedPrefereneces.
     * Parameters   : JSONObject latestMovies - The JSON data containing the list of upcoming movies
     * Return:      : N/A
     */
    public void SetLatestMoviesString(JSONObject latestMovies)
    {
        sharedPreferences.edit().putString("UpcomingMovies", latestMovies.toString()).commit();
    }

    /*
     * Function	: SetSearchMoviesString()
     * Description	: Takes a JSONObject and converts it to a string, storing it in the SharedPrefereneces.
     * Parameters   : JSONObject searchResults - The JSONObject containing search result data.
     * Return:      : N/A
     */
    public void SetSearchMoviesString(JSONObject searchResults)
    {
        sharedPreferences.edit().putString("SearchMovies", searchResults.toString()).commit();
    }

    /*
     * Function	: GetGSID()
     * Description	: Requests a guest session token ID from the TMDb website and uses the StoreGSID() method to save it.
     * Parameters   : RequestQueue requestQueue - A Volley request queue used for HTTP communication
     * Return:      : N/A
     */
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

    /*
     * Function	: GetAPIKey()
     * Description	: Converts the JSON in SharedPreferences representing the upcoming movies list to an ArrayList of Movie objects.
     * Parameters   : N/A
     * Return:      : ArrayList<Movie> - The ArrayList of Movies converted from JSON.
     */
    public String GetAPIKey()
    {
        String APIKey = sharedPreferences.getString("APIKey","");
        return APIKey;
    }

    /*
     * Function	: GetRandomMovieObject()
     * Description	: Takes the JSON data saved in SharedPreferences and instantiates a Movie object based on it, passing it back to the activity.
     * Parameters   : JSONObject searchResults - The JSONObject containing search result data.
     * Return:      : N/A
     */
    public Movie GetRandomMovieObject(Context context)
    {
        Movie currentMovie = new Movie();
        try
        {
            currentMovie = new Movie(new JSONObject(sharedPreferences.getString("RandomMovie","")), context);
        }
        catch (JSONException exception)
        {
            Log.d("CurrentMovieJSONFAIL", exception.toString());
        }
        return currentMovie;
    }

    /*
     * Function	: GetUpcomingMovies()
     * Description	: Converts the JSON in SharedPreferences representing the upcoming movies list to an ArrayList of Movie objects.
     * Parameters   : N/A
     * Return:      : ArrayList<Movie> - The ArrayList of Movies converted from JSON.
     */
    public ArrayList<Movie> GetUpcomingMovies(Context context)
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
                latestMovies.add(new Movie(latestMoviesArray.getJSONObject(upcomingCounter), context));
            }
        }
        catch (JSONException exception)
        {
            Log.d("CurrentMovieJSONFAIL", exception.toString());
        }

        return latestMovies;
    }

    /*
     * Function	: GetSearchResults()
     * Description	: Converts the JSON string held in SharedPreferences to an ArrayList for use in an activity.
     * Parameters   : N/A
     * Return:      : ArrayList<Movie> - The ArrayList of Movies converted from JSON
     */
    public ArrayList<Movie> GetSearchResults(Context context)
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
                latestMovies.add(new Movie(latestMoviesArray.getJSONObject(upcomingCounter), context));
            }
        }
        catch (JSONException exception)
        {
            Log.d("CurrentMovieJSONFAIL", exception.toString());
        }

        return latestMovies;
    }
}