package com.kikimore.leduino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ArduinoCheckup extends AppCompatActivity {

    private String id, uid;
    private TextView idtv, uidtv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arduino_checkup);

        setTitle("Проверка устройства");

        idtv = findViewById(R.id.idCode);
        uidtv = findViewById(R.id.uidCode);

        Intent intent = getIntent();
        idtv.setText(intent.getStringExtra("key"));
        uidtv.setText(intent.getStringExtra("uid"));
    }
}
