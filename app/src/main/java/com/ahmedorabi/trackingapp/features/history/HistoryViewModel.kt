package com.ahmedorabi.trackingapp.features.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedorabi.trackingapp.core.db.TripEntity
import com.ahmedorabi.trackingapp.core.use_case.TripUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val tripUseCase: TripUseCase): ViewModel() {

    private val _allTrips = MutableLiveData<TripEntity>()
    val allTripsData: LiveData<TripEntity>
        get() = _allTrips

    fun getAllTrips() = tripUseCase.getTrips()
}