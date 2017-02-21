package com.carpool.carpool.mvp;

/** Created by petar on 20/02/2017. */
public abstract class MvpPresenter<V> {

  private V view = null;

  final void takeView(V view) {
    if (view == null) throw new NullPointerException("new view must not be null");
    if (this.view == view) return;

    if (this.view != null) dropView(this.view);

    this.view = view;
    onLoad();
  }

  final void dropView(V view) {
    if (view == null) throw new NullPointerException("dropped view must not be null");
    if (view != this.view) return;

    onDestroy();
    this.view = null;
  }

  protected final V getView() {
    return view;
  }

  protected final boolean hasView() {
    return view != null;
  }

  public abstract void onLoad();

  public abstract void onDestroy();
}
