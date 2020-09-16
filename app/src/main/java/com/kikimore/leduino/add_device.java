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
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kikimore.leduino.ui.home.HomeFragment;
import com.kikimore.leduino.ui.home.HomeViewModel;

import java.util.List;

public class add_device extends AppCompatActivity {

    private EditText addname, ip;
    private Button addButton;
    private WifiManager mWifiManager;
    private TextView tv1,pbtv;
    private ProgressBar pb;
    private Spinner devicesp;
    private CheckBox chbmanual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        setTitle("Добавление устройства");

        addname = findViewById(R.id.addDeviceName);
        addButton = findViewById(R.id.addDevice);
        tv1 = findViewById(R.id.WifiDeviceInfo);
        pbtv = findViewById(R.id.Progresstv);
        pb = findViewById(R.id.progressBar);
        devicesp = findViewById(R.id.DeviceAddingSp);
        chbmanual = findViewById(R.id.checkBoxManual);
        ip = findViewById(R.id.ed_ip);

        chbmanual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chbmanual.isChecked()){
                    devicesp.setVisibility(View.INVISIBLE);
                    ip.setVisibility(View.VISIBLE);
                }else{
                    ip.setVisibility(View.INVISIBLE);
                    devicesp.setVisibility(View.VISIBLE);
                }
            }
        });

        ip.setText("http://192.168.1.1/?");

        final get_request get_request = new get_request();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pb.setVisibility(View.VISIBLE);
                pbtv.setVisibility(View.VISIBLE);

                pbtv.setText("Инициализация адаптера...");

                RecyclerView.Adapter adapter = new RVdeviceListAdapter(getApplicationContext());
                openDBHelper dBhelper = new openDBHelper(getApplicationContext());


                pbtv.setText("Проверка устройства...");

                try{
                    if (get_request.deviceChecking(String.valueOf(ip.getText()))) {
                        pbtv.setText("Проверка прошла успешно!");

                        pbtv.setText("Добавление информации...");

                        dBhelper.addInfo(addname.getText().toString());
                        HomeFragment.ref(getApplicationContext());
                        //adapter.notifyItemInserted(0);

                        pbtv.setText("Закрытие активности...");
                        close();
                    } else {
                        pbtv.setText("Ошибка при проверке устройства!");
                    }
                }catch (Exception e){
                    pbtv.setText("Ошибка при проверке устройства!");
                }

                pbtv.setText("Добавление информации...");

                dBhelper.addInfo(addname.getText().toString());
                HomeFragment.ref(getApplicationContext());

                pbtv.setText("Закрытие активности...");
                close();
            }
        });

        
    }
    private void close(){
        this.finish();
    }

}
