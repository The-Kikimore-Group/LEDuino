package com.kikimore.leduino;

import android.content.Context;
import android.database.Cursor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static openDBHelper dBhelper;
    private static WifiInfo mWifiInfo;
    private static ArrayList<String> id, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        id = new ArrayList<>();
        title = new ArrayList<>();
        dBhelper = new openDBHelper(this);
        initcv();


        WifiManager mWifiManager = (WifiManager)
                getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
        Log.e("IP in Mask Integer", mWifiInfo.getIpAddress() + "");
        Log.e("IP Address", intToIP(mWifiInfo.getIpAddress()) + "");


        String sub = mWifiInfo.getSSID();

        //NetWork.net();
    }

    public static String intToIP(int i) {
        return ((i & 0xFF) + "." + ((i >> 8) & 0xFF) +
                "." + ((i >> 16) & 0xFF) + "." + ((i >> 24) & 0xFF));
    }

    public static String getSsid() {
        return mWifiInfo.getSSID();
    }

    public static String getMyOwnIP() {
        return intToIP(mWifiInfo.getIpAddress());
    }

    public static void initcv() {
        Cursor cursor = dBhelper.cursor();

        while (cursor.moveToNext()) {
            id.add(cursor.getString(0));
            title.add(cursor.getString(1));
        }
    }
}


