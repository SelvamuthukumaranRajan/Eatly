package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliveryapp.commonutils.SharedPrefsUtils;
import com.example.deliveryapp.databinding.ActivityAddressBookBinding;
import com.example.deliveryapp.location.MapActivity;

import static com.example.deliveryapp.commonutils.Utils.Navigation;

public class AddressBook extends AppCompatActivity {

    ActivityAddressBookBinding activityAddressBookBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddressBookBinding = DataBindingUtil.setContentView(this,R.layout.activity_address_book);

        TextView title = activityAddressBookBinding.layoutActionBar.findViewById(R.id.tvActivity);
        title.setText("Address Book");

        ImageView back = activityAddressBookBinding.layoutActionBar.findViewById(R.id.ivBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Navigation(AddressBook.this, HomeNavigation.class);
            }
        });

        //Home
        String home = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.homeAddress);
        if (home == null || home.isEmpty()) {
            activityAddressBookBinding.tvHomeAddress.setVisibility(View.GONE);
            activityAddressBookBinding.btEditHome.setImageResource(R.drawable.ic_add);
        }
        else{
            activityAddressBookBinding.tvHomeAddress.setVisibility(View.VISIBLE);
            activityAddressBookBinding.tvHomeAddress.setText(home);
            activityAddressBookBinding.btEditHome.setImageResource(R.drawable.ic_more);
        }
        activityAddressBookBinding.btEditHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.navigateActivity,"AB");
                SharedPrefsUtils.putBoolean(SharedPrefsUtils.PREF_KEY.forHome,true);
                Navigation(AddressBook.this, MapActivity.class);
            }
        });

        //work
        String work = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.workAddress);
        if ( work == null || work.isEmpty() ) {
            activityAddressBookBinding.tvWorkAddress.setVisibility(View.GONE);
            activityAddressBookBinding.btWorkEdit.setImageResource(R.drawable.ic_add);
        }
        else{
            activityAddressBookBinding.tvWorkAddress.setVisibility(View.VISIBLE);
            activityAddressBookBinding.tvWorkAddress.setText(work);
            activityAddressBookBinding.btWorkEdit.setImageResource(R.drawable.ic_more);
        }
        activityAddressBookBinding.btWorkEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.navigateActivity,"AB");
                SharedPrefsUtils.putBoolean(SharedPrefsUtils.PREF_KEY.forWork,true);
                Navigation(AddressBook.this, MapActivity.class);
            }
        });

        //other
        String other = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.otherAddress);
        if ( other == null || other.isEmpty()) {
            activityAddressBookBinding.tvOtherLocation.setVisibility(View.GONE);
            activityAddressBookBinding.btOtherEdit.setImageResource(R.drawable.ic_add);
        }
        else{
            activityAddressBookBinding.tvOtherLocation.setVisibility(View.VISIBLE);
            activityAddressBookBinding.tvOtherLocation.setText(other);
            activityAddressBookBinding.btOtherEdit.setImageResource(R.drawable.ic_more);
        }
        activityAddressBookBinding.btOtherEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.navigateActivity,"AB");
                SharedPrefsUtils.putBoolean(SharedPrefsUtils.PREF_KEY.forOther,true);
                Navigation(AddressBook.this, MapActivity.class);
            }
        });

        /*SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.navigateActivity,"AB");

        String newLocation = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.shareNewLocation);
        if (!newLocation.isEmpty())
        {
            activityAddressBookBinding.tvHomeAddress.setText(newLocation);
        }*/
    }
}