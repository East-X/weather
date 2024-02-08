package com.eastx7.weather.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.eastx7.weather.R
import com.eastx7.weather.data.db.DbDay
import com.eastx7.weather.ui.theme.WeatherTheme
import com.eastx7.weather.ui.theme.WeatherTypography
import com.eastx7.weather.utilities.Converters

@Preview(showBackground = true)
@Composable
fun DaysListScreenPreview() {
    WeatherTheme {
        DaysListScreen(
            daysList = listOf(
                DbDay(
                    id = 1707228000,
                    epoch = 2024037L,
                    cloudCover = 75,
                    pressure = 978,
                    humidity = 92,
                    temperatureMinimum = -18.5,
                    temperatureMaximum = -12.3,
                    windSpeed = 5.2
                )
            ),
            conv = Converters(),
            onDayItemClick = { },
            onBottomBarButtonClick = { },
            onBack = { },
            textError = R.string.unknown_exception,
            clearTextError = { },
            bodyTextStyle = WeatherTypography.bodyMedium,
        )
    }
}