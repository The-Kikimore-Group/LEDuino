package com.kikimore.leduino.ui.colors;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

import com.kikimore.leduino.R;
import com.kikimore.leduino.RVdeviceListAdapter;

public class ColorsFragment extends Fragment {
    private ImageView hueWheel;
    private View mColor;
    private Bitmap bitmap;
    private CheckBox PresetCB;
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

                hueWheel = root.findViewById(R.id.hueWheel);
                mColor = root.findViewById(R.id.view);
                PresetCB = root.findViewById(R.id.PresetOnCheckbox);
                sp_preset = root.findViewById(R.id.sp_preset);

                hueWheel.setDrawingCacheEnabled(true);
                hueWheel.buildDrawingCache(false);

                hueWheel.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
                            try {

                                bitmap = hueWheel.getDrawingCache();

                                int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());

                                int r = Color.red(pixel);
                                int g = Color.green(pixel);
                                int b = Color.blue(pixel);

                                mColor.setBackgroundColor(Color.rgb(r, g, b));
                            }catch(Exception e){
                                //Toast.makeText(getContext(), "Вы вышли за пределы колеса", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
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
}