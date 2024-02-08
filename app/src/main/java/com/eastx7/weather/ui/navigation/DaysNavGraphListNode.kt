package com.eastx7.weather.ui.navigation

import android.Manifest
import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.eastx7.weather.R
import com.eastx7.weather.data.db.DbDay
import com.eastx7.weather.ui.screens.DaysListScreen
import com.eastx7.weather.ui.theme.WeatherTypography
import com.eastx7.weather.utilities.Constants.GPS_MAX_TIME_UI_UPDATE_IN_MILLIS
import com.eastx7.weather.utilities.Constants.GPS_MIN_TIME_UI_UPDATE_IN_MILLIS
import com.eastx7.weather.utilities.Constants.GPS_TIME_UI_UPDATE_IN_MILLIS
import com.eastx7.weather.utilities.Converters
import com.eastx7.weather.viewmodels.DaysListViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.first

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DaysNavGraphListNode(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    conv: Converters,
) {
    val listViewModel: DaysListViewModel = hiltViewModel()
    val context = LocalContext.current
    val coarseLocationPermissionsState =
        rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)

    var fusedLocationClient: FusedLocationProviderClient? = null
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            listViewModel.populateItemsList(locationResult.getLastLocation())
            fusedLocationClient?.removeLocationUpdates(this)
        }
    }
    val srvErrorText by listViewModel.srvErrorText.collectAsState()
    var itemsList by remember { mutableStateOf(listOf<DbDay>()) }
    val stateDbChange by listViewModel.stateDbChange.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    fusedLocationClient?.removeLocationUpdates(locationCallback)
                }

                Lifecycle.Event.ON_RESUME -> {
                    if (coarseLocationPermissionsState.status.isGranted) {
                        val locationRequest =
                            LocationRequest.Builder(
                                Priority.PRIORITY_LOW_POWER,
                                GPS_TIME_UI_UPDATE_IN_MILLIS
                            )
                                .setWaitForAccurateLocation(false)
                                .setMinUpdateIntervalMillis(GPS_MIN_TIME_UI_UPDATE_IN_MILLIS)
                                .setMaxUpdateDelayMillis(GPS_MAX_TIME_UI_UPDATE_IN_MILLIS)
                                .build()

                        fusedLocationClient =
                            LocationServices.getFusedLocationProviderClient(context)
                        fusedLocationClient?.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            Looper.getMainLooper()
                        )
                    } else {
                        coarseLocationPermissionsState.launchPermissionRequest()
                    }
                }

                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(
        key1 = stateDbChange,
        block = {
            itemsList = listViewModel.listOfItems().first()
        }
    )

    DaysListScreen(
        daysList = itemsList,
        conv = conv,
        onDayItemClick = {
            weatherNavGraphNavigate(
                navController,
                Destinations.DaysItem.route,
                it.id
            )
        },
        onBottomBarButtonClick = { listViewModel.setTextError(R.string.not_implemented_yet) },
        onBack = { },
        textError = srvErrorText,
        clearTextError = { listViewModel.clearTextError() },
        bodyTextStyle = WeatherTypography.bodyMedium,
    )
}
