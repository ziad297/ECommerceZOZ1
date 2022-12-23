package com.example.ecommerce.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ecommerce.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    private LatLng location;
    private String locationTitle;
    private Marker marker;
    Intent intent;
   Button button;
    String city;
    String state;
    private static final String TAG = "MapsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapsFragment);
        mapFragment.getMapAsync(this);
        button = findViewById(R.id.saveLocationButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        getUserCurrentLocation(map);
    }

    private void getUserCurrentLocation(GoogleMap map) {
        FusedLocationProviderClient currentLocation = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);

        currentLocation.getLastLocation()
                .addOnSuccessListener(_currentLocation -> {
                    location = new LatLng(_currentLocation.getLatitude(), _currentLocation.getLongitude());
                    locationTitle = "currentLocation";
                    map.moveCamera(CameraUpdateFactory.newLatLng(location));
                    marker = map.addMarker(
                            new MarkerOptions().position(location)
                                    .title(locationTitle));
                    Geocoder geo = new Geocoder(this);
                    try {
                        Log.d(TAG, "getUserCurrentLocation: " + geo.getFromLocation(_currentLocation.getLatitude(), _currentLocation.getLongitude(), 2).get(0).getAddressLine(0));
                          state= geo.getFromLocation(_currentLocation.getLatitude(), _currentLocation.getLongitude(), 2).get(0).getAddressLine(0);
                          city= geo.getFromLocation(_currentLocation.getLatitude(), _currentLocation.getLongitude(), 2).get(0).getAdminArea();
                        intent = new Intent(MapsActivity.this,UpdateUserInfoActivity.class);
                        intent.putExtra("state",state);
                        intent.putExtra("city",city);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
    }
}