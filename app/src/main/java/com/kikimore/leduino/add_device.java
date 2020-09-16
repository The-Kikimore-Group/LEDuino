package com.kikimore.leduino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kikimore.leduino.ui.home.HomeFragment;
import com.kikimore.leduino.ui.home.HomeViewModel;

import java.util.List;

public class add_device extends AppCompatActivity {

    private EditText addname;
    private Button addButton;
    private WifiManager mWifiManager;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        addname = findViewById(R.id.addDeviceName);
        addButton = findViewById(R.id.addDevice);
        tv1 = findViewById(R.id.WifiDeviceInfo);

        mWifiManager = (WifiManager)
                getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();

        StringBuffer stringBuffer = new StringBuffer();
        List<ScanResult> list = mWifiManager.getScanResults();
        for(ScanResult scanResult: list){
            stringBuffer.append(scanResult);
        }
        tv1.setText(stringBuffer);

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
