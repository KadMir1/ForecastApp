package com.example.android.forecastapp.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.android.forecastapp.data.provider.UnitProvider
import com.example.android.forecastapp.data.repository.ForecastRepository
import com.example.android.forecastapp.internal.UnitSystem
import com.example.android.forecastapp.internal.lazyDeffered

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeffered {
        forecastRepository.getCurrenWeather()
    }

    val weatherLocation by lazyDeffered {
        forecastRepository.getWeatherLocation()
    }
}