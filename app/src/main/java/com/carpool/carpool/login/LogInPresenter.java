package com.carpool.carpool.login;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;

import com.carpool.carpool.GPS_Service;
import com.carpool.carpool.dagger.ActivityContext;
import com.carpool.carpool.mvp.MvpPresenter;
import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/** Created by petar on 20/02/2017. */
public class LogInPresenter extends MvpPresenter<LogInView> {

  private final Context context;
  private final DatabaseReference testRef;
  private final CompositeSubscription subscription = new CompositeSubscription();

  @Inject
  public LogInPresenter(@ActivityContext Context context) {
    this.context = context;
    testRef = FirebaseDatabase.getInstance().getReference("test");
  }

  @Override
  public void onLoad() {
    subscription.add(observeLogInClicks());
    subscription.add(observeLongLogInClicks());

    testRef.addValueEventListener(
        new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            String value = dataSnapshot.getValue(String.class);
            if (value == null) value = "null";
            getView().email.setText(value);
          }

          @Override
          public void onCancelled(DatabaseError error) {
            getView().email.setText(error.toException().getMessage());
          }
        });
  }

  @Override
  public void onDestroy() {
    subscription.clear();
  }

  private Subscription observeLogInClicks() {
    return RxView.clicks(getView().logIn)
        .subscribe(i -> startService(), e -> Timber.e(e, "Error while observing clicks"));
  }

  private Subscription observeLongLogInClicks() {
    return RxView.longClicks(getView().logIn)
        .subscribe(i -> stopService(), e -> Timber.e(e, "Error while observing long clicks"));
  }

  private void startService() {
    context.startService(new Intent(context, GPS_Service.class));
  }

  private void stopService() {
    context.stopService(new Intent(context, GPS_Service.class));
  }
}
