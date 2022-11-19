package com.ahmedorabi.trackingapp.core.use_case

import androidx.lifecycle.LiveData
import com.ahmedorabi.trackingapp.core.db.TripDao
import com.ahmedorabi.trackingapp.core.db.TripEntity
import javax.inject.Inject

class TripUseCaseImpl @Inject constructor(private val tripDao: TripDao) : TripUseCase {


    override suspend fun addTrip(tripEntity: TripEntity) {
        tripDao.insertTrip(tripEntity)
    }

    override fun getTrips(): LiveData<List<TripEntity>> {
        return tripDao.getAllTrips()
    }
}