package com.example.enclosedmusicshareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMusicActivity extends AppCompatActivity {

    private String link;
    private String title;

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

                if(title != null){
                    serverCommunicator.addSongToServer(new ListviewItem(title, normalizedLink));
                }else{
                    Toast.makeText(getApplicationContext(), "너 주소가 이상한디", Toast.LENGTH_SHORT).show();
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

    public void setResultAndFinish(int statusCode){
        Intent intent = new Intent();
        setResult(statusCode, intent);
        finish();
    }

}
