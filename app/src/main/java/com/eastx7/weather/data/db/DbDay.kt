package com.eastx7.weather.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "days"
//    indices = [Index("name"),Index("uuid")]
)

data class DbDay(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var epoch: Long = 0L,
    @ColumnInfo(name = "cloud_cover")
    var cloudCover: Int = 0,
    var pressure: Int = 0,
    var humidity: Int = 0,
    @ColumnInfo(name = "t_min")
    var temperatureMinimum: Double = 0.0,
    @ColumnInfo(name = "t_max")
    var temperatureMaximum: Double = 0.0,
    @ColumnInfo(name = "wind_speed")
    var windSpeed: Double = 0.0
)