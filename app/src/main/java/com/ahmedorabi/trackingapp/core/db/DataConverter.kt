package com.ahmedorabi.trackingapp.core.db

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DataConverter {

    @TypeConverter
    fun arrayListToJson(countryLang: ArrayList<LatLng?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<LatLng?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun jsonToArrayList(countryLangString: String?): ArrayList<LatLng>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<LatLng?>?>() {}.type
        return gson.fromJson<ArrayList<LatLng>>(countryLangString, type)
    }
}