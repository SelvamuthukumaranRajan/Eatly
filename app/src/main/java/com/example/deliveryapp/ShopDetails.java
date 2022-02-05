package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.deliveryapp.adapters.ItemCategory;
import com.example.deliveryapp.adapters.SubCategoryExpandableRecyclerAdapter;
import com.example.deliveryapp.adapters.SubcategoryChildListItem;
import com.example.deliveryapp.adapters.SubcategoryParentListItem;
import com.example.deliveryapp.databinding.ActivityShopDetailsBinding;

import static com.example.deliveryapp.commonutils.Utils.Navigation;

public class ShopDetails extends AppCompatActivity {

    ActivityShopDetailsBinding activityShopDetailsBinding;
    Map<String, Map<String, String>> listData;
    List<Integer> showCountLayout;
//    Map<String, List<Integer>> pointer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityShopDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_shop_details);

        List<String> list = new ArrayList<>();
        list.add("South Indians");
        list.add("Starters");
        list.add("Main Course");
        list.add("Meals");
        list.add("Rice");
        list.add("Combos");
        list.add("Extras");
        list.add("Beverages");

        ItemCategory itemCategory = new ItemCategory(ShopDetails.this, list);
        activityShopDetailsBinding.rvCategory.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        activityShopDetailsBinding.rvCategory.setAdapter(itemCategory);

        listData = new LinkedHashMap<>();
//        pointer = new LinkedHashMap<>();
        Map<String, String> children = new LinkedHashMap<>();

        children.put("Idly", "68");
        children.put("Mini Idly", "105");
        children.put("Vada", "62");
        children.put("Sambar Idly", "59");
        children.put("Sambar Vada", "59");


        listData.put("Idlies & Vadas", children);
//        pointer.put("Idlies & Vadas", children.size());

        children = new LinkedHashMap<>();

        children.put("Plain Dosa", "68");
        children.put("Onion Dosa", "68");
        children.put("Ghee Dosa", "98");
        children.put("Rava Dosa", "199");
        children.put("Podi Dosa", "88");

        listData.put("Dosa", children);

        children = new LinkedHashMap<>();

        children.put("Paneer", "199");
        children.put("Ghee", "699");
        children.put("Milk", "32");
        children.put("Curd", "22");

        listData.put("Diary", children);
        RecyclerView expandable = findViewById(R.id.rv_categoryResult);
        List<SubcategoryParentListItem> subcategoryParentListItems = new ArrayList<>();
        for (String title : listData.keySet()) {
            SubcategoryParentListItem eachParentItem = new SubcategoryParentListItem(title);
            subcategoryParentListItems.add(eachParentItem);
        }

        List<ParentListItem> parentListItems = new ArrayList<>();
        for (SubcategoryParentListItem subcategoryParentListItem : subcategoryParentListItems) {
            List<SubcategoryChildListItem> childItemList = new ArrayList<>();
            for (Map.Entry<String, String> child : listData.get(subcategoryParentListItem.mTitle).entrySet()) {
                childItemList.add(new SubcategoryChildListItem(child.getKey(), child.getValue()));
            }
            subcategoryParentListItem.setChildItemList(childItemList);
            parentListItems.add(subcategoryParentListItem);
        }

        int i = 0;
        int size = listData.size();
        for(Map.Entry<String, Map<String, String>> e : listData.entrySet())
            size += e.getValue().size();

        showCountLayout = new ArrayList<>();
        while(i<size)
        {
            showCountLayout.add(0);
            i++;
        }

        expandable.setLayoutManager(new LinearLayoutManager(ShopDetails.this, LinearLayoutManager.VERTICAL, false));
        expandable.setAdapter(new SubCategoryExpandableRecyclerAdapter(ShopDetails.this, parentListItems, showCountLayout, activityShopDetailsBinding));

        activityShopDetailsBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(ShopDetails.this,HomeNavigation.class);
            }
        });
    }
}