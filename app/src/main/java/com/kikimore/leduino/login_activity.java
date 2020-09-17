package com.kikimore.leduino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.common.SignInButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class login_activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailed, passed;
    private Button loginbt, regbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        setTitle("Вход в аккаунт");

        emailed = findViewById(R.id.EmailEd);
        passed = findViewById(R.id.PassEd);
        loginbt = findViewById(R.id.LoginButton);
        regbt = findViewById(R.id.RegButton);

        mAuth = FirebaseAuth.getInstance();

            loginbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signInWithEmailAndPassword(emailed.getText().toString(),
                            passed.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }
            });
            regbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.createUserWithEmailAndPassword(emailed.getText().toString(),
                            passed.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            regbt.setText("Регистрация прошла успешно");
                        }
                    });
                }
            });
    }
}
