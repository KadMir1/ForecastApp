package com.example.android.forecastapp.data.db.entity


import com.google.gson.annotations.SerializedName

data class Request(
    val language: String,
    val query: String,
    val type: String,
    val unit: String
)