package com.eastx7.weather.api.openweathermap

import com.google.gson.annotations.SerializedName
import java.util.*

data class OwmHistory(
   @SerializedName("city_id")
   val cityId: Int,
   @SerializedName("list")
   val days: List<OwmHistoryDay>
)
