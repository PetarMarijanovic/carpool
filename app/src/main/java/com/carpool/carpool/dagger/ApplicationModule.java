package com.carpool.carpool.dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/** Created by petar on 18/01/2017. */
@Module
public class ApplicationModule {

  private Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @Provides
  @Singleton
  @ApplicationContext
  public Context context() {
    return application;
  }

  @Provides
  @Singleton
  public SharedPreferences sharedPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }
}
