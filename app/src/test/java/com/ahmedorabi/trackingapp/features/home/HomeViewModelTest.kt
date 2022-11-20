package com.ahmedorabi.trackingapp.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmedorabi.trackingapp.TestCoroutineRule
import com.ahmedorabi.trackingapp.core.db.CurrentLocation
import com.ahmedorabi.trackingapp.core.db.TripEntity
import com.ahmedorabi.trackingapp.core.use_case.TripUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var useCase: TripUseCase


    @Before
    fun setup() {
        viewModel = HomeViewModel(useCase)
    }


    @Test
    fun test_addTrip() {

        testCoroutineRule.runBlockingTest {

            viewModel.addTrip(
                steps = "2",
                distance = "",
                currentLocation = LatLng(0.9, 9.9),
                time = 1111,
                paths = emptyList()
            )

            Mockito.verify(useCase).addTrip(
                TripEntity(
                    steps = "2",
                    distance = "",
                    currentLocation = CurrentLocation(
                        0.9,
                        9.9
                    ),
                    paths = emptyList(),
                    time = 1111
                )
            )

        }
    }


}