package com.eastx7.weather.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val startEndPaddings = 2.dp
val thicknessDivider = 2.dp

@Composable
fun HorizontalDivider() {
    Divider(color = Color.Black, thickness = thicknessDivider)
}

@Composable
fun VertFieldSpacer() {
    Spacer(modifier = Modifier.height(12.dp))
}
