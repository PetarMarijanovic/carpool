package com.carpool.carpool.login;

import android.content.Context;
import android.view.View;

import com.carpool.carpool.MainActivity;
import com.carpool.carpool.R;
import com.carpool.carpool.dagger.ActivityContext;
import com.carpool.carpool.dagger.ActivityModule;
import com.carpool.carpool.mvp.Mvp;

import javax.inject.Singleton;

import dagger.Provides;

/** Created by petar on 21/02/2017. */
public class LogInDagger {

  @Singleton
  @dagger.Component(modules = {Module.class, ActivityModule.class})
  public interface Component {

    void inject(MainActivity target);
  }

  @dagger.Module
  public static class Module {

    @Provides
    // TODO: petar 21/02/2017 Singleton?
    public LogInView view(@ActivityContext Context context) {
      return (LogInView) View.inflate(context, R.layout.view_log_in, null);
    }

    @Provides
    // TODO: petar 21/02/2017 Singleton?
    Mvp<LogInPresenter, LogInView> mvp(LogInPresenter presenter, LogInView view) {
      // TODO: petar 21/02/2017 AutoFactory
      return Mvp.create(presenter, view);
    }
  }
}
