package com.example.deliveryapp.location;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import com.example.deliveryapp.AddressBook;
import com.example.deliveryapp.HomeNavigation;
import com.example.deliveryapp.R;
import com.example.deliveryapp.commonutils.SharedPrefsUtils;

import static com.example.deliveryapp.commonutils.Utils.Navigation;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    private GoogleMap map;
    public Marker marker;
    public TextView addressTV;
    LinearLayout addressLayout;
    Button done;

    String Location, Latitude, Longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ImageView back = findViewById(R.id.ivBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addressTV = findViewById(R.id.tvAddress);
        addressLayout = findViewById(R.id.addressLayout);

        Location = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.shareLocation);
        Latitude = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.shareLatitude);
        Longitude = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.shareLongitude);

        done = findViewById(R.id.btSelectLocation);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activityName = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.navigateActivity);
                if (activityName.equals("L")) {
                    Navigation(MapActivity.this, HomeNavigation.class);
                }
                if (activityName.equals("AB")) {
                    String currentLocation = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.shareCurrentLocation);

                    if (SharedPrefsUtils.getBoolean(SharedPrefsUtils.PREF_KEY.forHome))
                    {
                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.homeAddress,currentLocation);
                        SharedPrefsUtils.putBoolean(SharedPrefsUtils.PREF_KEY.forHome,false);
                    }
                    if (SharedPrefsUtils.getBoolean(SharedPrefsUtils.PREF_KEY.forWork))
                    {
                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.workAddress,currentLocation);
                        SharedPrefsUtils.putBoolean(SharedPrefsUtils.PREF_KEY.forWork,false);
                    }
                    if(SharedPrefsUtils.getBoolean(SharedPrefsUtils.PREF_KEY.forOther)){
                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.otherAddress,currentLocation);
                        SharedPrefsUtils.putBoolean(SharedPrefsUtils.PREF_KEY.forOther,false);
                    }
                    Navigation(MapActivity.this, AddressBook.class);
                }
                finish();
            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng point = new LatLng(Double.parseDouble(Latitude), Double.parseDouble(Longitude));
        marker = map.addMarker(new MarkerOptions().position(point).title("Marker in " + Location));
//        map.moveCamera(CameraUpdateFactory.newLatLng(point));
        LatLngBounds.Builder b = new LatLngBounds.Builder();
        b.include(point);
        LatLngBounds bounds = b.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 25, 25, 5);
        map.animateCamera(cu);

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng coOrdinates) {
                marker.remove();
                done.setText("Select Location");
                addressTV.setVisibility(View.VISIBLE);
                LatLng point = new LatLng(coOrdinates.latitude, coOrdinates.longitude);
                marker = map.addMarker(new MarkerOptions().position(point));
                map.animateCamera(CameraUpdateFactory.newLatLng(point));

                Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
                List<Address> addresses = null;

                try {
                    addresses = geocoder.getFromLocation(
                            coOrdinates.latitude,
                            coOrdinates.longitude,
                            1);
                } catch (Exception ioException) {
                    Log.e("", "Error in getting address for the location");
                }

                if (addresses == null || addresses.size() == 0) {
                    Toast.makeText(MapActivity.this, "No address found for the location", Toast.LENGTH_LONG).show();
                } else {
                    Address address = addresses.get(0);
                    StringBuffer addressDetails = new StringBuffer();

                    addressDetails.append(address.getFeatureName());
                    addressDetails.append("\n");

                    if (address.getThoroughfare() != null && !address.getThoroughfare().isEmpty()) {
                        addressDetails.append(address.getThoroughfare());
                        addressDetails.append("\n");
                    }

                    addressDetails.append("Locality: ");
                    addressDetails.append(address.getLocality());
                    addressDetails.append("\n");

                    addressDetails.append("District: ");
                    addressDetails.append(address.getSubAdminArea());
                    addressDetails.append("\n");

                    addressDetails.append("State: ");
                    addressDetails.append(address.getAdminArea());
                    addressDetails.append("\n");

                    addressDetails.append("Country: ");
                    addressDetails.append(address.getCountryName());
                    addressDetails.append("\n");

                    addressDetails.append("Postal Code: ");
                    addressDetails.append(address.getPostalCode());
                    addressDetails.append("\n");

                    addressTV.setText(addressDetails.toString());
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareNewLocation,addressDetails.toString());
                            String activityName = SharedPrefsUtils.getString(SharedPrefsUtils.PREF_KEY.navigateActivity);
                            if (activityName.equals("HN") || activityName.equals("L")) {
                                Navigation(MapActivity.this, HomeNavigation.class);
                            }
                            if (activityName.equals("AB")) {
                                String currentLocation = addressDetails.toString();

                                if (SharedPrefsUtils.getBoolean(SharedPrefsUtils.PREF_KEY.forHome))
                                {
                                    SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.homeAddress,currentLocation);
                                    SharedPrefsUtils.putBoolean(SharedPrefsUtils.PREF_KEY.forHome,false);
                                }
                                if (SharedPrefsUtils.getBoolean(SharedPrefsUtils.PREF_KEY.forWork))
                                {
                                    SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.workAddress,currentLocation);
                                    SharedPrefsUtils.putBoolean(SharedPrefsUtils.PREF_KEY.forWork,false);
                                }
                                if(SharedPrefsUtils.getBoolean(SharedPrefsUtils.PREF_KEY.forOther)){
                                    SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.otherAddress,currentLocation);
                                    SharedPrefsUtils.putBoolean(SharedPrefsUtils.PREF_KEY.forOther,false);
                                }
                                Navigation(MapActivity.this, AddressBook.class);
                            }
                            finish();
                        }
                    });
//                    Toast.makeText(MapActivity.this,addressDetails.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        mapFragment.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapFragment.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFragment.onLowMemory();
    }
}