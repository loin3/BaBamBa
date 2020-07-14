package com.example.enclosedmusicshareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

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

                String id = editTextForID.getText().toString();
                String password = editTextForPassword.getText().toString();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

//                ServerCommunicator serverCommunicator = new ServerCommunicator(LoginActivity.this);
//                serverCommunicator.signIn(id, password);
//
//                if(serverCommunicator.statusCode == 200){
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                }else if(checkIfUserWriteProperly(id, password) == -1 || serverCommunicator.statusCode == 1000){
//                    Toast.makeText(getApplicationContext(), "아이디나 비번이 틀린듯", Toast.LENGTH_SHORT).show();
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

}