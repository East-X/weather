package com.eastx7.weather.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eastx7.weather.utilities.Constants
import com.eastx7.weather.utilities.Converters
import java.util.*

@SuppressLint("MissingPermission")
@Composable
fun DaysNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    conv: Converters,
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = Destinations.DaysItem.daysLink,
            arguments = listOf(
                navArgument(Destinations.DaysItem.id) {
                    type = NavType.IntType; defaultValue = 0
                }
            )
        ) { backStackEntry ->
            DaysNavGraphItemNode(
                navController = navController,
                backStackEntry = backStackEntry,
                conv = conv
            )
        }
        composable(
            route = Destinations.DaysList.route
        ) { backStackEntry ->
            DaysNavGraphListNode(
                navController = navController,
                backStackEntry = backStackEntry,
                conv = conv
            )
        }
    }
}

fun weatherNavGraphNavigate(
    navController: NavHostController,
    route: String,
    id: Int
) {
    val savedStateHandle = navController.previousBackStackEntry
        ?.savedStateHandle
    savedStateHandle?.set(Constants.TAG_ID_REF, id)
    navController.navigate("$route/$id")
}