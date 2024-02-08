package com.eastx7.weather.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.eastx7.weather.data.db.DbDay
import com.eastx7.weather.ui.navigation.Destinations
import com.eastx7.weather.ui.theme.WeatherTheme
import com.eastx7.weather.utilities.Converters

@Preview(showBackground = true)
@Composable
fun DayItemScreenPreview() {
    WeatherTheme {
        DayItemScreen(
            item = DbDay(
                id = 1707228000,
                epoch = 2024037L,
                cloudCover = 75,
                pressure = 978,
                humidity = 92,
                temperatureMinimum = -18.5,
                temperatureMaximum = -12.3,
                windSpeed = 5.2
            ),
            conv = Converters(),
            onBack = { }
        )
    }
}