package com.kikimore.leduino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RVdeviceListAdapter extends RecyclerView.Adapter<RVdeviceListAdapter.deviceListView> {
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
    public void onBindViewHolder(@NonNull deviceListView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class deviceListView extends RecyclerView.ViewHolder {
        public deviceListView(@NonNull View itemView) {
            super(itemView);
        }
    }
}
