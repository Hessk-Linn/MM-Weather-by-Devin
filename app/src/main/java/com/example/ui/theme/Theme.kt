package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MonsoonDarkColorScheme = darkColorScheme(
    primary = ElegantPrimary,
    secondary = ElegantSecondary,
    tertiary = ElegantTertiary,
    background = ElegantDarkBg,
    surface = ElegantSurface,
    onPrimary = OnElegantPrimary,
    onSecondary = Color.Black,
    onTertiary = OnElegantSurface,
    onBackground = OnElegantSurface,
    onSurface = OnElegantSurface,
    onSurfaceVariant = OnElegantSurfaceVariant,
    error = WarningPink,
    errorContainer = ElegantSurface,
    primaryContainer = ElegantSurface,
    secondaryContainer = ElegantSurface,
    surfaceVariant = ElegantSurface,
    outline = ElegantDivider
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MonsoonDarkColorScheme,
        typography = Typography,
        content = content
    )
}
