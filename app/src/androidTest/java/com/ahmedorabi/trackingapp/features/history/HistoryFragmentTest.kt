package com.ahmedorabi.trackingapp.features.history

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ahmedorabi.trackingapp.R
import com.ahmedorabi.trackingapp.launchFragmentInHiltContainer
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoryFragmentTest {


    @Test
    fun testHistoryFragment() {


        val scenario = launchFragmentInHiltContainer<HistoryFragment>()


        onView(withId(R.id.recycler_view_main)).check(matches(isDisplayed()))


    }

}