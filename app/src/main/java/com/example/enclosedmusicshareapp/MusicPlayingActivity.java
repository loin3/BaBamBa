package com.example.enclosedmusicshareapp;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MusicPlayingActivity extends AppCompatActivity {
    private ListviewAdapter listviewAdapter;
    private ServerCommunicator serverCommunicator;
    private SongList songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playing);

        serverCommunicator = new ServerCommunicator(MusicPlayingActivity.this);
        songList = SongList.getInstance();

        ListView listView = findViewById(R.id.listView);
        listviewAdapter = new ListviewAdapter();
        listView.setAdapter(listviewAdapter);

        serverCommunicator.getSongListFromServer(listviewAdapter);

        setDefaultTextOnPlayer();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                showClickedYoutube(position);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                showDeleteDialog(position);

                return true;
            }
        });

        Button addButton = findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMusicActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

        Button refreshButton = findViewById(R.id.button4);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverCommunicator.getSongListFromServer(listviewAdapter);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == 201){
            listviewAdapter.notifyDataSetChanged();
        }
    }

    private void setDefaultTextOnPlayer(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.frameForFragment, BlankYoutubeViewFragment.newInstance()).commit();
    }

    private void showClickedYoutube(int position){
        YoutubeFragment f = YoutubeFragment.newInstance(position);
        f.setAdapter(listviewAdapter);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameForFragment, f).commit();
    }

    private void showDeleteDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(MusicPlayingActivity.this);
        builder.setTitle("삭제");
        ListviewItem item = songList.getSongFromList(position);
        builder.setMessage(item.getTitle() + " : 해당 음악을 리스트에서 제거할거에요?");
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                serverCommunicator.deleteSongFromServer(position, listviewAdapter);
            }
        });
        builder.create().show();
    }
}
