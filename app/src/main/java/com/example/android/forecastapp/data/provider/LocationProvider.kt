package com.example.android.forecastapp.data.provider

import com.example.android.forecastapp.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String
}