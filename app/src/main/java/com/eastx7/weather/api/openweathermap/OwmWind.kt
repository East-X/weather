package com.eastx7.weather.api.openweathermap

import com.google.gson.annotations.SerializedName

data class OwmWind(
    @SerializedName("speed")
    val speed: Double = 0.0,
    @SerializedName("deg")
    val direction: Int = 0,
)
