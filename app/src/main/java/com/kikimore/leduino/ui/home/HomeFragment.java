package com.kikimore.leduino.ui.home;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kikimore.leduino.MainActivity;
import com.kikimore.leduino.R;
import com.kikimore.leduino.RVdeviceListAdapter;
import com.kikimore.leduino.add_device;
import com.kikimore.leduino.openDBHelper;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel dashboardViewModel;
    private static RecyclerView rv;
    private FloatingActionButton fab;
    private static RVdeviceListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                rv = root.findViewById(R.id.deviceInfo);
                fab = root.findViewById(R.id.AddButton);

                adapter = new RVdeviceListAdapter(getContext());
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                rv.setAdapter(adapter);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), add_device.class);
                        startActivity(intent);
                    }
                });

            }
        });
        return root;
    }

    public static void remove(int position) {
        rv.removeViewAt(position);
    }
}