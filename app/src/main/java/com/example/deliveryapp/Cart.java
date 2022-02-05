package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.deliveryapp.SQLiteDatabase.CartListModel;
import com.example.deliveryapp.SQLiteDatabase.DatabaseHandler;
import com.example.deliveryapp.adapters.CartList;
import com.example.deliveryapp.commonutils.SharedPrefsUtils;
import com.example.deliveryapp.databinding.ActivityCartBinding;

import static com.example.deliveryapp.commonutils.Utils.Navigation;

public class Cart extends AppCompatActivity {

    ActivityCartBinding activityCartBinding;
    int price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCartBinding = DataBindingUtil.setContentView(this,R.layout.activity_cart);

        TextView title = activityCartBinding.layoutActionBar.findViewById(R.id.tvActivity);
        title.setText("Cart");
        ImageView back = activityCartBinding.layoutActionBar.findViewById(R.id.ivBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        activityCartBinding.tvAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DatabaseHandler db = new DatabaseHandler(Cart.this);
        List<CartListModel> contacts = db.getAllCartItems();

        for ( CartListModel clm : contacts)
        {
            int quantity = clm.getQuantity();
            while(quantity >= 1)
            {
                price+=clm.getPrice();
                quantity--;
            }
        }

        if (contacts.size() == 0)
        {
            activityCartBinding.emptyCart.setVisibility(View.VISIBLE);
            activityCartBinding.nonEmptyCart.setVisibility(View.GONE);
        }
        else
        {
            activityCartBinding.emptyCart.setVisibility(View.GONE);
            activityCartBinding.nonEmptyCart.setVisibility(View.VISIBLE);
        }

        activityCartBinding.tvItemPrice.setText(String.format("₹ %d", price));
        activityCartBinding.tvPackingPrice.setText("₹ 25");
        activityCartBinding.tvDeliveryPrice.setText("Free");
        activityCartBinding.tvFinalPrice.setText(String.format("₹ %d", price+25));

        CartList cartList = new CartList(Cart.this, contacts, price, price+25, activityCartBinding);
        activityCartBinding.rvCart.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        activityCartBinding.rvCart.setAdapter(cartList);

        String home = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.homeAddress);
        String work = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.workAddress);
        String other = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.otherAddress);
        String currentLocation = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.shareSelectedLocation);

        if (currentLocation == null || currentLocation.isEmpty() || currentLocation.equals("Home")){
            activityCartBinding.tvAddress.setText("Home");
            activityCartBinding.tvAddressDetails.setText(home);
        }
        else {
            if (currentLocation.equals("Work")) {
                activityCartBinding.tvAddress.setText("Work");
                activityCartBinding.tvAddressDetails.setText(work);
            }
            else if (currentLocation.equals("Other")) {
                activityCartBinding.tvAddress.setText("Other");
                activityCartBinding.tvAddressDetails.setText(other);
            }
        }


        activityCartBinding.btPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(Cart.this);
                db.deleteAll();
                finish();
                Navigation(Cart.this,HomeNavigation.class);
            }
        });
    }
}