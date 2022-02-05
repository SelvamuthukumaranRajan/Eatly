package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.deliveryapp.databinding.ActivitySignUpBinding;

import static com.example.deliveryapp.commonutils.Utils.Navigation;

public class SignUp extends AppCompatActivity {

    ActivitySignUpBinding activitySignUpBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }

        activitySignUpBinding.btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(SignUp.this,HomeNavigation.class);
            }
        });

    }
}