package com.eastx7.weather.api.openweathermap

import com.google.gson.annotations.SerializedName

data class OwmHistoryDayParameters(
    @SerializedName("temp")
    val temperatureNow: Double = 0.0,
    @SerializedName("temp_max")
    val temperatureMaximum: Double = 0.0,
    @SerializedName("temp_min")
    val temperatureMinimum: Double = 0.0,
    @SerializedName("pressure")
    val pressure: Int = 0,
    @SerializedName("humidity")
    val humidity: Int = 0,
)
