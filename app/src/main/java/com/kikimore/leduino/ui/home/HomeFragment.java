package com.kikimore.leduino.ui.home;

import android.database.Cursor;
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

import com.kikimore.leduino.MainActivity;
import com.kikimore.leduino.R;
import com.kikimore.leduino.RVdeviceListAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel dashboardViewModel;
    openDBhelper dBhelper;
    private RecyclerView rv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                rv = root.findViewById(R.id.deviceInfo);

                RVdeviceListAdapter adapter = new RVdeviceListAdapter();

                dBhelper = new openDBhelper();

                //Cursor cursor = dBhelper.cursor();

               // while (cursor.moveToNext()) {
               //     id.add(cursor.getString(0));
               //     title.add(cursor.getString(1));
               // }

                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                rv.setAdapter(adapter);
            }
        });
        return root;
    }

    private class openDBhelper {
    }
}