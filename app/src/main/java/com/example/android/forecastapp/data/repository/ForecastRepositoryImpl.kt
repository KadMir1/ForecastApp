package com.example.android.forecastapp.data.repository

import androidx.lifecycle.LiveData
import com.example.android.forecastapp.data.db.dao.CurrentWeatherDao
import com.example.android.forecastapp.data.db.dao.WeatherLocationDao
import com.example.android.forecastapp.data.db.entity.CurrentWeatherEntry
import com.example.android.forecastapp.data.db.entity.WeatherLocation
import com.example.android.forecastapp.data.network.WeatherNetworkDataSource
import com.example.android.forecastapp.data.network.response.CurrentWeatherResponse
import com.example.android.forecastapp.data.provider.LocationProvider
import kotlinx.coroutines.*
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    init {
       weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
           //presist
           presistFetchedCurrentWeather(newCurrentWeather)
       }
    }

    override suspend fun getCurrenWeather(): LiveData<CurrentWeatherEntry> {
       initWeatherData()
        return withContext(Dispatchers.IO) {
            return@withContext currentWeatherDao.getWeather()
       }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun presistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
       currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)

            //persisting location
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            return
        }

        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thrityMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thrityMinutesAgo)
    }
}