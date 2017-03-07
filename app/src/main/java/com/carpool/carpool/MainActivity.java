package com.carpool.carpool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.carpool.carpool.dagger.ActivityModule;
import com.carpool.carpool.map.DaggerMapDagger_Component;
import com.carpool.carpool.map.MapDagger.Component;
import com.carpool.carpool.map.MapView;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Component component =
        DaggerMapDagger_Component.builder().activityModule(new ActivityModule(this)).build();

    MapView view = component.mvp().view();
    // TODO: petar 07/03/2017 This is because of shit
    component.inject(view);
    setContentView(view);
  }
}
