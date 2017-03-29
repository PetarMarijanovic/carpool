package com.carpool.carpool.mvp;

import android.support.v4.view.ViewCompat;
import android.view.View;

/** Created by petar on 20/02/2017. */
public class Mvp<P extends MvpPresenter<V>, V extends View> {

  public P presenter;
  public V view;

  private Mvp(P presenter, V view) {
    this.presenter = presenter;
    this.view = view;
  }

  public static <P extends MvpPresenter<V>, V extends View> Mvp<P, V> create(
      final P presenter, final V view) {

    // TODO: petar 17/03/2017 RxView

    view.addOnAttachStateChangeListener(
        new View.OnAttachStateChangeListener() {
          @Override
          public void onViewAttachedToWindow(View v) {
            presenter.takeView(view);
          }

          @Override
          public void onViewDetachedFromWindow(View v) {
            presenter.dropView(view);
          }
        });

    if (ViewCompat.isAttachedToWindow(view)) presenter.takeView(view);

    return new Mvp(presenter, view);
  }
}
