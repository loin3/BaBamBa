package com.example.enclosedmusicshareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    final int USER = 0;
    final int ADMIN = 1;
    final int IMPROPER_ACCOUNT = -1;

    private EditText editTextForID;
    private EditText editTextForPassword;
    private Button logInButton;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextForID = findViewById(R.id.editTextForID);
        editTextForPassword = findViewById(R.id.editTextForPassword);
        logInButton = findViewById(R.id.logInButton);
        signInButton = findViewById(R.id.signInButton);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAuthorizedUser() == USER){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else if(checkAuthorizedUser() == ADMIN){
                    Intent intent = new Intent(getApplicationContext(), MainForAdminActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "아이디나 비번이 틀린듯", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int checkAuthorizedUser(){
        String id = editTextForID.getText().toString();
        String password = editTextForPassword.getText().toString();

        if(checkProperLengthID(id) == false || checkProperLengthPassword(password) == false){
            return IMPROPER_ACCOUNT;
        }

        //id, password를 가지고 해당 유저가 맞는지 확인해야됨
        //일반 유저일 경우 0, 관리자일 경우 1, 틀리면 -1 반환
        return 0;
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

}