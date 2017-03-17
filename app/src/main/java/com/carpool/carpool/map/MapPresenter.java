package com.carpool.carpool.map;

import com.carpool.carpool.mvp.MvpPresenter;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/** Created by petar on 20/02/2017. */
public class MapPresenter extends MvpPresenter<MapView> {

  private final MapModel model;
  private final CompositeDisposable disposables = new CompositeDisposable();

  @Inject
  public MapPresenter(MapModel model) {
    this.model = model;
  }

  @Override
  public void onLoad() {
    disposables.add(observeLogInClicks());
    disposables.add(observeLongLogInClicks());
    disposables.add(observeLocation());
  }

  @Override
  public void onDestroy() {
    disposables.clear();
  }

  private Disposable observeLocation() {
    return model
        .location()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            location -> getView().setLocation(location),
            e -> Timber.e(e, "Error while observing location"));
  }

  private Disposable observeLogInClicks() {
    return RxView.clicks(getView().start)
        .subscribe(i -> model.startService(), e -> Timber.e(e, "Error while observing clicks"));
  }

  private Disposable observeLongLogInClicks() {
    return RxView.clicks(getView().stop)
        .subscribe(i -> model.stopService(), e -> Timber.e(e, "Error while observing long clicks"));
  }
}
