package com.eastx7.weather.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.eastx7.weather.ui.screens.DayItemScreen
import com.eastx7.weather.utilities.Converters
import com.eastx7.weather.viewmodels.DaysItemViewModel

@Composable
fun DaysNavGraphItemNode(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    conv: Converters,
) {
    val itemViewModel: DaysItemViewModel = hiltViewModel()
    val dayId = backStackEntry.arguments?.getInt(Destinations.DaysItem.id) ?: 0
    itemViewModel.setDayItemId(dayId)
    val dbDay by itemViewModel.dbDay.collectAsState()
    dbDay?.let {
        DayItemScreen(
            item = it,
            conv = conv,
            onBack = { navController.navigate(Destinations.DaysList.route) }
        )
    }
}

