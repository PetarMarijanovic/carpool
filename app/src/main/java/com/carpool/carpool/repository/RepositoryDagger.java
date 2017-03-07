package com.carpool.carpool.repository;

import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Provides;

/** Created by petar on 21/02/2017. */
public class RepositoryDagger {

  @Singleton
  @dagger.Component(modules = {Module.class})
  public interface Component {
    LocationRepository locationRepository();
  }

  @dagger.Module
  public static class Module {

    @Provides
    @Singleton
    public FirebaseDatabase firebaseDatabase() {
      return FirebaseDatabase.getInstance();
    }

    @Provides
    @Singleton
    LocationRepository locationRepository(FirebaseDatabase firebaseDatabase) {
      return new LocationRepository(firebaseDatabase);
    }
  }
}
