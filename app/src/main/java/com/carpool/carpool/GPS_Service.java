package com.carpool.carpool;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.Manifest.permission;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import timber.log.Timber;

/** Created by petar on 24/02/2017. */
public class GPS_Service extends Service
    implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

  private GoogleApiClient googleApiClient;
  private LocationRequest locationRequest;
  private DatabaseReference testRef;

  @Override
  public void onCreate() {
    testRef = FirebaseDatabase.getInstance().getReference("test");

    if (googleApiClient == null) {
      googleApiClient =
          new GoogleApiClient.Builder(this)
              .addConnectionCallbacks(this)
              .addOnConnectionFailedListener(this)
              .addApi(LocationServices.API)
              .build();
    }

    locationRequest = new LocationRequest();
    locationRequest.setInterval(10000);
    locationRequest.setFastestInterval(5000);
    locationRequest.setSmallestDisplacement(0); // in meters // TODO: petar 24/02/2017 Set to 50?
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    googleApiClient.connect();
  }

  @Override
  public void onDestroy() {
    LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    googleApiClient.disconnect();
    super.onDestroy();
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      // TODO: petar 24/02/2017 What now?
      Timber.d("Petarr NO PERMISSION GPS");
      return;
    }
    LocationServices.FusedLocationApi.requestLocationUpdates(
        googleApiClient, locationRequest, this);
  }

  @Override
  public void onConnectionSuspended(int i) {}

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

  @Override
  public void onLocationChanged(Location location) {
    testRef.setValue(location.toString());
  }
}
