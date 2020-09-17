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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kikimore.leduino.ui.home.HomeFragment;

import java.util.ArrayList;


public class RVdeviceListAdapter extends RecyclerView.Adapter<RVdeviceListAdapter.deviceListView> {
    Context context;
    private FirebaseAuth mAuth;
    public RVdeviceListAdapter(Context context) {
        this.context = context;
    }
    public static ArrayList<String> id = new ArrayList<>(), title = new ArrayList<>(),
        devicetype = new ArrayList<>(), uid = new ArrayList<>();

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
         holder.SSID.setText("UID: " + uid.get(position));
         holder.IP.setText("ID: " + id.get(position));
         holder.typeofdevice.setText("Тип устройства: " + devicetype.get(position));

         holder.delDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 DatabaseReference delDB =
                         FirebaseDatabase.getInstance().getReference("Devices").child(id.get(position));

                 delDB.removeValue();

                    initcv(context);

                    int newPosition = holder.getAdapterPosition();

                    notifyItemRemoved(newPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class deviceListView extends RecyclerView.ViewHolder {

        TextView nameOfDevice, IP, typeofdevice;
        TextView SSID;
        LinearLayout delDevice;

        public deviceListView(@NonNull final View itemView) {
            super(itemView);

            initcv(context);

            nameOfDevice = itemView.findViewById(R.id.nameOfDevice);
            delDevice = itemView.findViewById(R.id.delDevice);
            SSID = itemView.findViewById(R.id.SSID);
            IP = itemView.findViewById(R.id.ipadress);
            typeofdevice = itemView.findViewById(R.id.typeOfDevice);
        }
    }
    public void initcv(Context context) {

        mAuth = FirebaseAuth.getInstance();

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Devices").child(mAuth.getUid());

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    id.clear();
                    title.clear();
                    devicetype.clear();
                    uid.clear();
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        Device device = ds.getValue(Device.class);
                        assert  device != null;
                        id.add(device.id);
                        title.add(device.name);
                        devicetype.add(device.devicetype);
                        uid.add(device.uid);
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

    }
}
