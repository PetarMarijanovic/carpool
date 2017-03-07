package com.carpool.carpool.map;

import com.carpool.carpool.mvp.MvpPresenter;
import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/** Created by petar on 20/02/2017. */
public class MapPresenter extends MvpPresenter<MapView> {

  private final MapModel model;
  private final CompositeSubscription subscription = new CompositeSubscription();

  @Inject
  public MapPresenter(MapModel model) {
    this.model = model;
  }

  @Override
  public void onLoad() {
    subscription.add(observeLogInClicks());
    subscription.add(observeLongLogInClicks());
    subscription.add(observeLocation());
  }

  @Override
  public void onDestroy() {
    subscription.clear();
  }

  private Subscription observeLocation() {
    return model
        .location()
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            location -> getView().setLocation(location),
            e -> Timber.e(e, "Error while observing location"));
  }

  private Subscription observeLogInClicks() {
    return RxView.clicks(getView().start)
        .subscribe(i -> model.startService(), e -> Timber.e(e, "Error while observing clicks"));
  }

  private Subscription observeLongLogInClicks() {
    return RxView.clicks(getView().stop)
        .subscribe(i -> model.stopService(), e -> Timber.e(e, "Error while observing long clicks"));
  }
}
