package com.carpool.carpool.login;

import com.carpool.carpool.mvp.MvpPresenter;
import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/** Created by petar on 20/02/2017. */
public class LogInPresenter extends MvpPresenter<LogInView> {

  private CompositeSubscription subscription = new CompositeSubscription();

  @Inject
  public LogInPresenter() {
    // TODO: petar 21/02/2017 temporary for dagger
  }

  @Override
  public void onLoad() {
    subscription.add(observeLogInClicks());
  }

  @Override
  public void onDestroy() {
    subscription.clear();
  }

  private Subscription observeLogInClicks() {
    return RxView.clicks(getView().logIn)
        .subscribe(
            i -> getView().email.setText("A"),
            e -> Timber.e(e, "Error while observing logInClicks"));
  }
}
