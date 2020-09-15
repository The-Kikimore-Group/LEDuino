package com.kikimore.leduino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

public class add_device extends AppCompatActivity {

    private EditText addname;
    private Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        addname = findViewById(R.id.addDeviceName);
        addButton = findViewById(R.id.addDevice);

        final RecyclerView.Adapter adapter = new RVdeviceListAdapter(this);

        final openDBHelper dBhelper = new openDBHelper(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dBhelper.addInfo(addname.getText().toString());
                adapter.notifyDataSetChanged();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
