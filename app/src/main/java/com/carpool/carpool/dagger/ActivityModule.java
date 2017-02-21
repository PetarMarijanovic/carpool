package com.carpool.carpool.dagger;

import android.app.Activity;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/** Created by petar on 21/02/2017. */
@Module
public class ActivityModule {

  private Activity activity;

  public ActivityModule(Activity activity) {
    this.activity = activity;
  }

  @Provides
  @Singleton
  @ActivityContext
  public Context context() {
    return activity;
  }
}
