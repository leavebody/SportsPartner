package com.sportspartner.util;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 *  Once you get the NetworkResponseRequest object:
 *  This is status code: response.statusCode
 *  This is string response: NetworkResponseRequest.parseToString(response)
 *  This is the JsonObject of the returned body: NetworkResponseRequest.parseToJsonObject(response)
 *
 * @author Xiaochen Li
 */

public class NetworkResponseRequest extends Request<NetworkResponse> {


    /** Charset for request. */
    private static final String PROTOCOL_CHARSET = "utf-8";
    /** Content type for request. */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private final Response.Listener<NetworkResponse> mListener;
    private final String mRequestBody;

    public NetworkResponseRequest(int method, String url, String requestBody, Response.Listener<NetworkResponse> listener,
                                  Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        mRequestBody = requestBody;
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public byte[] getBody() {
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }

    /**
     * Parse the response to string
     * @param response
     * @return The parsed string
     */
    public static String parseToString(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return parsed;
    }

    /**
     * Parse the response to JSONObject.
     * @param response
     * @return The parsed object
     * @throws JSONException
     * @deprecated please use Gson in this project.
     */
    public static JSONObject parseToJSONObject(NetworkResponse response) throws JSONException {
        String parsedString = new String(response.data);

        JSONObject jo = new JSONObject(parsedString);
        System.out.println(parsedString);
        return jo;
    }

    /**
     * Parse the response to JsonObject.
     * @param response
     * @return The parsed object
     */
    public static JsonObject parseToJsonObject(NetworkResponse response) {
        String parsedString = new String(response.data);
        Gson gson = new Gson();
        JsonObject convertedObject = gson.fromJson(parsedString, JsonObject.class);
        return convertedObject;
    }


}
