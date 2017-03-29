package com.carpool.carpool.map

import android.app.FragmentManager
import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.carpool.carpool.R
import com.carpool.carpool.model.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.view_map.view.*
import javax.inject.Inject

/** Created by petar on 29/03/2017. */
class MapView(context: Context, attrs: AttributeSet) : LinearLayout(context,
                                                                    attrs), OnMapReadyCallback {
  fun start(): Button = start
  fun stop(): Button = stop
  fun accuracy(): TextView = accuracy
  fun speed(): TextView = speed
  fun time(): TextView = time
  
  // TODO: petar 01/03/2017 This is shit
  @Inject lateinit var fragmentManager: FragmentManager
  
  private lateinit var googleMap: GoogleMap
  private lateinit var driverIcon: BitmapDescriptor
  
  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    // TODO: petar 01/03/2017 This is shit
    val mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment
    mapFragment.getMapAsync(this)
  }
  
  override fun onMapReady(googleMap: GoogleMap) {
    this.googleMap = googleMap
    driverIcon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)
  }
  
  fun setLocation(location: Location) {
    updateMap(LatLng(location.latitude, location.longitude))
    updateTexts(location)
  }
  
  private fun updateTexts(location: Location) {
    accuracy.text = "Accuracy: " + String.format("%.2f", location.accuracy)
    speed.text = String.format("%.2f", location.speed) + "m/s"
    time.text = location.time.toString("hh:mm:ss dd.MM.yy")
  }
  
  private fun updateMap(latLng: LatLng) {
    googleMap.clear()
    
    googleMap.addMarker(toMarker(latLng))
    
    val cameraPosition = CameraPosition.Builder().target(latLng).zoom(15f).build()
    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
  }
  
  private fun toMarker(latLng: LatLng): MarkerOptions {
    return MarkerOptions().position(latLng).title("Current Position").icon(driverIcon)
  }
}