package com.example.finalproject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class API {
    public interface ApiCallback {
        void onMoviesLoaded(ArrayList<Movie> movies);
    }

    private static final String API_KEY = "********************************"; //32 character alphanumeric key from signup
    private static final String SEARCH_START_URL = "https://api.themoviedb.org/3/search/movie";
    private static final String SEARCH_END_URL = "&language=en-US&page=1&include_adult=false";
    private static final String DISC_START_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String DISC_END_URL = "&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate";
    private Context context;

    public API(Context context) {
        this.context = context;
    }

    public void discoverAPI(final ApiCallback callback) {
        String url = DISC_START_URL + "?api_key=" + API_KEY + DISC_END_URL;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<Movie> movieList = new ArrayList<>();
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject movieObj = results.getJSONObject(i);
                                String posterPath = movieObj.getString("poster_path");
                                String title = movieObj.getString("title");
                                String overview = movieObj.getString("overview");
                                String releaseDate = movieObj.getString("release_date");
                                String id = movieObj.getString("id");
                                Movie movie = new Movie(posterPath, title, overview, releaseDate, id);
                                movieList.add(movie);
                            }
                            callback.onMoviesLoaded(movieList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    public void searchAPI(String query, final ApiCallback callback) {
        String url = SEARCH_START_URL + "?api_key=" + API_KEY + SEARCH_END_URL + "&query=" + query;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<Movie> movieList = new ArrayList<>();
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject movieObj = results.getJSONObject(i);
                                String posterPath = movieObj.getString("poster_path");
                                String title = movieObj.getString("title");
                                String overview = movieObj.getString("overview");
                                String releaseDate = movieObj.getString("release_date");
                                String id = movieObj.getString("id");
                                Movie movie = new Movie(posterPath, title, overview, releaseDate, id);
                                movieList.add(movie);
                            }
                            callback.onMoviesLoaded(movieList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
