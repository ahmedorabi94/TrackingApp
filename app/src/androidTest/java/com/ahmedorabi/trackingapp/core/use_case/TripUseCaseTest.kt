package com.ahmedorabi.trackingapp.core.use_case

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ahmedorabi.trackingapp.core.db.AppDatabase
import com.ahmedorabi.trackingapp.core.db.CurrentLocation
import com.ahmedorabi.trackingapp.core.db.TripDao
import com.ahmedorabi.trackingapp.core.db.TripEntity
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class TripUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var tripDao: TripDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        tripDao = db.tripDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun insertAndGetAllTrip() {

        val trip = TripEntity(
            steps = "2",
            time = 2,
            distance = "23",
            currentLocation = CurrentLocation(0.0, 1.2),
            paths = emptyList()
        )


        runBlocking {
            tripDao.insertTrip(trip)
            val list = tripDao.getAllTrips()
            assertEquals(list.size, 1)
            assertEquals(list[0].steps, "2")
            assertEquals(list[0].time, 2)
            assertEquals(list[0].distance, "23")
        }

    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAllTrips2() {
        val trip = TripEntity(
            steps = "2",
            time = 2,
            distance = "23",
            currentLocation = CurrentLocation(0.0, 1.2),
            paths = emptyList()
        )

        val trip2 = TripEntity(
            steps = "66",
            time = 22,
            distance = "3112",
            currentLocation = CurrentLocation(0.0, 1.2),
            paths = emptyList()
        )

        runBlocking {
            tripDao.insertTrip(trip)
            tripDao.insertTrip(trip2)

            val list = tripDao.getAllTrips()

            assertEquals(list.size, 2)

            assertEquals(list[1].steps, "66")
            assertEquals(list[1].time, 22)
            assertEquals(list[1].distance, "3112")

        }

    }


}