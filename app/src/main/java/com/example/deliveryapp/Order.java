package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.deliveryapp.adapters.Orders;
import com.example.deliveryapp.databinding.ActivityOrderBinding;

public class Order extends AppCompatActivity {

    ActivityOrderBinding activityOrderBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderBinding = DataBindingUtil.setContentView(this,R.layout.activity_order);

        TextView title = activityOrderBinding.layoutActionBar.findViewById(R.id.tvActivity);
        title.setText("Orders");
        ImageView back = activityOrderBinding.layoutActionBar.findViewById(R.id.ivBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        List<String> list = new ArrayList<>();
        list.add("All");
        list.add("Pick&Drop");
        list.add("Restaurants");
        list.add("HealthCare");
        list.add("Home Made");
        list.add("Grocery");

        Orders orders = new Orders(Order.this, list);
        activityOrderBinding.rvOrder.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        activityOrderBinding.rvOrder.setAdapter(orders);

    }
}