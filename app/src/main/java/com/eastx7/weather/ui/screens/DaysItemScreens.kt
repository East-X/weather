package com.eastx7.weather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import com.eastx7.weather.R
import com.eastx7.weather.data.db.DbDay
import com.eastx7.weather.ui.theme.VertFieldSpacer
import com.eastx7.weather.ui.theme.WeatherTypography
import com.eastx7.weather.ui.theme.startEndPaddings
import com.eastx7.weather.ui.utilities.BackPressHandler
import com.eastx7.weather.utilities.Converters

@Composable
fun DayItemScreen(
    item: DbDay,
    conv: Converters,
    onBack: () -> Unit,
) {

    val context = LocalContext.current

    BackPressHandler(onBackPressed = onBack)

    Scaffold(
        topBar = {
            DayItemTopBar(
                title = conv.epochToHumanDate(item.epoch, context),
                onBack = onBack,
            )
        },
        content = { innerPadding ->
            DayBodyItem(
                innerPadding = innerPadding,
                item = item
            )
        }
    )
}

@Composable
fun DayBodyItem(
    innerPadding: PaddingValues,
    item: DbDay
) {
    Column(
        modifier = Modifier
            .padding(
                top = innerPadding.calculateTopPadding(),
                start = startEndPaddings,
                end = startEndPaddings
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DayBodyItemTemperature(
            item = item,
        )
        DayBodyItemCloudCover(
            item = item,
        )
        DayBodyItemHumidity(
            item = item,
        )
        DayBodyItemPressure(
            item = item,
        )
        DayBodyItemWindSpeed(
            item = item,
        )
    }
}

@Composable
fun DayBodyItemTemperature(
    item: DbDay
) {
    VertFieldSpacer()
    DayBodyItemText(stringResource(R.string.temperature))
    VertFieldSpacer()
    Text(
        text = "${item.temperatureMinimum} / ${item.temperatureMaximum}",
        style = WeatherTypography.headlineLarge,
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
fun DayBodyItemCloudCover(
    item: DbDay,
) {
    VertFieldSpacer()
    Row {
        DayBodyItemIcon(
            imageVector = Icons.Outlined.Cloud,
            contentDescription = "Cloud"
        )
        DayBodyItemText("${stringResource(R.string.cloud_cover)} :")
        DayBodyItemText("${item.cloudCover} %")
    }
    VertFieldSpacer()
}

@Composable
fun DayBodyItemHumidity(
    item: DbDay,
) {
    VertFieldSpacer()
    Row {
        DayBodyItemIcon(
            imageVector = Icons.Outlined.WaterDrop,
            contentDescription = "Humidity"
        )
        DayBodyItemText("${stringResource(R.string.humidity)} :")
        DayBodyItemText("${item.humidity} %")
    }
    VertFieldSpacer()
}

@Composable
fun DayBodyItemPressure(
    item: DbDay,
) {
    VertFieldSpacer()
    Row {
        DayBodyItemIcon(
            imageVector = Icons.Outlined.PlayForWork,
            contentDescription = "Pressure"
        )
        DayBodyItemText("${stringResource(R.string.pressure)} :")
        DayBodyItemText("${item.pressure} ${stringResource(R.string.mm_hg)}")
    }
    VertFieldSpacer()
}

@Composable
fun DayBodyItemWindSpeed(
    item: DbDay,
) {
    VertFieldSpacer()
    Row {
        DayBodyItemIcon(
            imageVector = Icons.Outlined.WindPower,
            contentDescription = "Wind"
        )
        DayBodyItemText("${stringResource(R.string.wind_speed)} :")
        DayBodyItemText("${item.windSpeed} ${stringResource(R.string.m_s)}")
    }
    VertFieldSpacer()
}

@Composable
fun DayBodyItemText(text: String) {
    Text(
        text = text,
        style = WeatherTypography.bodyMedium,
        modifier = Modifier.padding(
            start = startEndPaddings,
            end = startEndPaddings,
        )
    )
}

@Composable
fun DayBodyItemIcon(
    imageVector: ImageVector,
    contentDescription: String
) {
    Icon(
        modifier = Modifier.padding(
            start = startEndPaddings,
            end = startEndPaddings,
        ),
        imageVector = imageVector,
        contentDescription = contentDescription
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayItemTopBar(
    title: String,
    onBack: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "back"
                )
            }
        },
    )
}