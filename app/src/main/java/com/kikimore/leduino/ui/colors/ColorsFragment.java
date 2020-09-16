package com.kikimore.leduino.ui.colors;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.kikimore.leduino.openDBHelper;

import java.util.ArrayList;

import top.defaults.colorpicker.ColorObserver;
import top.defaults.colorpicker.ColorPickerPopup;
import top.defaults.colorpicker.ColorPickerView;

public class ColorsFragment extends Fragment {

    private CheckBox PresetCB;
    private static openDBHelper dBhelper;
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

                dBhelper = new openDBHelper(getContext());

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
        Cursor cursor = dBhelper.cursor();

        title.clear();

        while (cursor.moveToNext()) {
            title.add(cursor.getString(1));
        }
    }
}
