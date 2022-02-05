package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.example.deliveryapp.adapters.StoreResult;
import com.example.deliveryapp.databinding.ActivityShopListBinding;

public class ShopList extends AppCompatActivity {

    ActivityShopListBinding activityShopListBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityShopListBinding = DataBindingUtil.setContentView(this,R.layout.activity_shop_list);

        activityShopListBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        List<String> list = new ArrayList<>();
        StoreResult storeResult = new StoreResult(ShopList.this, list);
        activityShopListBinding.rvShopList.setLayoutManager(new LinearLayoutManager(ShopList.this, RecyclerView.VERTICAL, false));
        activityShopListBinding.rvShopList.setAdapter(storeResult);
    }
}