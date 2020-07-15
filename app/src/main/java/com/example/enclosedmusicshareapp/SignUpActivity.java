package com.example.enclosedmusicshareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    public int idAvailable = -1;
    private EditText idEditText, passwordEditText;
    private TextView idTextView, passwordTextView;
    public TextView checkIdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        idEditText = findViewById(R.id.idEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        idTextView = findViewById(R.id.idTextView);
        passwordTextView = findViewById(R.id.passwordTextView);

        checkIdTextView = findViewById(R.id.checkIdTextView);

        idEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(checkProperLengthID(idEditText.getText().toString()) == false){
                    idTextView.setTextColor(Color.RED);
                }else{
                    idTextView.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(checkProperLengthID(passwordEditText.getText().toString()) == false){
                    passwordTextView.setTextColor(Color.RED);
                }else{
                    passwordTextView.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button checkIdButton = findViewById(R.id.checkIdButton);
        checkIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkProperLengthID(idEditText.getText().toString()) == true){
                    ServerCommunicator serverCommunicator = new ServerCommunicator(SignUpActivity.this);
                    serverCommunicator.checkDuplicateUserToServer(idEditText.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "아이디를 확인해라", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idAvailable != 1){
                    Toast.makeText(getApplicationContext(), "아이디를 확인해라", Toast.LENGTH_SHORT).show();
                    idEditText.requestFocus();
                }else if(checkProperLengthPassword(passwordEditText.getText().toString()) == false){
                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해라", Toast.LENGTH_SHORT).show();
                    passwordEditText.requestFocus();
                }else{
                    ServerCommunicator serverCommunicator = new ServerCommunicator(SignUpActivity.this);
                    serverCommunicator.signUp(idEditText.getText().toString(), passwordEditText.getText().toString());
                }
            }
        });
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
