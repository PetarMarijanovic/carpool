package com.carpool.carpool.map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.FragmentManager;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carpool.carpool.R;
import com.carpool.carpool.model.Location;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/** Created by petar on 20/02/2017. */
// TODO: petar 28/02/2017 Coordinator layout or something new
public class MapView extends LinearLayout implements OnMapReadyCallback {

  @BindView(R.id.start)
  Button start;

  @BindView(R.id.stop)
  Button stop;

  @BindView(R.id.speed)
  TextView speed;

  @BindView(R.id.accuracy)
  TextView accuracy;

  @BindView(R.id.time)
  TextView time;

  // TODO: petar 01/03/2017 This is shit
  @Inject FragmentManager fragmentManager;

  private GoogleMap googleMap;
  private BitmapDescriptor driverIcon;

  public MapView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    driverIcon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);

    ButterKnife.bind(this);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    // TODO: petar 01/03/2017 This is shit
    MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    this.googleMap = googleMap;
  }

  public void setLocation(Location location) {
    updateMap(new LatLng(location.latitude(), location.longitude()));
    updateTexts(location);
  }

  private void updateTexts(Location location) {
    accuracy.setText("Accuracy: " + String.format("%.2f", location.accuracy()));
    speed.setText(String.format("%.2f", location.speed()) + "m/s");
    time.setText(location.dateTime().toString("hh:mm:ss dd.MM.yy"));
  }

  private void updateMap(LatLng latLng) {
    googleMap.clear();

    googleMap.addMarker(toMarker(latLng));

    CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15f).build();
    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
  }

  private MarkerOptions toMarker(LatLng latLng) {
    return new MarkerOptions().position(latLng).title("Current Position").icon(driverIcon);
  }
}
