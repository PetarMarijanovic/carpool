package com.carpool.carpool;

import android.app.Application;

import timber.log.Timber;
import timber.log.Timber.DebugTree;

/** Created by petar on 12/01/2017. */
public class CarPoolApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();

    Timber.plant(new DebugTree());
  }
}
