package com.example.womeniya;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import static android.content.Context.LOCATION_SERVICE;

public class MyLocation implements LocationListener {
    public static double latitude;
    Context context;
    Location loc;
    LocationManager locationManager;
    boolean isGPSon = false;
    boolean isNETon = false;
    public static double longitude;

    MyLocation(Context context) {
        this.context = context;
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGPSon = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Toast.makeText(context, "GPS Enable" + isGPSon, Toast.LENGTH_LONG).show();
            isNETon = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Toast.makeText(context, "Network enable" + isNETon, Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            if (isGPSon) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if (isNETon) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            latitude = loc.getLatitude();
            longitude = loc.getLongitude();
        } catch (Exception exception) {
            Toast.makeText(context, "exception", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location l) {
        l.getLatitude();
        l.getLongitude();
        latitude = l.getLatitude();
        longitude = l.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(context, "GPS disabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(context, "GPS disabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

}
