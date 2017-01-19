package com.carpool.carpool.dagger;

import com.carpool.carpool.CarPoolApplication;
import com.carpool.carpool.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/** Created by petar on 18/01/2017. */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

  void inject(CarPoolApplication target);

  void inject(MainActivity target);
}
