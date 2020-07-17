package com.example.enclosedmusicshareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextForID;
    private EditText editTextForPassword;
    private Button logInButton;
    private Button signInButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(checkValidCaching() == true){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        editTextForID = findViewById(R.id.editTextForID);
        editTextForPassword = findViewById(R.id.editTextForPassword);
        logInButton = findViewById(R.id.logInButton);
        signInButton = findViewById(R.id.signInButton);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = editTextForID.getText().toString();
                String password = editTextForPassword.getText().toString();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

//                if(checkProperLengthID(id) == true){
//                    ServerCommunicator serverCommunicator = new ServerCommunicator(LoginActivity.this);
//                    serverCommunicator.signIn(id, password);
//                }
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public int checkIfUserWriteProperly(String id, String password){
        if(checkProperLengthID(id) == false || checkProperLengthPassword(password) == false){
            return -1;
        }else{
            return 0;
        }
    }

    private boolean checkProperLengthID(String id){
        if(id.length() >= 3 && id.length() < 25){
            return true;
        }else{
            return false;
        }
    }

    private boolean checkProperLengthPassword(String password){
        if(password.length() >= 8 && password.length() < 25){
            return true;
        }else{
            return false;
        }
    }

    private boolean checkValidCaching(){
        sharedPreferences = getSharedPreferences("IdCache", Context.MODE_PRIVATE);
        String cachedID = sharedPreferences.getString("id", "null");
        String cachedPassword = sharedPreferences.getString("password", "null");
        long cachedTime = sharedPreferences.getLong("data", 0);

        if(cachedID != null && cachedPassword != null & System.currentTimeMillis() - cachedTime < 15552000){
            return true;
        }else{
            return false;
        }
    }

}