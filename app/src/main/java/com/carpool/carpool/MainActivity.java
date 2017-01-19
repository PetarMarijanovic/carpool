package com.carpool.carpool;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java8.util.stream.StreamSupport;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

  // TODO: petar 12/01/2017 Robolectric
  // TODO: petar 12/01/2017 Flow
  // TODO: petar 12/01/2017 MVP

  @Inject SharedPreferences prefs;

  @BindView(R.id.text)
  TextView text;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    if (text == null) Timber.d("Butterknife error!");

    ((CarPoolApplication) getApplication()).getApplicationComponent().inject(this);
    if (prefs == null) Timber.d("Dagger error!");

    Observable.just(1)
        .subscribeOn(Schedulers.computation())
        .map(v -> null)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(value -> Timber.d(String.valueOf(value)), e -> Timber.e(e, "Error"));
  }

  @OnClick(R.id.text)
  void changeText() {

    text.setText(Event.create(DateTime.now(), "name").toString());

    List<String> logs = new ArrayList<>();
    logs.add("log1");
    logs.add("log2");
    StreamSupport.stream(logs).forEach(Timber::d);
  }
}
