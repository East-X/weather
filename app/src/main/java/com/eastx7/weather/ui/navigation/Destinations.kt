package com.eastx7.weather.ui.navigation

sealed class Destinations(val route: String) {

    object DaysList : Destinations("days_list")
    object DaysItem : Destinations("days_item") {
        const val daysLink: String = "days_item/{id}"
        const val id: String = "id"
    }
}