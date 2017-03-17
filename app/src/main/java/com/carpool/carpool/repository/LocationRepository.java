package com.carpool.carpool.repository;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.carpool.carpool.FirebaseMapper;
import com.carpool.carpool.model.Location;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import timber.log.Timber;

/** Created by petar on 24/02/2017. */
public class LocationRepository {

  private BehaviorSubject<Location> subject = BehaviorSubject.create();

  private DatabaseReference locationRef;

  public LocationRepository(FirebaseDatabase database) {
    locationRef = database.getReference("location");

    locationRef.addValueEventListener(
        new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            Location location = FirebaseMapper.asType(dataSnapshot, Location.class);
            if (location != null) subject.onNext(location);
          }

          @Override
          public void onCancelled(DatabaseError error) {
            Timber.e(error.toException(), "Error while observing location");
          }
        });
  }

  public Observable<Location> location() {
    // TODO: petar 17/03/2017 asObservable?
    return subject;
  }

  public void save(Location location) {
    locationRef.setValue(FirebaseMapper.asMap(location));
  }
}
