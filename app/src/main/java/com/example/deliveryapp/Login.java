package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.deliveryapp.commonutils.SharedPrefsUtils;
import com.example.deliveryapp.databinding.ActivityLoginBinding;
import com.example.deliveryapp.location.MapActivity;

import static com.example.deliveryapp.commonutils.Utils.Navigation;

public class Login extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }

        activityLoginBinding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPrefsUtils.getBoolean(SharedPrefsUtils.PREF_KEY.shareLoginStatus))
                {
                    Navigation(Login.this, HomeNavigation.class);
                }
                else{
                    SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.navigateActivity,"L");
                    Navigation(Login.this, MapActivity.class);
                }
            }
        });
        activityLoginBinding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(Login.this,SignUp.class);
            }
        });
    }
}