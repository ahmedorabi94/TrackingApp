package com.ahmedorabi.trackingapp.features

fun Long.getTimeFormatted(): String {

    val seconds = (this / 1000).toInt() % 60
    val minutes = (this / (1000 * 60) % 60).toInt()
    val hours = (this / (1000 * 60 * 60) % 24).toInt()

    return "$hours : $minutes : $seconds"

//         return   String.format("%d min, %d sec",
//                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
//                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
//                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds))
//            );

//            Timber.e("time " + dateInMillis)
//            val formatter = SimpleDateFormat("mm:ss")
//            return formatter.format(Date(dateInMillis))


}
