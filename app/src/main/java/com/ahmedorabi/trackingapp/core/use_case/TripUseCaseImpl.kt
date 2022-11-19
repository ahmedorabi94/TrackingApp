package com.ahmedorabi.trackingapp.core.use_case

import com.ahmedorabi.trackingapp.core.db.TripDao
import com.ahmedorabi.trackingapp.core.db.TripEntity
import javax.inject.Inject

class TripUseCaseImpl @Inject constructor(private val tripDao: TripDao) : TripUseCase {


    override suspend fun addTrip(tripEntity: TripEntity) {
        tripDao.insertTrip(tripEntity)
    }

    override suspend fun getTrips(): List<TripEntity> {
        return tripDao.getAllTrips()
    }
}