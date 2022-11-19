package com.ahmedorabi.trackingapp.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(tripEntity: TripEntity)

    @Query("Select * from trip")
    suspend fun getAllTrips(): List<TripEntity>

}