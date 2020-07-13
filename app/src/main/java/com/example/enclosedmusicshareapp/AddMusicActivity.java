package com.example.enclosedmusicshareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class AddMusicActivity extends AppCompatActivity {

    String link;
    String title;
    int statusCode;
    final int ADD_MUSIC_SUCCESS = 1;
    final int DUPLICATED_MUSIC = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);

        final ServerCommunicator serverCommunicator = new ServerCommunicator(this);

        final EditText editText = findViewById(R.id.editText);
        Button okayButton = findViewById(R.id.button2);
        Button cancelButton = findViewById(R.id.button3);

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link = editText.getText().toString();

                String normalizedLink =  normalizeLine(link);
                Thread thread = new Thread(){
                    public void run(){
                        HtmlController htmlController = new HtmlController();
                        title = htmlController.getTitleFromUrl(link);
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                serverCommunicator.addSongToServer(link, title);
                while(true){
                    if(statusCode == ADD_MUSIC_SUCCESS){
                        break;
                    }else if(statusCode == DUPLICATED_MUSIC){
                        break;
                    }
                }

                Intent intent = new Intent();
                intent.putExtra("link", normalizedLink);
                intent.putExtra("title", title);
                setResult(serverCommunicator.statusCode, intent);
                finish();
                }

        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public String normalizeLine(String link){
        String normalizedLink;

        int location = link.indexOf("v=");
        if(location == -1){
            normalizedLink = link.substring(17);
        }else{
            normalizedLink = link.substring(location+2);
        }

        return normalizedLink;
    }

}
