package com.kikimore.leduino;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class get_request {
    private static final String COLOR = "color";
    private static final String PARAMETER = "parameter";
    private String ip;
    private int pushcode;
    private String resp;

    public boolean deviceChecking(String ip)  {
        this.ip = ip;

        try {
            WifiCheckTask wifiCheckTask = new WifiCheckTask();
            wifiCheckTask.execute();
        }catch(Exception e){
            return false;
        }

        if(Integer.valueOf(resp) == 102){
            return true;
        }else{
            return false;
        }
    }


   public static URL URLConstructor(String ipadress, String param,  String parameter) {

        Uri builturi = Uri.parse(ipadress).buildUpon()
                .appendQueryParameter(COLOR, param)
                .appendQueryParameter(PARAMETER, parameter)
                .build();
        URL url = null;
        try {
            url = new URL(builturi.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    class WifiCheckTask extends AsyncTask<URL,Void,String> {

        private String ipadressln;
        private URL ipadressin;
        public String response;
        private int status;
        @Override
        protected void onPreExecute(){
        }
        @SuppressLint("WrongThread")
        protected String doInBackground(URL... urls) {
            ipadressin = URLConstructor(ip, null, "101");
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection)ipadressin.openConnection();
            } catch (Exception e) {
                e.printStackTrace();
                //cancel(true);
            }
            InputStream in = null;
            try {
                in = urlConnection.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("//A");
                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    response = scanner.next();
                } else {
                    response =  null;
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }
            return response;
        }
        protected void onPostExecute(String ipadressln){
            try {
                if (response == null) {
                    pushcode = 0;
                } else {
                    pushcode = 1;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            resp = response;
        }
    }
}
