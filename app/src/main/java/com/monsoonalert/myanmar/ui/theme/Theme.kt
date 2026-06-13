package com.monsoonalert.myanmar.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = MonsoonPrimary,
    onPrimary = Color.White,
    primaryContainer = MonsoonPrimaryDark,
    onPrimaryContainer = Color.White,
    
    secondary = MonsoonSecondary,
    onSecondary = Color.White,
    secondaryContainer = MonsoonSecondaryDark,
    onSecondaryContainer = Color.White,
    
    tertiary = MonsoonTertiary,
    onTertiary = Color.Black,
    tertiaryContainer = MonsoonTertiaryDark,
    onTertiaryContainer = Color.Black,
    
    background = DarkBackground,
    onBackground = OnDarkPrimary,
    
    surface = DarkSurface,
    onSurface = OnDarkPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = OnDarkSecondary,
    
    error = AlertSevere,
    onError = Color.White,
    errorContainer = AlertSevere.copy(alpha = 0.2f),
    onErrorContainer = AlertSevere,
    
    outline = OutlineDark,
    outlineVariant = DividerDark,
    
    scrim = ScrimDark,
    
    inverseSurface = OnDarkPrimary,
    inverseOnSurface = DarkBackground,
    inversePrimary = MonsoonPrimaryLight
)

@Composable
fun MonsoonAlertTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disabled for consistent brand experience
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> DarkColorScheme // Always use dark theme for monsoon aesthetic
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
