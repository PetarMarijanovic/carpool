package com.carpool.carpool;

import android.app.Application;

import com.carpool.carpool.dagger.ApplicationComponent;
import com.carpool.carpool.dagger.ApplicationModule;
import com.carpool.carpool.dagger.DaggerApplicationComponent;

import timber.log.Timber;
import timber.log.Timber.DebugTree;

/** Created by petar on 12/01/2017. */
public class CarPoolApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    applicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();

    Timber.plant(new DebugTree());
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }
}
