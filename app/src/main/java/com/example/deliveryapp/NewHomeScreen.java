package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.deliveryapp.databinding.ActivityNewHomeScreenBinding;

public class NewHomeScreen extends AppCompatActivity {

    ActivityNewHomeScreenBinding activityNewHomeScreenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNewHomeScreenBinding = DataBindingUtil.setContentView(this,R.layout.activity_new_home_screen);
    }
}