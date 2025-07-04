package com.serhiihrabas.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val lightColorScheme = lightColorScheme(
    primary = Color(0xFF5977F7),
    onPrimary = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1B1B1C),
    onSurfaceVariant = Color(0xFF535364),
    surface = Color(0xFFEFEFF2),
    error = Color(0xFFE1294B)
)

@Composable
fun IndicadoTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = lightColorScheme,
        typography = Typography,
        content = content
    )
}