package com.carpool.carpool.map;

import android.content.Context;
import android.view.View;

import com.carpool.carpool.R;
import com.carpool.carpool.dagger.ActivityContext;
import com.carpool.carpool.dagger.ActivityModule;
import com.carpool.carpool.mvp.Mvp;
import com.carpool.carpool.repository.LocationRepository;
import com.carpool.carpool.repository.RepositoryDagger;

import javax.inject.Singleton;

import dagger.Provides;

/** Created by petar on 21/02/2017. */
public class MapDagger {

  @Singleton
  @dagger.Component(modules = {Module.class, ActivityModule.class, RepositoryDagger.Module.class})
  public interface Component {
    Mvp<MapPresenter, MapView> mvp();

    void inject(MapView view);
  }

  @dagger.Module
  public static class Module {

    @Provides
    @Singleton
    public MapModel mapModel(@ActivityContext Context context, LocationRepository repository) {
      return new MapModel(context, repository);
    }

    @Provides
    @Singleton
    public MapView view(@ActivityContext Context context) {
      return (MapView) View.inflate(context, R.layout.view_map, null);
    }

    @Provides
    @Singleton
    Mvp<MapPresenter, MapView> mvp(MapPresenter presenter, MapView view) {
      return Mvp.create(presenter, view);
    }
  }
}
