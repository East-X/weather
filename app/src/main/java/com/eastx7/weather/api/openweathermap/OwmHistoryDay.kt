package com.eastx7.weather.api.openweathermap

import com.google.gson.annotations.SerializedName

data class OwmHistoryDay(
    @SerializedName("dt")
    val epoch: Long = 0L,
    @SerializedName("main")
    val parameters: OwmHistoryDayParameters,
    @SerializedName("clouds")
    val cloudCover: OwmClouds,
    @SerializedName("wind")
    val wind: OwmWind
)
