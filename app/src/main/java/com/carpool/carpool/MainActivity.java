package com.carpool.carpool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.carpool.carpool.dagger.ActivityModule;
import com.carpool.carpool.login.DaggerLogInDagger_Component;
import com.carpool.carpool.login.LogInDagger.Component;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Component component =
        DaggerLogInDagger_Component.builder().activityModule(new ActivityModule(this)).build();

    setContentView(component.mvp().view());
  }
}
