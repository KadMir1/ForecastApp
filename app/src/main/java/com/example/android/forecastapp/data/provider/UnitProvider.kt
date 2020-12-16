package com.example.android.forecastapp.data.provider

import com.example.android.forecastapp.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}