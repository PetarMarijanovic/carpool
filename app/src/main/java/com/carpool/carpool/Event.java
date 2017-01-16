package com.carpool.carpool;

import com.google.auto.value.AutoValue;

import org.joda.time.DateTime;

/** Created by petar on 16/01/2017. */
@AutoValue
public abstract class Event {

  public static Event create(DateTime date, String name) {
    return new AutoValue_Event(date, name);
  }

  public abstract DateTime date();

  public abstract String name();
}
