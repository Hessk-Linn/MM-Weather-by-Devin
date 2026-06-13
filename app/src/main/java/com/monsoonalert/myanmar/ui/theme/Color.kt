package com.monsoonalert.myanmar.ui.theme

import androidx.compose.ui.graphics.Color

// Professional Myanmar Monsoon Weather App Color Palette
// Based on deep monsoon clouds and tropical rain aesthetics

// Primary Colors - Deep Monsoon Blue/Purple
val MonsoonPrimary = Color(0xFF6366F1)        // Indigo - main brand color
val MonsoonPrimaryDark = Color(0xFF4338CA)   // Dark indigo - pressed states
val MonsoonPrimaryLight = Color(0xFF818CF8)  // Light indigo - hover states

// Secondary Colors - Rain Water Tones
val MonsoonSecondary = Color(0xFF0EA5E9)     // Sky blue - secondary actions
val MonsoonSecondaryDark = Color(0xFF0284C7) // Dark sky blue
val MonsoonSecondaryLight = Color(0xFF38BDF8)// Light sky blue

// Tertiary Colors - Storm Warning
val MonsoonTertiary = Color(0xFFF59E0B)      // Amber - warnings
val MonsoonTertiaryDark = Color(0xFFD97706)  // Dark amber
val MonsoonTertiaryLight = Color(0xFFFBBF24) // Light amber

// Background Colors - Dark Monsoon Theme
val DarkBackground = Color(0xFF0F172A)       // Very dark slate - main background
val DarkSurface = Color(0xFF1E293B)          // Dark slate - card surfaces
val DarkSurfaceVariant = Color(0xFF334155)   // Medium slate - elevated surfaces

// Text Colors
val OnDarkPrimary = Color(0xFFF8FAFC)        // Almost white - primary text
val OnDarkSecondary = Color(0xFFCBD5E1)       // Light gray - secondary text
val OnDarkTertiary = Color(0xFF94A3B8)       // Medium gray - tertiary text

// Weather Condition Colors
val SunnyColor = Color(0xFFF59E0B)             // Amber for sunny
val CloudyColor = Color(0xFF94A3B8)          // Gray for cloudy
val RainyColor = Color(0xFF3B82F6)            // Blue for rain
val StormyColor = Color(0xFF6366F1)            // Indigo for storms
val ThunderColor = Color(0xFF8B5CF6)          // Purple for thunder
val FoggyColor = Color(0xFF64748B)             // Slate for fog

// Alert Colors
val AlertSevere = Color(0xFFDC2626)          // Red for severe alerts
val AlertModerate = Color(0xFFEA580C)        // Orange for moderate
val AlertAdvisory = Color(0xFFF59E0B)        // Amber for advisories
val AlertNormal = Color(0xFF22C55E)          // Green for all clear

// Temperature Gradients
val TempCold = Color(0xFF3B82F6)             // Blue for cold
val TempCool = Color(0xFF0EA5E9)             // Sky for cool
val TempMild = Color(0xFF22C55E)             // Green for mild
val TempWarm = Color(0xFFF59E0B)             // Amber for warm
val TempHot = Color(0xFFEF4444)              // Red for hot

// Additional UI Colors
val DividerDark = Color(0xFF334155)          // Dark divider
val OutlineDark = Color(0xFF475569)          // Dark outline
val ScrimDark = Color(0xFF000000).copy(alpha = 0.6f) // Modal scrim
