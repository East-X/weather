package com.eastx7.weather.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.eastx7.weather.R
import com.eastx7.weather.data.db.DbDay

import com.eastx7.weather.ui.navigation.DayListScreenType
import com.eastx7.weather.ui.theme.HorizontalDivider
import com.eastx7.weather.ui.theme.VertFieldSpacer
import com.eastx7.weather.ui.theme.WeatherTypography
import com.eastx7.weather.ui.theme.startEndPaddings
import com.eastx7.weather.ui.utilities.BackPressHandler
import com.eastx7.weather.ui.utilities.ErrorLabel
import com.eastx7.weather.utilities.Converters

@Composable
fun DaysListScreen(
    daysList: List<DbDay>,
    conv: Converters,
    onDayItemClick: (DbDay) -> Unit,
    onBottomBarButtonClick: (DayListScreenType) -> Unit,
    onBack: () -> Unit,
    textError: Int,
    clearTextError: () -> Unit,
    bodyTextStyle: TextStyle,
) {
    val context = LocalContext.current

    BackPressHandler(onBackPressed = onBack)

    Scaffold(
        topBar = {
            DaysListTopBar(
                title = stringResource(R.string.app_name),
            )
        },
        bottomBar = {
            DaysListBottomBar(
                onButtonClick = onBottomBarButtonClick
            )

        },
        content = { innerPadding ->
            DaysListBody(
                innerPadding = innerPadding,
                itemsList = daysList,
                conv = conv,
                context = context,
                onItemClick = onDayItemClick,
                clearTextError = clearTextError,
                textError = textError,
                textStyle = bodyTextStyle
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaysListBody(
    innerPadding: PaddingValues,
    itemsList: List<DbDay>,
    conv: Converters,
    context: Context,
    onItemClick: (DbDay) -> Unit,
    clearTextError: () -> Unit,
    textError: Int,
    textStyle: TextStyle,
) {

    val modifier = Modifier
        .padding(top = 2.dp, bottom = 2.dp, end = startEndPaddings, start = startEndPaddings)

    Column(
        modifier = Modifier.testTag("days_list_body")
            .padding(
                top = innerPadding.calculateTopPadding(),
                start = startEndPaddings,
                end = startEndPaddings,
                bottom = innerPadding.calculateBottomPadding(),
            )
            .fillMaxWidth()
            .statusBarsPadding(),
    ) {
        ErrorLabel(textError, textStyle, clearTextError)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(startEndPaddings),//.Center,
        ) {
            items(
                items = itemsList,
                key = { item ->
                    item.id
                }
            )
            { item ->
                Card(
                    modifier = Modifier.testTag("days_list_card"),
                    onClick = {
                        onItemClick(item)
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
                ) {
                    DaysListRowBody(
                        item = item,
                        modifier = modifier,
                        conv = conv,
                        context = context,
                    )
                }
            }
        }
    }
}

@Composable
fun DaysListRowBody(
    item: DbDay,
    modifier: Modifier,
    conv: Converters,
    context: Context,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {

        DaysListRowBodyDate(
            item = item,
            conv = conv,
            context = context
        )
        Column {
            Row {
                DaysListRowBodyCloudCover(
                    item = item,
                )
                VertFieldSpacer()
                DaysListRowBodyHumidity(
                    item = item,
                )
                VertFieldSpacer()
                DaysListRowBodyPressure(
                    item = item,
                )
            }
            Row {
                DaysListRowBodyTemperature(
                    item = item,
                )
                VertFieldSpacer()
                DaysListRowBodyWindSpeed(
                    item = item,
                )
            }
        }
    }
    HorizontalDivider()
}

@Composable
fun DaysListRowBodyHumidity(
    item: DbDay,
) {
    DaysListRowBodyIcon(
        imageVector = Icons.Outlined.WaterDrop,
        contentDescription = "Humidity"
    )
    DaysListRowBodyText(item.humidity.toString())
}

@Composable
fun DaysListRowBodyPressure(
    item: DbDay,
) {
    DaysListRowBodyIcon(
        imageVector = Icons.Outlined.PlayForWork,
        contentDescription = "Pressure"
    )
    DaysListRowBodyText(item.pressure.toString())
}

@Composable
fun DaysListRowBodyTemperature(
    item: DbDay,
) {
    DaysListRowBodyIcon(
        imageVector = Icons.Outlined.DeviceThermostat,
        contentDescription = "Temperature"
    )
    DaysListRowBodyText("${item.temperatureMinimum} / ${item.temperatureMaximum}")
}

@Composable
fun DaysListRowBodyWindSpeed(
    item: DbDay,
) {
    DaysListRowBodyIcon(
        imageVector = Icons.Outlined.WindPower,
        contentDescription = "Wind"
    )
    DaysListRowBodyText(item.windSpeed.toString())
}

@Composable
fun DaysListRowBodyText(text: String) {
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
fun DaysListRowBodyIcon(
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

@Composable
fun DaysListRowBodyDate(
    item: DbDay,
    conv: Converters,
    context: Context
) {
    DaysListRowBodyText(conv.epochToHumanDate(item.epoch, context))
}

@Composable
fun DaysListRowBodyCloudCover(
    item: DbDay,
) {
    DaysListRowBodyIcon(
        imageVector = Icons.Outlined.Cloud,
        contentDescription = "Cloud"
    )
    DaysListRowBodyText(item.cloudCover.toString())
}

@Composable
fun DaysListBottomBar(
    onButtonClick: (DayListScreenType) -> Unit,
) {
    val activeColor = MaterialTheme.colorScheme.primary
    BottomAppBar(
        actions = {
            IconButton(
                onClick = { onButtonClick(DayListScreenType.UPDATE) }
            ) {
                Icon(
                    Icons.Outlined.Update,
                    tint = activeColor,
                    contentDescription = "Update"
                )
            }
            IconButton(
                onClick = { onButtonClick(DayListScreenType.HOME) }
            ) {
                Icon(
                    Icons.Outlined.Home,
                    tint = activeColor,
                    contentDescription = "Current",
                )
            }
            IconButton(
                onClick = { onButtonClick(DayListScreenType.WARNING) }
            ) {
                Icon(
                    Icons.Outlined.Warning,
                    tint = activeColor,
                    contentDescription = "Urgent",
                )
            }
            IconButton(
                onClick = { onButtonClick(DayListScreenType.MAP) }
            ) {
                Icon(
                    Icons.Outlined.Map,
                    contentDescription = "Map",
                )
            }
            IconButton(
                onClick = { onButtonClick(DayListScreenType.FLASH) }
            ) {
                Icon(
                    Icons.Outlined.FlashOn,
                    contentDescription = "Flash",
                )

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaysListTopBar(
    title: String,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
            )
        },
    )
}