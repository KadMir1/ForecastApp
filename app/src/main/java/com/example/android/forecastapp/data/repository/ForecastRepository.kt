package com.example.android.forecastapp.data.repository

import androidx.lifecycle.LiveData
import com.example.android.forecastapp.data.db.entity.CurrentWeatherEntry
import com.example.android.forecastapp.data.db.entity.WeatherLocation


interface ForecastRepository {
    suspend fun getCurrenWeather(): LiveData<CurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}