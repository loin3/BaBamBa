package com.example.enclosedmusicshareapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.enclosedmusicshareapp.MusicPlayingActivity.songList;

public class ServerCommunicator {

    private static RequestQueue requestQueue;
    private Context context;
    private String baseURL = "https://codewear-musicplayer.herokuapp.com";
    private ProgressBarDisplayer progressBarDisplayer;

    public int statusCode = 0;
    private int status = 0;

    public ServerCommunicator(Context context){
        this.context = context;
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }
        progressBarDisplayer = new ProgressBarDisplayer(context);
    }

    public void getSongListFromServer(final ListviewAdapter listviewAdapter){
        progressBarDisplayer.showDialog();

        songList.clear();

        String RestAPI= "/videolist";
        String url = baseURL + RestAPI;

        requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("asdf", response.toString());
                try {
                    status = Integer.parseInt(response.get("status").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(statusCode == 200 && status == 200){
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ListviewItem listviewItem = new ListviewItem(jsonObject.getString("key"), jsonObject.getString("name"));
                            Log.d("asdf2", listviewItem.toString());
                                songList.add(listviewItem);
                                listviewAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if(statusCode == 200 && status == 204){
                    Toast.makeText(context, "노래가 하나도 없네", Toast.LENGTH_SHORT).show();
                }

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

    public void addSongToServer(final String videoKey, final String videoName){
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

                try {
                    status = Integer.parseInt(response.get("status").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(statusCode == 200 && status == 201){
                    songList.add(new ListviewItem(videoName, videoKey));
                    ((AddMusicActivity)context).setResultAndFinish(status);

                }else if(statusCode == 200 && status ==208){
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

    public void deleteSongFromServer(final int position, final ListviewAdapter listviewAdapter){
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
                try {
                    status = Integer.parseInt(response.get("status").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(statusCode == 200 && status == 200 || status == 404){
                    Log.d("asdf", status+"");
                    Toast.makeText(context, "삭제완료", Toast.LENGTH_SHORT).show();
                    songList.remove(position);
                    listviewAdapter.notifyDataSetChanged();
                }

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
                progressBarDisplayer.hideDialog();

                try {
                    status = Integer.parseInt(response.get("status").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(statusCode == 200 && status == 200){
                    SharedPreferences sharedPreferences = context.getSharedPreferences("IdCache", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id", id);
                    editor.putString("password", password);
                    editor.putLong("data", System.currentTimeMillis());
                    editor.commit();

                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "아이디나 비번이 틀린듯", Toast.LENGTH_SHORT).show();
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

                try {
                    status = Integer.parseInt(response.get("status").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(statusCode == 200 && status == 200){
                    Toast.makeText(context, "회원가입성공", Toast.LENGTH_SHORT).show();
                    ((SignUpActivity)context).finish();
                }else{
                    Toast.makeText(context, "회원가입실패", Toast.LENGTH_SHORT).show();
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
                progressBarDisplayer.hideDialog();

                try {
                    status = Integer.parseInt(response.get("status").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(statusCode == 200 && status == 200){
                    ((SignUpActivity)context).idAvailable = 1;
                    ((SignUpActivity)context).checkIdTextView.setVisibility(View.VISIBLE);
                    ((SignUpActivity)context).checkIdTextView.setText("사용가능");
                    ((SignUpActivity)context).checkIdTextView.setTextColor(Color.GREEN);
                }else if(statusCode == 200 && status == 400){
                    ((SignUpActivity)context).idAvailable = 0;
                    ((SignUpActivity)context).checkIdTextView.setVisibility(View.VISIBLE);
                    ((SignUpActivity)context).checkIdTextView.setText("사용불가");
                    ((SignUpActivity)context).checkIdTextView.setTextColor(Color.RED);
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
}
