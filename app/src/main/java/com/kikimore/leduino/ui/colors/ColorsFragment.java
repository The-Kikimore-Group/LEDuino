package com.kikimore.leduino.ui.colors;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kikimore.leduino.Device;
import com.kikimore.leduino.R;
import com.kikimore.leduino.login_activity;

import java.util.ArrayList;

import top.defaults.colorpicker.ColorObserver;
import top.defaults.colorpicker.ColorPickerView;

public class ColorsFragment extends Fragment {

    private CheckBox PresetCB;
    private static ArrayList<String> title = new ArrayList<>(), ida = new ArrayList<>();
    private ColorPickerView colorPickerView;
    public static int pos;
    private Spinner sp_preset, sp_device;
    private static FirebaseAuth mAuth;

    private ColorsViewModel homeViewModel;
    private DatabaseReference mColor;
    private DatabaseReference mPreset;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(ColorsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_colors, container, false);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onChanged(@Nullable String s) {

                mAuth = FirebaseAuth.getInstance();

                colorPickerView = root.findViewById(R.id.colorPicker);
                PresetCB = root.findViewById(R.id.PresetOnCheckbox);
                sp_preset = root.findViewById(R.id.sp_preset);
                sp_device = root.findViewById(R.id.sp_device);

                initcv();

                sp_device.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, title));

                colorPickerView.setInitialColor(Color.WHITE);


               sp_device.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                   @Override
                   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                       pos = position;

                       if(mAuth.getCurrentUser() != null) {
                           if(!PresetCB.isChecked()) {
                               mColor = FirebaseDatabase.getInstance()
                                       .getReference("Devices")
                                       .child(mAuth.getUid())
                                       .child(ida.get(position))
                                       .child("color");
                               colorPickerView.subscribe(new ColorObserver() {
                                   @RequiresApi(api = Build.VERSION_CODES.O)
                                   @Override
                                   public void onColor(int color, boolean fromUser, boolean shouldPropagate) {
                                       //Color clr = Color.valueOf(color);

                                       mColor.setValue(Integer.toHexString(color));
                                   }
                               });
                           }
                       }
                   }
                   @Override
                   public void onNothingSelected(AdapterView<?> parent) {
                   }
               });
                PresetCB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(PresetCB.isChecked()){
                            mPreset = FirebaseDatabase.getInstance()
                                    .getReference("Devices")
                                    .child(mAuth.getUid())
                                    .child(ida.get(pos))
                                    .child("Preset");
                            sp_preset.setVisibility(View.VISIBLE);
                            sp_preset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    switch(position) {
                                        case (0):
                                            mPreset.setValue("1");
                                        case (1):
                                            mPreset.setValue("2");
                                        case (2):
                                            mPreset.setValue("3");
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    mPreset.setValue("0");
                                }
                            });
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
        if(mAuth.getCurrentUser() != null) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Devices").child(mAuth.getUid());

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    title.clear();
                    ida.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Device device = ds.getValue(Device.class);
                        assert device != null;
                        title.add(device.name);
                        ida.add(device.id);
                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(getContext(), login_activity.class);
            startActivity(intent);
        }
    }
}
