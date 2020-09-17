package com.kikimore.leduino.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kikimore.leduino.R;
import com.kikimore.leduino.RVdeviceListAdapter;
import com.kikimore.leduino.add_device;

public class HomeFragment extends Fragment {

    private HomeViewModel dashboardViewModel;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private static RVdeviceListAdapter adapter;
    private SwipeRefreshLayout swp;


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
                swp = root.findViewById(R.id.refresh);

                adapter = new RVdeviceListAdapter(getContext());
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                rv.setAdapter(adapter);
                adapter.initcv(getContext());



                swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        adapter.initcv(getContext());
                        adapter.notifyDataSetChanged();
                        swp.setRefreshing(false);
                    }
                });

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
    public static void ref(Context context){
        adapter.initcv(context);
        adapter.notifyItemInserted(0);
    }

}