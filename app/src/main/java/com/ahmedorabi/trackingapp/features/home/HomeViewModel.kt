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


    val ui = MutableLiveData(TripUI.EMPTY)


    fun initProviders(activity: FragmentActivity) {
        locationProvider = LocationProvider(activity)
        stepCounter = StepCounter(activity)
        onViewCreated(activity)


    }


    private fun onViewCreated(activity: FragmentActivity) {


        locationProvider.liveLocations.observe(activity) { locations ->
            val current = ui.value
            ui.value = current?.copy(userPath = locations)
        }

        locationProvider.liveLocation.observe(activity) { currentLocation ->
            val current = ui.value
            ui.value = current?.copy(currentLocation = currentLocation)
        }

        locationProvider.liveDistance.observe(activity) { distance ->
            val current = ui.value
            val formattedDistance = activity.getString(R.string.distance_value, distance)
            ui.value = current?.copy(formattedDistance = formattedDistance)
        }

        stepCounter.liveSteps.observe(activity) { steps ->
            val current = ui.value
            ui.value = current?.copy(formattedPace = "$steps")
        }

    }


    fun startTracking() {
        stepCounter.setupStepCounter()
        locationProvider.getUserLocation()
        locationProvider.trackUser()

        val currentUi = ui.value
        ui.value = currentUi?.copy(
            formattedPace = TripUI.EMPTY.formattedPace,
            formattedDistance = TripUI.EMPTY.formattedDistance
        )
    }

    fun stopTracking() {
        locationProvider.stopTracking()
        stepCounter.unloadStepCounter()


    }

}