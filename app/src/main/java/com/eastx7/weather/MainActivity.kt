package com.eastx7.weather

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.eastx7.weather.ui.navigation.DaysNavGraph
import com.eastx7.weather.ui.navigation.Destinations
import com.eastx7.weather.ui.theme.WeatherTheme
import com.eastx7.weather.utilities.Converters
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var conv: Converters
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                DaysNavGraph(
                    startDestination = Destinations.DaysList.route,
                    conv = conv
                )
            }
        }
    }
}
