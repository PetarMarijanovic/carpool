package com.carpool.carpool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.carpool.carpool.dagger.ActivityModule;
import com.carpool.carpool.login.DaggerLogInDagger_Component;
import com.carpool.carpool.login.LogInDagger.Component;
import com.carpool.carpool.login.LogInPresenter;
import com.carpool.carpool.login.LogInView;
import com.carpool.carpool.mvp.Mvp;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

  @Inject Mvp<LogInPresenter, LogInView> mvp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Component component =
        DaggerLogInDagger_Component.builder().activityModule(new ActivityModule(this)).build();
    component.inject(this);

    setContentView(mvp.view());
  }
}
