package com.ahmedorabi.trackingapp.core.use_case

import com.ahmedorabi.trackingapp.core.db.TripEntity

interface TripUseCase {

    suspend fun addTrip(tripEntity: TripEntity)

    fun getTrips() : List<TripEntity>
}