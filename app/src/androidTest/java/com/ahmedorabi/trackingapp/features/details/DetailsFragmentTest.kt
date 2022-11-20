package com.ahmedorabi.trackingapp.features.details

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ahmedorabi.trackingapp.R
import com.ahmedorabi.trackingapp.core.db.CurrentLocation
import com.ahmedorabi.trackingapp.core.db.TripEntity
import com.ahmedorabi.trackingapp.features.getTimeFormatted
import com.google.android.gms.maps.model.LatLng
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {


    @Test
    fun testDetailsFragment() {

        val trip = TripEntity(
            steps = "9",
            time = 2555,
            distance = "235",
            currentLocation = CurrentLocation(0.0, 1.2),
            paths = arrayListOf(LatLng(9.0, 8.9))
        )

        val bundle = Bundle()
        bundle.putParcelable("trip_entity", trip)


        val scenario = launchFragmentInContainer<DetailsFragment>(bundle)

        scenario.moveToState(Lifecycle.State.RESUMED)


        onView(withId(R.id.map)).check(matches(isDisplayed()))
        onView(withId(R.id.distanceTv)).check(matches(isDisplayed()))
        onView(withId(R.id.stepsTv)).check(matches(isDisplayed()))
        onView(withId(R.id.trackTimeTv)).check(matches(isDisplayed()))

        onView(withId(R.id.trackTimeTv)).check(matches(withText(trip.time.getTimeFormatted())))
        onView(withId(R.id.stepsTv)).check(matches(withText(trip.steps)))
        onView(withId(R.id.distanceTv)).check(matches(withText(trip.distance)))


    }

}