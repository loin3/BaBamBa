package com.example.enclosedmusicshareapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class MainActivity extends YouTubeBaseActivity {

    final String APIKey = "AIzaSyBpNITvbs8PgrOXLXT4bw5cVvTsHtlYcqQ";

    private String url;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener listener;
    private ListView listView;
    private ListviewAdapter listviewAdapter;

    private ArrayList<ListviewItem> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songList = getSongList();

        listView = findViewById(R.id.listView);
        listviewAdapter = new ListviewAdapter(songList);
        listView.setAdapter(listviewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                url = songList.get(position).getUrl();

                youTubePlayerView = findViewById(R.id.youtubeView);
                listener = new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo(url);

                        youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                            @Override
                            public void onLoading() {

                            }

                            @Override
                            public void onLoaded(String s) {
                                youTubePlayer.play();
                            }

                            @Override
                            public void onAdStarted() {

                            }

                            @Override
                            public void onVideoStarted() {

                            }

                            @Override
                            public void onVideoEnded() {
                                int nextPosition = getNextSong(position);
                                String nextUrl = songList.get(nextPosition).getUrl();
                                youTubePlayer.loadVideo(nextUrl);
                            }

                            @Override
                            public void onError(YouTubePlayer.ErrorReason errorReason) {

                            }
                        });
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                };
                youTubePlayerView.initialize(APIKey, listener);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
            int location = addUrl.indexOf("v=");
            if(location == -1){
                addUrl = addUrl.substring(17);
            }else{
                addUrl = addUrl.substring(location+2);
            }

            songList.add(new ListviewItem(title, "가수3", addUrl));
            listviewAdapter.notifyDataSetChanged();
        }
    }

    public ArrayList<ListviewItem> getSongList(){
        ArrayList<ListviewItem> arrayList = new ArrayList<>();
        arrayList.add(new ListviewItem("제목", "가수", "86gRJTK-vgo"));
        arrayList.add(new ListviewItem("제목2", "가수2", "ru-O5L2uxho"));
        return arrayList;
    }

    public int getNextSong(int currentPosition){
        if(currentPosition == songList.size() - 1){
            return 0;
        }
        else{
            return currentPosition+1;
        }
    }
}
