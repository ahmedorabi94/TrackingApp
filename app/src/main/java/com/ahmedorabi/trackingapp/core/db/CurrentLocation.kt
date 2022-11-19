package com.ahmedorabi.trackingapp.core.db

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentLocation(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) : Parcelable