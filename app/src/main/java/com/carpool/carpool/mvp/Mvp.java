package com.carpool.carpool.mvp;

import com.google.auto.value.AutoValue;

import android.support.v4.view.ViewCompat;
import android.view.View;

/** Created by petar on 20/02/2017. */
@AutoValue
public abstract class Mvp<P extends MvpPresenter<V>, V extends View> {

  public static <P extends MvpPresenter<V>, V extends View> Mvp<P, V> create(P presenter, V view) {

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

    return new AutoValue_Mvp<>(presenter, view);
  }

  public abstract P presenter();

  public abstract V view();
}
