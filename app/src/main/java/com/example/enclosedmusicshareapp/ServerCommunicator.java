package com.example.enclosedmusicshareapp;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.enclosedmusicshareapp.MusicPlayingActivity.songList;

public class ServerCommunicator {

    private static RequestQueue requestQueue;
    private Context context;
    private String baseURL = "";

    public int statusCode = 0;

    public ServerCommunicator(Context context){
        this.context = context;
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }
    }

    public void getSongListFromServer(){
        songList = new ArrayList<>();
        songList.add(new ListviewItem("제목", "86gRJTK-vgo"));
        songList.add(new ListviewItem("제목2", "ru-O5L2uxho"));

        String RestAPI= "/video/videos";
        String url = baseURL + RestAPI;

        requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null ,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        ListviewItem listviewItem = new ListviewItem(jsonObject.getString("videoKey"), jsonObject.getString("videoName"));
                        songList.add(listviewItem);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(jsonArrayRequest);
    }

    public void addSongToServer(String videoKey, String videoName){
        String RestAPI= "/video/videos";
        String url = baseURL + RestAPI;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("videoKey", videoKey);
            jsonObject.put("videoName", videoName);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(statusCode == 200){

                }else if(statusCode == 1000){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public void deleteSongFromServer(String videoKey, String videoName){
        String RestAPI = "/video";
        String url = baseURL + RestAPI;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("videoKey", videoKey);
            jsonObject.put("videoName", videoName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
}
