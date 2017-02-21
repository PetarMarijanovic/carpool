package com.carpool.carpool.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** Created by petar on 21/02/2017. */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ApplicationContext {}
