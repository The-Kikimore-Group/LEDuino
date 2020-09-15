package com.kikimore.leduino;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kikimore.leduino.ui.home.HomeFragment;

import java.util.ArrayList;


public class RVdeviceListAdapter extends RecyclerView.Adapter<RVdeviceListAdapter.deviceListView> {
    Context context;
    private static openDBHelper dBhelper;
    public RVdeviceListAdapter(Context context) {
        this.context = context;
    }
    public static ArrayList<String> id = new ArrayList<>(), title = new ArrayList<>();

    @NonNull
    @Override
    public deviceListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int layoutidforlistitem = R.layout.device_listview;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutidforlistitem, parent, false);

        deviceListView listView = new deviceListView(view);


        return listView;
    }

    @Override
    public void onBindViewHolder(@NonNull final deviceListView holder, final int position) {

         holder.nameOfDevice.setText(title.get(position));
         holder.SSID.setText("SSID: " + MainActivity.getSsid());
         holder.IP.setText("IP: " + MainActivity.getMyOwnIP());

         holder.delDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final openDBHelper dbHelper = new openDBHelper(context);

                    dbHelper.deleteData(id.get(position));

                    initcv(context);

                    int newPosition = holder.getAdapterPosition();

                    dBhelper = new openDBHelper(context);

                    Toast.makeText(context, String.valueOf(id.size()), Toast.LENGTH_SHORT)
                            .show();

                    notifyItemRemoved(newPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class deviceListView extends RecyclerView.ViewHolder {

        TextView nameOfDevice, IP;
        TextView SSID;
        LinearLayout delDevice;

        public deviceListView(@NonNull final View itemView) {
            super(itemView);

            initcv(context);

            nameOfDevice = itemView.findViewById(R.id.nameOfDevice);
            delDevice = itemView.findViewById(R.id.delDevice);
            SSID = itemView.findViewById(R.id.SSID);
            IP = itemView.findViewById(R.id.ipadress);
        }
    }
    public void initcv(Context context) {
            dBhelper = new openDBHelper(context);

            Cursor cursor = dBhelper.cursor();

            id.clear();
            title.clear();

            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                title.add(cursor.getString(1));
            }
            Toast.makeText(context, String.valueOf(id.size()), Toast.LENGTH_SHORT).show();
    }
}
