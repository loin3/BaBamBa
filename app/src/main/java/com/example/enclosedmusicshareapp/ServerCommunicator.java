package com.example.enclosedmusicshareapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerCommunicator {

    private static RequestQueue requestQueue;
    private Context context;
    private String baseURL = "https://codewear-musicplayer.herokuapp.com";
    private ProgressBarDisplayer progressBarDisplayer;
    private SongList songList;
    private int status = 0;

    public ServerCommunicator(Context context){
        this.context = context;
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }
        progressBarDisplayer = new ProgressBarDisplayer(context);
        songList = SongList.getInstance();
    }

    public void getSongListFromServer(final ListviewAdapter listviewAdapter){
        progressBarDisplayer.showDialog();

        songList.clearSongList();

        String RestAPI= "/videolist";
        String url = baseURL + RestAPI;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("get song response", response.toString());
                try {
                    status = Integer.parseInt(response.get("status").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(status == 200){
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ListviewItem listviewItem = new ListviewItem(jsonObject.getString("name"), jsonObject.getString("key"));
                            songList.addSongToList(listviewItem);
                            listviewAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if(status == 204){
                    Toast.makeText(context, "노래가 하나도 없네", Toast.LENGTH_SHORT).show();
                }

                progressBarDisplayer.hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("get song error", error.toString());
                progressBarDisplayer.hideDialog();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void addSongToServer(final ListviewItem item){
        progressBarDisplayer.showDialog();

        String RestAPI= "/video";
        String url = baseURL + RestAPI;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("videoKey", item.getUrl());
            jsonObject.put("videoName", item.getTitle());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("add song response", response.toString());

                progressBarDisplayer.hideDialog();

                try {
                    status = Integer.parseInt(response.get("status").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(status == 201){
                    songList.addSongToList(item);
                    ((AddMusicActivity)context).setResultAndFinish(status);

                }else if(status ==208){
                    Toast.makeText(context, "이미 있는 노래임", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("add song error", error.toString());

                progressBarDisplayer.hideDialog();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void deleteSongFromServer(final int position, final ListviewAdapter listviewAdapter){
        progressBarDisplayer.showDialog();

        ListviewItem song = songList.getSongFromList(position);
        String videoKey = song.getUrl();

        String RestAPI = "/video?";
        String url = baseURL + RestAPI + videoKey;
        Log.d("tlqkf2", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("delete song response", response.toString());

                try {
                    status = Integer.parseInt(response.get("status").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(status == 200 || status == 404){
                    Toast.makeText(context, "삭제완료", Toast.LENGTH_SHORT).show();
                    ListviewItem item = songList.getSongFromList(position);
                    songList.deleteSongFromList(item);
                    listviewAdapter.notifyDataSetChanged();
                }

                progressBarDisplayer.hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("delete song error", error.toString());

                progressBarDisplayer.hideDialog();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void signIn(final String id, final String password){
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
                Log.d("signin song response", response.toString());

                progressBarDisplayer.hideDialog();

                try {
                    status = Integer.parseInt(response.get("status").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(status == 200){
                    cachingAccount(id, password);

                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "아이디나 비번이 틀린듯", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("signin song error", error.toString());

                progressBarDisplayer.hideDialog();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void signUp(String id, String password){
        progressBarDisplayer.showDialog();

        String RestAPI = "/user";
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
                Log.d("signup response", response.toString());

                progressBarDisplayer.hideDialog();

                try {
                    status = Integer.parseInt(response.get("status").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(status == 200){
                    Toast.makeText(context, "회원가입성공", Toast.LENGTH_SHORT).show();
                    ((SignUpActivity)context).finish();
                }else{
                    Toast.makeText(context, "회원가입실패", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("signup response", error.toString());

                progressBarDisplayer.hideDialog();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void checkDuplicateUserToServer(String id){
        progressBarDisplayer.showDialog();

        String RestAPI = "/user/check";
        String url = baseURL + RestAPI;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("check song response", response.toString());

                progressBarDisplayer.hideDialog();

                try {
                    status = Integer.parseInt(response.get("status").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(status == 200){
                    ((SignUpActivity)context).idAvailable = 1;
                    ((SignUpActivity)context).checkIdTextView.setVisibility(View.VISIBLE);
                    ((SignUpActivity)context).checkIdTextView.setText("사용가능");
                    ((SignUpActivity)context).checkIdTextView.setTextColor(Color.GREEN);
                }else if(status == 400){
                    ((SignUpActivity)context).idAvailable = 0;
                    ((SignUpActivity)context).checkIdTextView.setVisibility(View.VISIBLE);
                    ((SignUpActivity)context).checkIdTextView.setText("사용불가");
                    ((SignUpActivity)context).checkIdTextView.setTextColor(Color.RED);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("check song response", error.toString());

                progressBarDisplayer.hideDialog();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void cachingAccount(String id, String password){
        SharedPreferences sharedPreferences = context.getSharedPreferences("IdCache", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", id);
        editor.putString("password", password);
        editor.putLong("data", System.currentTimeMillis());
        editor.commit();
    }
}
