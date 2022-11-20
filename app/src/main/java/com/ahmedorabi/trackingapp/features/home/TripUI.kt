package com.ahmedorabi.trackingapp.features.home

import com.google.android.gms.maps.model.LatLng

data class TripUI(
    val steps: String,
    val distance: String,
    val currentLocation: LatLng?,
    val userPath: List<LatLng>
) {

    companion object {

        val EMPTY = TripUI(
            steps = "",
            distance = "",
            currentLocation = null,
            userPath = emptyList()
        )
    }
}