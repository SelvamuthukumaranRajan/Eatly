package com.example.deliveryapp.commonutils;

import android.app.Application;

public class DeliveryApp extends Application
{


    private static DeliveryApp _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

    public static synchronized DeliveryApp getInstance()
    {
        return _instance;
    }

}
