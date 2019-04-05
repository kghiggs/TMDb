/*
 *
 * Author		: Kieron Higgs
 * Date			: Apr. 5th, 2019
 * Project		: Assignment 2
 * File			: JsonObjectRequest.java
 * Description	: Used to make an HTTP request for a JSON object and decode the respoonse.
 */

package com.example.k13r0.TMDb_Project.Utilities;

import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;

/*
 * Class		: JsonObjectRequest
 * Description	: This class is used to create HTTP-based requests for JSON data and display the result.
 */
public class JsonObjectRequest extends JsonRequest<JSONObject>
{
    /*
     * Function		: JsonObjectRequest
     * Description	: Constructs a JSONObject
     * Parameters	: int method - Either GET or POST
     *                String url - The desired URL to send the request
     *                JSONObject jsonRequest - The JSONObject to hold the request
     *                Listener<JSONObject> listener - The Listener object with its defined response behaviour
     *                Response.ErrorListener errorListener - A second listener for detecting errors
     */
    public JsonObjectRequest(int method, String url, JSONObject jsonRequest, Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener, errorListener);
    }

    /*
     * Function		: parseNetworkResponse
     * Description	: Decodes the HTTP response from the database using the HttpHeaderParser class methods.
     * Parameters	: NetworkResponse response
     * Returns		: Response<JSONObject> - The JSON from the HTTP response.
     */
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response)
    {
        try
        {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException exception)
        {
            return Response.error(new ParseError(exception));
        }
        catch (JSONException exception)
        {
            return Response.error(new ParseError(exception));
        }
    }
}