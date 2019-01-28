// FILENAME:
// PROJECT:  PROG3150_MAD - Assignment #1
// BY: Kieron Higgs
// DATE: 01-24-2019
// DESCRIPTION:

package com.example.k13r0.TMDb_Project.Classes;

import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;


import com.android.volley.Request;

public class JsonObjectRequest extends JsonRequest<JSONObject>
{
    public JsonObjectRequest(int method, String url, JSONObject jsonRequest, Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response)
    {
        try
        {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }
        catch (JSONException je)
        {
            return Response.error(new ParseError(je));
        }
    }
}