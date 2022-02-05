package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.example.deliveryapp.adapters.DishModel;
import com.example.deliveryapp.adapters.ItemCategory;
import com.example.deliveryapp.adapters.ShopDetailsAdapter;
import com.example.deliveryapp.databinding.ActivityShopDetailsListBinding;

public class ShopDetailsList extends AppCompatActivity {

    ActivityShopDetailsListBinding activityShopDetailsListBinding;
    List<DishModel> dishModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityShopDetailsListBinding = DataBindingUtil.setContentView(this,R.layout.activity_shop_details_list);

        activityShopDetailsListBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        List<String> list = new ArrayList<>();
        list.add("South Indians");
        list.add("Starters");
        list.add("Main Course");
        list.add("Meals");
        list.add("Rice");
        list.add("Combos");
        list.add("Extras");
        list.add("Beverages");

        ItemCategory itemCategory = new ItemCategory(ShopDetailsList.this, list);
        activityShopDetailsListBinding.rvCategory.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        activityShopDetailsListBinding.rvCategory.setAdapter(itemCategory);

        dishModelList = new ArrayList<>();

        dishModelList.add(new DishModel("Idlies & Vadas",""));

        dishModelList.add(new DishModel("Idly", "60"));
        dishModelList.add(new DishModel("Mini Idly", "100"));
        dishModelList.add(new DishModel("Vada", "65"));
        dishModelList.add(new DishModel("Sambar Idly", "59"));
        dishModelList.add(new DishModel("Sambar Vada", "59"));

        dishModelList.add(new DishModel("Dosa", ""));

        dishModelList.add(new DishModel("Plain Dosa", "68"));
        dishModelList.add(new DishModel("Onion Dosa", "68"));
        dishModelList.add(new DishModel("Ghee Dosa", "98"));
        dishModelList.add(new DishModel("Rava Dosa", "199"));
        dishModelList.add(new DishModel("Podi Dosa", "88"));

        dishModelList.add(new DishModel("Diary", ""));

        dishModelList.add(new DishModel("Paneer", "199"));
        dishModelList.add(new DishModel("Ghee", "699"));
        dishModelList.add(new DishModel("Milk", "32"));
        dishModelList.add(new DishModel("Curd", "22"));

        List<Integer> countList = new ArrayList<>();

        int i = 0;
        while(i<dishModelList.size())
        {
            countList.add(0);
            i++;
        }

        ShopDetailsAdapter shopDetailsAdapter = new ShopDetailsAdapter(ShopDetailsList.this, dishModelList, countList, activityShopDetailsListBinding);
        activityShopDetailsListBinding.rvCategoryResult.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        activityShopDetailsListBinding.rvCategoryResult.setAdapter(shopDetailsAdapter);


    }
}