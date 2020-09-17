package com.kikimore.leduino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kikimore.leduino.ui.home.HomeFragment;
import com.kikimore.leduino.ui.home.HomeViewModel;

import java.util.List;

public class add_device extends AppCompatActivity {

    private EditText addname;
    private Button addButton;
    private Spinner devicesp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        setTitle("Добавление устройства");

        addname = findViewById(R.id.addDeviceName);
        addButton = findViewById(R.id.addDevice);
        devicesp = findViewById(R.id.DeviceTypeSp);


        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.device_type, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        devicesp.setAdapter(adapter1);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Devices");


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RecyclerView.Adapter adapter = new RVdeviceListAdapter(getApplicationContext());

                String key = mDatabase.push().getKey();
                Device newDevice = new Device(addname.getText().toString(), "RGB Лента",
                        null, "10294", key);
                mDatabase.child(key).setValue(newDevice);

                HomeFragment.ref(getApplicationContext());

                Intent intent = new Intent(getApplicationContext(), ArduinoCheckup.class);
                intent.putExtra("key",  key);
                intent.putExtra("uid", "ЖОПА");
                startActivity(intent);

                close();
            }
        });

        
    }
    private void close(){
        this.finish();
    }

}
