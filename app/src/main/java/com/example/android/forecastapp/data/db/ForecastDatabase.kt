package com.example.android.forecastapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.forecastapp.data.db.dao.CurrentWeatherDao
import com.example.android.forecastapp.data.db.dao.WeatherLocationDao
import com.example.android.forecastapp.data.db.entity.CurrentWeatherEntry
import com.example.android.forecastapp.data.db.entity.WeatherLocation
import com.example.android.forecastapp.internal.Converters


@Database(entities = [CurrentWeatherEntry::class, WeatherLocation::class],  version = 1)
@TypeConverters(value = [(Converters::class)])
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao

    companion object {
       @Volatile
       private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            ForecastDatabase::class.java, "forecast.db")
                .build()
    }
}