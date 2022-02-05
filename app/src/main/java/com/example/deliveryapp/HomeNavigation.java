package com.example.deliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

import com.example.deliveryapp.adapters.CustomPagerAdapter;
import com.example.deliveryapp.commonutils.SharedPrefsUtils;
import com.example.deliveryapp.databinding.ActivityHomeNavigationBinding;

import static com.example.deliveryapp.commonutils.Utils.DisplayCustomBottomDialog;
import static com.example.deliveryapp.commonutils.Utils.Navigation;

public class HomeNavigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityHomeNavigationBinding activityHomeNavigationBinding;
    public DrawerLayout navDrawer;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    //Banner
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeNavigationBinding = DataBindingUtil.setContentView(this,R.layout.activity_home_navigation);

        ImageView menu = activityHomeNavigationBinding.homeLayout.ivMenu;
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navDrawer.openDrawer(Gravity.LEFT);
            }
        });

        ImageView search = activityHomeNavigationBinding.homeLayout.ivSearch;
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(HomeNavigation.this,Search.class);
            }
        });

        ImageView addressBook = activityHomeNavigationBinding.homeLayout.ivAddressBook;
        addressBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(HomeNavigation.this,AddressBook.class);
            }
        });

        TextView location = activityHomeNavigationBinding.homeLayout.tvLocation;
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog selectLocation = DisplayCustomBottomDialog(HomeNavigation.this,R.layout.dialog_select_address);
                Button cancel = selectLocation.findViewById(R.id.btCancel);
                ImageView addressBook = selectLocation.findViewById(R.id.ivAddressBook);
                TextView tvHome = selectLocation.findViewById(R.id.tvHomeAddress);
                TextView tvWork = selectLocation.findViewById(R.id.tvWorkAddress);
                TextView tvOther = selectLocation.findViewById(R.id.tvOtherLocation);

                RadioGroup mainCategory = selectLocation.findViewById(R.id.radiogroup_mainCategory);
                RadioButton r1 = selectLocation.findViewById(R.id.radiobutton_1);
                RadioButton r2 = selectLocation.findViewById(R.id.radiobutton_2);
                RadioButton r3 = selectLocation.findViewById(R.id.radiobutton_3);

                String home = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.homeAddress);
                String work = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.workAddress);
                String other = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.otherAddress);

                if (home == null || home.isEmpty()) {
                    r1.setVisibility(View.GONE);
                    tvHome.setVisibility(View.GONE);
                }
                else{
                    r1.setVisibility(View.VISIBLE);
                    tvHome.setVisibility(View.VISIBLE);
                    tvHome.setText(home);
                }

                if (work == null || work.isEmpty()) {
                    r2.setVisibility(View.GONE);
                    tvWork.setVisibility(View.GONE);
                }
                else{
                    r2.setVisibility(View.VISIBLE);
                    tvWork.setVisibility(View.VISIBLE);
                    tvWork.setText(work);
                }

                if (other == null || other.isEmpty()) {
                    r3.setVisibility(View.GONE);
                    tvOther.setVisibility(View.GONE);
                }
                else{
                    r3.setVisibility(View.VISIBLE);
                    tvOther.setVisibility(View.VISIBLE);
                    tvOther.setText(other);
                }
                selectLocation.show();

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectLocation.dismiss();
                    }
                });

                addressBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Navigation(HomeNavigation.this,AddressBook.class);
                    }
                });

                mainCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // checkedId is the RadioButton selected
                        if (mainCategory.getCheckedRadioButtonId() != -1) {
                            // one of the radio buttons is checked
                            cancel.setText("Continue");

                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    switch (mainCategory.getCheckedRadioButtonId()) {
                                        case R.id.radiobutton_1:
                                            location.setText(r1.getText().toString());
                                            SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareSelectedLocation,r1.getText().toString());
                                            break;

                                        case R.id.radiobutton_2:
                                            location.setText(r2.getText().toString());
                                            SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareSelectedLocation,r2.getText().toString());
                                            break;

                                        case R.id.radiobutton_3:
                                            location.setText(r3.getText().toString());
                                            SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareSelectedLocation,r3.getText().toString());
                                            break;
                                    }
                                    selectLocation.dismiss();
                                }
                            });
                        }
                    }
                });
            }
        });
        navDrawer = findViewById(R.id.my_drawer_layout);
        navDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, navDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        navDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        View header = activityHomeNavigationBinding.navView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.tvName);
        name.setText("User Name");
        LinearLayout headerLayout = (LinearLayout) header.findViewById(R.id.navigationHeader);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(HomeNavigation.this, Profile.class);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Banner
        int[] images = {R.drawable.combooffer, R.drawable.riskfree, R.drawable.combooffer, R.drawable.riskfree};
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new CustomPagerAdapter(HomeNavigation.this,images));

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);


        activityHomeNavigationBinding.homeLayout.grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(HomeNavigation.this,ShopList.class);
            }
        });

        activityHomeNavigationBinding.homeLayout.fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(HomeNavigation.this,ShopList.class);
            }
        });

        activityHomeNavigationBinding.homeLayout.meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(HomeNavigation.this,ShopList.class);
            }
        });

        activityHomeNavigationBinding.homeLayout.medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(HomeNavigation.this,ShopList.class);
            }
        });

        activityHomeNavigationBinding.homeLayout.food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(HomeNavigation.this,ShopList.class);
            }
        });

        activityHomeNavigationBinding.homeLayout.bakery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation(HomeNavigation.this,ShopList.class);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_cart) {
            Navigation(HomeNavigation.this,Cart.class);
        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_addressBook) {
            finish();
            Navigation(HomeNavigation.this,AddressBook.class);
        } else if (id == R.id.nav_chat) {

        }
        /*else if (id == R.id.nav_announcement) {

        }*/
        else if (id == R.id.nav_scan) {

        }
        DrawerLayout drawer = findViewById(R.id.my_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}