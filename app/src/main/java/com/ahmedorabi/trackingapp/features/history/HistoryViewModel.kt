package com.ahmedorabi.trackingapp.features.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedorabi.trackingapp.core.db.TripEntity
import com.ahmedorabi.trackingapp.core.use_case.TripUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val tripUseCase: TripUseCase) : ViewModel() {

    private val _allTrips = MutableLiveData<List<TripEntity>>()
    val allTrips: LiveData<List<TripEntity>>
        get() = _allTrips

    init {
        getAllTrips()
    }

    private fun getAllTrips() {
        viewModelScope.launch(Dispatchers.IO) {
            _allTrips.postValue(tripUseCase.getTrips())

        }
    }

}