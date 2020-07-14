package com.example.enclosedmusicshareapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
    private ProgressBarDisplayer progressBarDisplayer;

    public int statusCode = 0;

    public ServerCommunicator(Context context){
        this.context = context;
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }
        progressBarDisplayer = new ProgressBarDisplayer(context);
    }

    public void getSongListFromServer(){
        Log.d("asdf", "1");
        progressBarDisplayer.showDialog();

        songList = new ArrayList<>();
        songList.add(new ListviewItem("제목", "86gRJTK-vgo"));
        songList.add(new ListviewItem("제목2", "ru-O5L2uxho"));

        String RestAPI= "/videolist";
        String url = baseURL + RestAPI;

        requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null ,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(statusCode == 200){
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            ListviewItem listviewItem = new ListviewItem(jsonObject.getString("videoKey"), jsonObject.getString("videoName"));
                            songList.add(listviewItem);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else if(statusCode == 204){
                    Toast.makeText(context, "노래가 하나도 없네", Toast.LENGTH_SHORT).show();
                }

                progressBarDisplayer.hideDialog();
                Log.d("asdf", "2");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarDisplayer.hideDialog();
                if(error.networkResponse != null){
                    statusCode = error.networkResponse.statusCode;
                }
                Log.d("asdf", "3");
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
        progressBarDisplayer.showDialog();

        String RestAPI= "/video";
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
                progressBarDisplayer.hideDialog();
                if(statusCode == 201){
                    try {
                        String videoKey = response.get("vedioKey").toString();
                        String videoName = response.get("videoName").toString();
                        songList.add(new ListviewItem(videoName, videoKey));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else if(statusCode == 1000){
                    Toast.makeText(context, "이미 있는 노래임", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarDisplayer.hideDialog();
                if(error.networkResponse != null){
                    statusCode = error.networkResponse.statusCode;
                }
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

    public void deleteSongFromServer(int position){
        progressBarDisplayer.showDialog();

        String RestAPI = "/video";
        String url = baseURL + RestAPI;

        String videoKey = songList.get(position).getUrl();
        String videoName = songList.get(position).getTitle();

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
                progressBarDisplayer.hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarDisplayer.hideDialog();
                if(error.networkResponse != null){
                    statusCode = error.networkResponse.statusCode;
                }
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

    public void signIn(String id, String password){
        progressBarDisplayer.showDialog();

        String RestAPI = "/user/signin";
        String url = baseURL + RestAPI;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBarDisplayer.hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarDisplayer.hideDialog();
                if(error.networkResponse != null){
                    statusCode = error.networkResponse.statusCode;
                }
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

    public void signUp(String id, String password){
        progressBarDisplayer.showDialog();

        String RestAPI = "/user/signup";
        String url = baseURL + RestAPI;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBarDisplayer.hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarDisplayer.hideDialog();
                if(error.networkResponse != null){
                    statusCode = error.networkResponse.statusCode;
                }
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
