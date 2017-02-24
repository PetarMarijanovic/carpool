package com.carpool.carpool.login;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.carpool.carpool.mvp.MvpPresenter;
import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/** Created by petar on 20/02/2017. */
public class LogInPresenter extends MvpPresenter<LogInView> {

  private final DatabaseReference testRef;
  private final CompositeSubscription subscription = new CompositeSubscription();

  @Inject
  public LogInPresenter() {
    testRef = FirebaseDatabase.getInstance().getReference("test");
  }

  @Override
  public void onLoad() {
    subscription.add(observeLogInClicks());

    testRef.addValueEventListener(
        new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            String value = dataSnapshot.getValue(String.class);
            if (value == null) value = "null";
            getView().password.setText(value);
          }

          @Override
          public void onCancelled(DatabaseError error) {
            getView().password.setText(error.toException().getMessage());
          }
        });
  }

  @Override
  public void onDestroy() {
    subscription.clear();
  }

  private Subscription observeLogInClicks() {
    return RxView.clicks(getView().logIn)
        .subscribe(
            i -> testRef.setValue(getView().email.getText().toString()),
            e -> Timber.e(e, "Error while observing logInClicks"));
  }
}
