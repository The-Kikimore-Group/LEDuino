package com.kikimore.leduino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kikimore.leduino.ui.home.HomeFragment;
import com.kikimore.leduino.ui.home.HomeViewModel;

public class add_device extends AppCompatActivity {

    private EditText addname;
    private Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        addname = findViewById(R.id.addDeviceName);
        addButton = findViewById(R.id.addDevice);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.Adapter adapter = new RVdeviceListAdapter(getApplicationContext());
                openDBHelper dBhelper = new openDBHelper(getApplicationContext());

                dBhelper.addInfo(addname.getText().toString());
                HomeFragment.ref(getApplicationContext());
                //adapter.notifyItemInserted(0);
                close();
            }
        });

    }
    private void close(){
        this.finish();
    }
}
