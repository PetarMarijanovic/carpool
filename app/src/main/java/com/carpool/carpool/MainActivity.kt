package com.carpool.carpool

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.carpool.carpool.dagger.ActivityModule
import com.carpool.carpool.map.DaggerMapDagger_Component

class MainActivity : AppCompatActivity() {
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    val component = DaggerMapDagger_Component.builder().activityModule(ActivityModule(this)).build()
    
    val view = component.mvp().view
    // TODO: petar 07/03/2017 This is because of shit
    component.inject(view)
    setContentView(view)
  }
}
