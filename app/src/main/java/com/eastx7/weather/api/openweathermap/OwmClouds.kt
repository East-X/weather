package com.eastx7.weather.api.openweathermap

import com.google.gson.annotations.SerializedName

data class OwmClouds(
    @SerializedName("all")
    val percent: Int = 0
)
