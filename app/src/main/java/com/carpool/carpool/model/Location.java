package com.carpool.carpool.model;

import com.google.auto.value.AutoValue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.joda.time.DateTime;

/** Created by petar on 26/02/2017. */
@AutoValue
@JsonDeserialize(builder = AutoValue_Location.Builder.class)
public abstract class Location {

  public static Builder builder() {
    return new AutoValue_Location.Builder();
  }

  @JsonProperty("latitude")
  public abstract double latitude();

  @JsonProperty("longitude")
  public abstract double longitude();

  @JsonProperty("speed")
  public abstract double speed(); // m/s

  @JsonProperty("time")
  public abstract DateTime dateTime();

  @JsonProperty("accuracy")
  public abstract double accuracy();

  @AutoValue.Builder
  public abstract static class Builder {

    @JsonProperty("latitude")
    public abstract Builder latitude(double value);

    @JsonProperty("longitude")
    public abstract Builder longitude(double value);

    @JsonProperty("speed")
    public abstract Builder speed(double value);

    @JsonProperty("time")
    public abstract Builder dateTime(DateTime value);

    @JsonProperty("accuracy")
    public abstract Builder accuracy(double value);

    public abstract Location build();
  }
}
