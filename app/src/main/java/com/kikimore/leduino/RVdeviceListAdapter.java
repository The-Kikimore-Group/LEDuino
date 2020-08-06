package com.kikimore.leduino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kikimore.leduino.ui.home.HomeFragment;

import java.util.ArrayList;

public class RVdeviceListAdapter extends RecyclerView.Adapter<RVdeviceListAdapter.deviceListView> {
    Context context;
    public RVdeviceListAdapter(Context context) {
        this.context = context;
    }

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
    public void onBindViewHolder(@NonNull deviceListView holder, final int position) {
        final openDBHelper dbHelper = new openDBHelper(context);

        holder.nameOfDevice.setText(MainActivity.title.get(position));
       holder.delDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteData(MainActivity.id.get(position));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainActivity.title.size();
    }

    class deviceListView extends RecyclerView.ViewHolder {

        TextView nameOfDevice;
        LinearLayout delDevice;

        public deviceListView(@NonNull View itemView) {
            super(itemView);
            nameOfDevice = itemView.findViewById(R.id.nameOfDevice);
            delDevice = itemView.findViewById(R.id.delDevice);
        }
    }
}
