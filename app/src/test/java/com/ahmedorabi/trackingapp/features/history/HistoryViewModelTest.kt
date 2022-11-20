package com.ahmedorabi.trackingapp.features.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ahmedorabi.trackingapp.TestCoroutineRule
import com.ahmedorabi.trackingapp.core.db.CurrentLocation
import com.ahmedorabi.trackingapp.core.db.TripEntity
import com.ahmedorabi.trackingapp.core.use_case.TripUseCase
import junit.framework.Assert.assertEquals
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
class HistoryViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()


    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: HistoryViewModel

    @Mock
    private lateinit var tripsObserver: Observer<List<TripEntity>>

    @Mock
    private lateinit var useCase: TripUseCase

    private val trip = TripEntity(
        steps = "2",
        time = 2,
        distance = "23",
        currentLocation = CurrentLocation(0.0, 1.2),
        paths = emptyList()
    )

    private val trip2 = TripEntity(
        steps = "66",
        time = 22,
        distance = "3112",
        currentLocation = CurrentLocation(0.0, 1.2),
        paths = emptyList()
    )

    @Before
    fun setup() {
        viewModel = HistoryViewModel(useCase)
    }


    @Test
    fun test_view_model_getAllTrips_with_response() {

        val result = listOf(trip, trip2)

        testCoroutineRule.runBlockingTest {

            Mockito.doReturn(result)
                .`when`(useCase)
                .getTrips()

            viewModel = HistoryViewModel(useCase)

            viewModel.allTrips.observeForever(tripsObserver)

            Mockito.verify(tripsObserver).onChanged(result)

            assertEquals(viewModel.allTrips.value, result)
            assertEquals(viewModel.allTrips.value?.get(0)?.steps, "2")


            viewModel.allTrips.removeObserver(tripsObserver)

        }

    }


}