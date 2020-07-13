package com.example.enclosedmusicshareapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainForAdminActivity extends AppCompatActivity {

    long keyPressTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_admin);

        Button goToPlayer = (Button) findViewById(R.id.goToPlayer2);
        Button logout = (Button) findViewById(R.id.logout2);

        goToPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MusicPlayingActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast toast = Toast.makeText(getApplicationContext(), "'뒤로'버튼을 한번 더 누르면 종료", Toast.LENGTH_SHORT);
        if(System.currentTimeMillis() > keyPressTime + 2000){
            keyPressTime = System.currentTimeMillis();
            toast.show();
        }
        else{
            toast.cancel();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }else{
                ActivityCompat.finishAffinity(this);
            }
            System.runFinalization();
            System.exit(0);
        }
    }
}
