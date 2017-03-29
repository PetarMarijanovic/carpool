package com.carpool.carpool;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import android.Manifest.permission;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.carpool.carpool.model.Location;
import com.carpool.carpool.repository.DaggerRepositoryDagger_Component;
import com.carpool.carpool.repository.LocationRepository;

import org.joda.time.DateTime;

import timber.log.Timber;

/** Created by petar on 24/02/2017. */
public class GPS_Service extends Service
    implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

  private static final int NOTIFICATION_ID = 0;

  // TODO: petar 07/03/2017 Dagger
  private GoogleApiClient googleApiClient;
  private LocationRequest locationRequest;
  private LocationRepository locationRepository;
  private NotificationManager notificationManager;
  private FusedLocationProviderApi fusedLocationApi;

  @Override
  public void onCreate() {
    locationRepository = DaggerRepositoryDagger_Component.create().locationRepository();

    googleApiClient =
        new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();

    locationRequest = new LocationRequest();
    locationRequest.setInterval(10000);
    locationRequest.setFastestInterval(5000);
    locationRequest.setSmallestDisplacement(50); // meters
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    googleApiClient.connect();

    notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    fusedLocationApi = LocationServices.FusedLocationApi;

    showNotification();
  }

  @Override
  public void onDestroy() {
    notificationManager.cancel(NOTIFICATION_ID);
    fusedLocationApi.removeLocationUpdates(googleApiClient, this);
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
      // TODO: petar 24/02/2017 Ask for permission
      Timber.e("Petarr NO PERMISSION GPS");
      return;
    }

    fusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
  }

  @Override
  public void onConnectionSuspended(int i) {}

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

  @Override
  public void onLocationChanged(android.location.Location location) {
    Location build =
        new Location(
            location.getLatitude(),
            location.getLongitude(),
            location.getSpeed(),
            new DateTime().withMillis(location.getTime()),
            location.getAccuracy());

    locationRepository.save(build);
  }

  public void showNotification() {
    // TODO: petar 07/03/2017 Icon
    Notification notification =
        new Notification.Builder(this)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_text))
            .setSmallIcon(R.drawable.common_full_open_on_phone)
            .setOngoing(true) // Cannot be dismissed by user
            .build();

    notificationManager.notify(NOTIFICATION_ID, notification);
  }
}
