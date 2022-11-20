package com.ahmedorabi.trackingapp.features

fun Long.getTimeFormatted(): String {

    val seconds = (this / 1000).toInt() % 60
    val minutes = (this / (1000 * 60) % 60).toInt()
    val hours = (this / (1000 * 60 * 60) % 24).toInt()

    return "$hours : $minutes : $seconds"

}
