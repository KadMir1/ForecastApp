package com.example.android.forecastapp.data.network.response


import com.example.android.forecastapp.data.db.entity.CurrentWeatherEntry
import com.example.android.forecastapp.data.db.entity.WeatherLocation
import com.example.android.forecastapp.data.db.entity.Request
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: WeatherLocation,
    val request: Request
)