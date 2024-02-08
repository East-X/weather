package com.eastx7.weather.ui.utilities

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import com.eastx7.weather.ui.theme.WeatherShapes
import com.eastx7.weather.ui.theme.startEndPaddings

@Composable
fun ErrorLabel(
    textRes: Int,
    textStyle: TextStyle,
    clearTextError: () -> Unit
) {
    if (textRes != 0) {
        Box(
            modifier = Modifier
                .padding(all = 10.dp)
                .clip(WeatherShapes.medium)
                .clickable(onClick = clearTextError)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.errorContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(textRes),
                style = textStyle.copy(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(
                    start = startEndPaddings,
                    end = 48.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
            )
            Icon(
                Icons.Outlined.Cancel,
                contentDescription = "Close",
                modifier = Modifier
                    .padding(all = 10.dp)
                    .align(Alignment.CenterEnd),
                tint = MaterialTheme.colorScheme.error
            )

        }
    }
}
