package com.carpool.carpool;

import com.google.firebase.database.DataSnapshot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.util.Map;

/** Created by petar on 26/02/2017. */
public class FirebaseMapper {

  private static final ObjectMapper MAPPER_INSTANCE = new ObjectMapper();

  static {
    MAPPER_INSTANCE.registerModule(new JodaModule());
    MAPPER_INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public static <T> T asType(DataSnapshot fromSnapshot, Class<T> toValueType) {
    return MAPPER_INSTANCE.convertValue(fromSnapshot.getValue(), toValueType);
  }

  public static Map<String, Object> asMap(Object value) {
    TypeReference ref = new TypeReference<Map<String, Object>>() {
          // no body
        };
    return MAPPER_INSTANCE.convertValue(value, ref);
  }
}
