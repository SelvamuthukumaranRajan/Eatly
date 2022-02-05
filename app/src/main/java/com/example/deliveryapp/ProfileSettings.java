package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliveryapp.databinding.ActivityProfileSettingsBinding;

public class ProfileSettings extends AppCompatActivity {

    ActivityProfileSettingsBinding activityMyProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_profile_settings);

        TextView title = activityMyProfileBinding.layoutActionBar.findViewById(R.id.tvActivity);
        title.setText("My Profile");
        TextView button = activityMyProfileBinding.layoutActionBar.findViewById(R.id.tvButton);
        button.setText("Logout");
        ImageView back = activityMyProfileBinding.layoutActionBar.findViewById(R.id.ivBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}