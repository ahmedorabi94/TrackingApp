package com.ahmedorabi.trackingapp.features.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ahmedorabi.trackingapp.R
import com.ahmedorabi.trackingapp.launchFragmentInHiltContainer
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {


    @Test
    fun testHomeFragment() {


        launchFragmentInHiltContainer<HomeFragment>()

        onView(withId(R.id.startBtn)).check(matches(isDisplayed()))
        onView(withId(R.id.endBtn)).check(matches(isDisplayed()))

    }

    @Test
    fun test_start_button_HomeFragment() {


        launchFragmentInHiltContainer<HomeFragment>()


        onView(withId(R.id.startBtn)).check(matches(isDisplayed()))
        onView(withId(R.id.endBtn)).check(matches(isDisplayed()))

        onView(withId(R.id.startBtn)).perform(click())

        Thread.sleep(2000)

        //  onView(withId(R.id.distanceTv)).check(matches(isDisplayed()))
        // onView(withId(R.id.stepsTv)).check(matches(isDisplayed()))
        onView(withId(R.id.trackTimeTv)).check(matches(isDisplayed()))
        onView(withId(R.id.circularProgressBar)).check(matches(isDisplayed()))


    }
}