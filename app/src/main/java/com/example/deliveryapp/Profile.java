package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.deliveryapp.databinding.ActivityProfileBinding;

import static com.example.deliveryapp.commonutils.Utils.Navigation;

public class Profile extends AppCompatActivity {

    ActivityProfileBinding activityProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.blue, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.blue));
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = mdformat.format(calendar.getTime());


        int time = Integer.parseInt(strDate.substring(0, 2));
        if (time > 6 && time < 12) {
            activityProfileBinding.tvGreetings.setText("Good Morning");
        } else if (time > 12 && time < 15) {
            activityProfileBinding.tvGreetings.setText("Good Afternoon");
        } else if (time > 15 && time < 19) {
            activityProfileBinding.tvGreetings.setText("Good Evening");
        } else {
            activityProfileBinding.tvGreetings.setText("Good Night");
        }


        activityProfileBinding.tvGreetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Profile.this,strDate,Toast.LENGTH_LONG).show();
            }
        });
        activityProfileBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        activityProfileBinding.tvOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(Profile.this, Order.class);
            }
        });

        activityProfileBinding.tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(Profile.this, ProfileSettings.class);
            }
        });
    }
}