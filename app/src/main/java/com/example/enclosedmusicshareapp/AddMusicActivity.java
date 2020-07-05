package com.example.enclosedmusicshareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMusicActivity extends AppCompatActivity {

    String link;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);

        final EditText editText = findViewById(R.id.editText);
        Button okayButton = findViewById(R.id.button2);
        Button cancelButton = findViewById(R.id.button3);

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link = editText.getText().toString();

                String normalizedLink =  normalizeLine(link);
                if(checkDuplicatedSong(normalizedLink) == true){
                    Toast.makeText(getApplicationContext(), "이미 존재하는 노래임", Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(){
                        public void run(){
                            HtmlController htmlController = new HtmlController();
                            title = htmlController.getTitleFromUrl(link);
                        }
                    }.start();

                    while(title == null){
                    }

                    Intent intent = new Intent();
                    intent.putExtra("link", normalizedLink);
                    intent.putExtra("title", title);
                    setResult(1000, intent);
                    finish();
                }
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

    public boolean checkDuplicatedSong(String url){
        for(int i = 0; i < MainActivity.songList.size(); i++){
            if(MainActivity.songList.get(i).getUrl().equals(url)){
                return true;
            }
        }
        return false;
    }
}
