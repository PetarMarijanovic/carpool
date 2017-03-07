package com.carpool.carpool.map;

import android.content.Context;
import android.content.Intent;

import com.carpool.carpool.GPS_Service;
import com.carpool.carpool.model.Location;
import com.carpool.carpool.repository.LocationRepository;

import rx.Observable;

/** Created by petar on 07/03/2017. */
public class MapModel {

  private final Context context;
  private final LocationRepository locationRepository;

  public MapModel(Context context, LocationRepository locationRepository) {
    this.context = context;
    this.locationRepository = locationRepository;
  }

  public Observable<Location> location() {
    return locationRepository.location();
  }

  public void startService() {
    context.startService(new Intent(context, GPS_Service.class));
  }

  public void stopService() {
    context.stopService(new Intent(context, GPS_Service.class));
  }
}
