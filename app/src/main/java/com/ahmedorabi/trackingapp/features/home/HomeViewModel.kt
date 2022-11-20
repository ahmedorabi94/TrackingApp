package com.ahmedorabi.trackingapp.features.home

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedorabi.trackingapp.R
import com.ahmedorabi.trackingapp.core.db.CurrentLocation
import com.ahmedorabi.trackingapp.core.db.TripEntity
import com.ahmedorabi.trackingapp.core.use_case.TripUseCase
import com.ahmedorabi.trackingapp.features.home.providers.LocationProvider
import com.ahmedorabi.trackingapp.features.home.providers.StepCounter
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tripUseCase: TripUseCase
) : ViewModel() {

    private lateinit var locationProvider: LocationProvider

    private lateinit var stepCounter: StepCounter


    fun addTrip(
        steps: String,
        distance: String,
        currentLocation: LatLng,
        paths: List<LatLng>,
        time: Long = 0
    ) {

        val trip = TripEntity(
            steps = steps,
            distance = distance,
            currentLocation = CurrentLocation(
                currentLocation.latitude,
                currentLocation.longitude
            ),
            paths = paths,
            time = time
        )

        viewModelScope.launch {
            tripUseCase.addTrip(trip)

        }

    }


    val tripState = MutableLiveData(TripUI.EMPTY)


    fun initProviders(activity: FragmentActivity) {
        locationProvider = LocationProvider(activity)
        stepCounter = StepCounter(activity)
        observeUserTrip(activity)
    }


    private fun observeUserTrip(activity: FragmentActivity) {

        locationProvider.liveLocations.observe(activity) { locations ->
            val current = tripState.value
            tripState.value = current?.copy(userPath = locations)
        }

        locationProvider.liveLocation.observe(activity) { currentLocation ->
            val current = tripState.value
            tripState.value = current?.copy(currentLocation = currentLocation)
        }

        locationProvider.liveDistance.observe(activity) { distance ->
            val current = tripState.value
            val formattedDistance = activity.getString(R.string.distance_value, distance)
            tripState.value = current?.copy(distance = formattedDistance)
        }

        stepCounter.liveSteps.observe(activity) { steps ->
            val current = tripState.value
            tripState.value = current?.copy(steps = "$steps")
        }

    }


    fun startTracking() {
        stepCounter.setupStepCounter()
        locationProvider.trackUser()
        locationProvider.getUserLocation()

        val currentUi = tripState.value
        tripState.value = currentUi?.copy(
            steps = TripUI.EMPTY.steps,
            distance = TripUI.EMPTY.distance
        )
    }

    fun stopTracking() {
        locationProvider.stopTracking()
        stepCounter.unloadStepCounter()


    }

}