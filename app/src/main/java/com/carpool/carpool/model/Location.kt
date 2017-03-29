package com.carpool.carpool.model

import org.joda.time.DateTime

/** Created by petar on 29/03/2017. */
data class Location(val latitude: Double,
                    val longitude: Double,
                    val speed: Double,
                    val time: DateTime,
                    val accuracy: Double)
