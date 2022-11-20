package com.ahmedorabi.trackingapp.core.db

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Entity(tableName = "trip")
@Parcelize
data class TripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val steps: String,
    val time: Long = 0,
    val distance: String,
    @Embedded
    val currentLocation: CurrentLocation,
    val paths: List<LatLng>
) : Parcelable
