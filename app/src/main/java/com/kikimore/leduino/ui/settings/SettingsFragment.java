package com.kikimore.leduino.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kikimore.leduino.R;
import com.kikimore.leduino.login_activity;

public class SettingsFragment extends Fragment {

    private SettingsViewModel notificationsViewModel;
    private TextView exitAccount;
    private FirebaseAuth mAuth;
    private LinearLayout setting_arduino;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_settings, container, false);

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               setting_arduino = root.findViewById(R.id.setting_arduino);

               mAuth = FirebaseAuth.getInstance();
               exitAccount = root.findViewById(R.id.exitbutton);

               exitAccount.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       mAuth.signOut();
                       FirebaseUser currentUser = mAuth.getCurrentUser();
                       if(currentUser == null){
                           Intent intent = new Intent(getContext(), login_activity.class);
                           startActivity(intent);
                       }
                   }
               });
            }
        });
        return root;
    }
}