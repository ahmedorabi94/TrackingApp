package com.ahmedorabi.trackingapp.core.db

import android.os.Parcelable
import androidx.room.ColumnInfo
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
    val distance : String,
    @Embedded
    val currentLocation: CurrentLocation,
    val paths : List<LatLng>
) : Parcelable{

    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TripEntity

       // if (id != other.id) return false
        if (steps != other.steps) return false
       // if (time != other.time) return false
        if (distance != other.distance) return false
        if (currentLocation != other.currentLocation) return false
        if (paths != other.paths) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + steps.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + distance.hashCode()
        result = 31 * result + currentLocation.hashCode()
        result = 31 * result + paths.hashCode()
        return result
    }
}