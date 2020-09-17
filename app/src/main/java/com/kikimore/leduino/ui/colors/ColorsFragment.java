package com.kikimore.leduino.ui.colors;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kikimore.leduino.Device;
import com.kikimore.leduino.R;

import java.util.ArrayList;

import top.defaults.colorpicker.ColorObserver;
import top.defaults.colorpicker.ColorPickerView;

public class ColorsFragment extends Fragment {

    private CheckBox PresetCB;
    private static ArrayList<String> title = new ArrayList<>();
    private ColorPickerView colorPickerView;

    private Spinner sp_preset, sp_device;

    private ColorsViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(ColorsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_colors, container, false);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onChanged(@Nullable String s) {

                colorPickerView = root.findViewById(R.id.colorPicker);
                PresetCB = root.findViewById(R.id.PresetOnCheckbox);
                sp_preset = root.findViewById(R.id.sp_preset);
                sp_device = root.findViewById(R.id.sp_device);

                initcv();

                sp_device.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, title));

                colorPickerView.setInitialColor(Color.WHITE);
                colorPickerView.subscribe(new ColorObserver() {
                    @Override
                    public void onColor(int color, boolean fromUser, boolean shouldPropagate) {
                    }
                });

                PresetCB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(PresetCB.isChecked()){
                            sp_preset.setVisibility(View.VISIBLE);
                        }else{
                            sp_preset.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });
        return root;
    }
    private static void initcv() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Devices");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                title.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Device device = ds.getValue(Device.class);
                    assert  device != null;
                    title.add(device.name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
