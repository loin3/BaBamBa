package com.example.enclosedmusicshareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

                new Thread(){
                    public void run(){
                        HtmlController htmlController = new HtmlController();
                        title = htmlController.getTitleFromUrl(link);
                    }
                }.start();

                if(title == null){
                    synchronized (this){
                        try {
                            wait(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Intent intent = new Intent();
                intent.putExtra("link", link);
                intent.putExtra("title", title);
                setResult(1000, intent);
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
}
