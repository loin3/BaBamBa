package com.example.enclosedmusicshareapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class MusicPlayingActivity extends AppCompatActivity {
    private ListviewAdapter listviewAdapter;
    private RequestQueue requestQueue;

    public static ArrayList<ListviewItem> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playing);

        songList = new ArrayList<>();
        getSongList();

        setDefaultTextOnPlayer();

        ListView listView = findViewById(R.id.listView);
        listviewAdapter = new ListviewAdapter(songList);
        listView.setAdapter(listviewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                YoutubeFragment f = YoutubeFragment.newInstance(position);
                f.setAdapter(listviewAdapter);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameForFragment, f).commit();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MusicPlayingActivity.this);
                builder.setTitle("삭제");
                builder.setMessage(songList.get(position).getTitle() + " : 해당 음악을 리스트에서 제거할거에요?");
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        songList.remove(position);
                        listviewAdapter.notifyDataSetChanged();
                    }
                });
                builder.create().show();

                return true;
            }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMusicActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == 1000){
            String title = data.getStringExtra("title");
            String addUrl = data.getStringExtra("link");

            songList.add(new ListviewItem(title, addUrl));
            listviewAdapter.notifyDataSetChanged();
        }
    }

    public void getSongList(){
        songList.add(new ListviewItem("제목", "86gRJTK-vgo"));
        songList.add(new ListviewItem("제목2", "ru-O5L2uxho"));

//        String url= " ";
//        requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject jsonObject = response.getJSONObject(i);
//
//                        ListviewItem listviewItem = new ListviewItem(jsonObject.getString("title"), jsonObject.getString("url"));
//                        songList.add(listviewItem);
//                        listviewAdapter.notifyDataSetChanged();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(jsonArrayRequest);

    }

    private void setDefaultTextOnPlayer(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.frameForFragment, BlankYoutubeViewFragment.newInstance()).commit();
    }
}
