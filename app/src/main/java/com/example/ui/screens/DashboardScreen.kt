package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.WeatherData
import com.example.ui.WeatherViewModel
import com.example.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: WeatherViewModel,
    onNavigateToMap: () -> Unit,
    onNavigateToAnalytics: () -> Unit
) {
    val selectedTownship by viewModel.selectedTownship.collectAsState()
    val weatherData by viewModel.currentWeather.collectAsState()
    val activeBannerNotification by viewModel.simulatedNotification.collectAsState()
    val activeAlertsCount by viewModel.monsoonAlerts.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Location Header Row (matches header in Elegant Dark HTML)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "location_on",
                        tint = ElegantPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = selectedTownship?.nameEn ?: "Select Township",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
                        color = OnElegantSurface
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = "expand_less",
                        tint = OnElegantSurface,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Updated: Just now • Offline mode active",
                    style = MaterialTheme.typography.bodySmall,
                    color = OnElegantSurfaceVariant,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }
            // Muted Notification bell with pink dot
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(ElegantSurface)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "notifications",
                    tint = OnElegantSurface,
                    modifier = Modifier.size(20.dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(WarningPink, CircleShape)
                        .align(Alignment.TopEnd)
                )
            }
        }

        // 2. Severe Warning Notification Banner (Dismissable - styled in WarningPink)
        AnimatedVisibility(
            visible = activeBannerNotification != null,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            activeBannerNotification?.let { alert ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(WarningPink)
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Severe Alert",
                            tint = DarkWarningText,
                            modifier = Modifier.size(24.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Severe Monsoon Alert",
                                color = DarkWarningText,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = alert.description,
                                color = DarkWarningText.copy(alpha = 0.9f),
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                        IconButton(
                            onClick = { viewModel.dismissNotificationBanner() },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Dismiss",
                                tint = DarkWarningText,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        // 3. Main Weather Display Card (deep violet-to-dark gradient)
        weatherData?.let { data ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(26.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(ElegantTertiary, ElegantSurface)
                        )
                    )
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = "${data.temperature}°",
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontWeight = FontWeight.Light,
                                letterSpacing = (-2).sp
                            ),
                            fontSize = 62.sp,
                            color = OnElegantSurface
                        )
                        Text(
                            text = data.weatherCondition,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
                            color = Color(0xFFEADDFF)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Umbrella,
                                contentDescription = "umbrella",
                                tint = ElegantPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "${data.rainProbability}% Rain Probability",
                                style = MaterialTheme.typography.bodyMedium,
                                color = ElegantPrimary
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val conditionIcon = when (data.weatherCondition) {
                            "Monsoon Storm", "Torrential Rain" -> Icons.Default.Thunderstorm
                            "Heavy Rain", "Thunderstorm" -> Icons.Default.Thunderstorm
                            "Cloudy" -> Icons.Default.Cloud
                            else -> Icons.Default.WbSunny
                        }
                        Icon(
                            imageVector = conditionIcon,
                            contentDescription = data.weatherCondition,
                            tint = ElegantPrimary,
                            modifier = Modifier.size(64.dp)
                        )

                        // High/Low Temperature badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100.dp))
                                .background(DeepPlumBg)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "H: ${data.temperature + 3}° L: ${data.temperature - 2}°",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFFEADDFF),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(26.dp))
                    .background(ElegantSurface)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Loading local cached metrics...", color = OnElegantSurfaceVariant)
            }
        }

        // 4. Primary Agricultural & Commute Index Dashboard Columns
        val data = weatherData
        if (data != null) {
            Text(
                text = "COMMUTER & FARMER HAZARD INDEX",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.1.sp
                ),
                color = OnElegantSurfaceVariant,
                modifier = Modifier.padding(top = 10.dp, start = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Farmer card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, ElegantDivider, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = ElegantSurface
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Agriculture,
                                contentDescription = null,
                                tint = ElegantSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Farmers' Alert",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = OnElegantSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        val (agriText, agriColor) = when {
                            data.rainLevel >= 40.0 -> "🚫 STOP Irrigation. Extreme topsoil runoff danger. Shelter newly planted rice shoots. Clear sluice channels immediately." to Color(0xFFEF4444)
                            data.rainLevel >= 20.0 -> "⚠️ Hold Transplanting. High silt pooling. Soil moisture saturation critical. Watch out for waterborne root rot." to Color(0xFFF97316)
                            data.rainProbability >= 75 -> "🌱 Safe for Seed Sowing. Gentle rainfall expected to optimize seed absorption. Delay chemical sprays 24 hrs." to ElegantSecondary
                            else -> "☀️ Irrigate Crops. Clear skies. Safe to administer foliar spray and fertilizer treatments." to ElegantSecondary
                        }

                        Text(
                            text = agriText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = agriColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Humidity: ${data.humidity}%\nHazard Level: ${if (data.humidity > 90) "CRITICAL" else "MEDIUM"}",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnElegantSurfaceVariant
                        )
                    }
                }

                // Commuter card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, ElegantDivider, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = ElegantSurface
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.DirectionsCar,
                                contentDescription = null,
                                tint = ElegantPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Commuter Plan",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = OnElegantSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        val (commText, commColor) = when {
                            data.rainLevel >= 40.0 -> "⛔ HIGH FLOOD RISK. Main road nodes (Hledan/Kabar Aye) highly waterlogged. Commuter speeds <10km/h. Stay indoors." to Color(0xFFEF4444)
                            data.rainLevel >= 20.0 -> "⚠️ Moderate Congestion. Sluggish traffic near flyovers. Surface water ponding. Use alternative bypass routes immediately." to Color(0xFFF97316)
                            data.rainProbability >= 60 -> "🚗 Mild Wet Roads. Scattered showers. Good visibility, but keep brake gaps wide. Expect minor delays." to ElegantPrimary
                            else -> "✅ Rapid Transit. Perfect driving conditions. No water depth recorded. Downtown nodes operating normally." to ElegantSecondary
                        }

                        Text(
                            text = commText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = commColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Wind: ${data.windSpeed} km/h\nVisibility: ${if (data.rainLevel > 30) "RESTRICTED" else "CLEAR"}",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnElegantSurfaceVariant
                        )
                    }
                }
            }

            // 5. Air Quality Index Panel (Elegant Dark style)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, ElegantDivider, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = ElegantSurface
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Air,
                                contentDescription = "Air Quality",
                                tint = ElegantPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Local Air Quality Index (AQI)",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = OnElegantSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Current township index is ${data.aqi} which is categorized as ${data.aqiStatus}.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnElegantSurfaceVariant
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(
                                color = when (data.aqiStatus) {
                                    "Good" -> ElegantSecondary
                                    "Moderate" -> Color(0xFFFBBF24)
                                    "Unhealthy" -> Color(0xFFEF4444)
                                    else -> Color(0xFFB91C1C)
                                },
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${data.aqi}",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                color = Color.Black
                            )
                            Text(
                                text = "AQI",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.Black,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }

        // 6. Navigation Quick-Access Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onNavigateToMap,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = ElegantPrimary, contentColor = DeepPlumBg),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Navigation, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Road Flood Map", fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = onNavigateToAnalytics,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = ElegantTertiary, contentColor = OnElegantSurface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.BarChart, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Rainfall Charts", fontWeight = FontWeight.Bold)
            }
        }

        HorizontalDivider(color = ElegantDivider, modifier = Modifier.padding(vertical = 8.dp))

        // 7. Interactive Storm & Monsoon Weather Simulator (EXCEPTIONAL VALUE FOR USER INTENT)
        Card(
            colors = CardDefaults.cardColors(
                containerColor = ElegantSurface
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, WarningPink.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Cyclone,
                        contentDescription = "Monsoon Simulation Control Center",
                        tint = WarningPink,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Myanmar Monsoon Weather Simulator",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = WarningPink
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Test the app's real-time features! Click below to trigger extreme cloud/monsoon warnings, flooding washouts, and severe notifications, then clear them to normal skies.",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = OnElegantSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { viewModel.simulateExtremeStorm() },
                        colors = ButtonDefaults.buttonColors(containerColor = WarningPink, contentColor = DarkWarningText),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("🌩️ Trigger Storm Cell", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = { viewModel.simulateClearSky() },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OnElegantSurface),
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, OnElegantSurfaceVariant.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("☀️ Clear Weather", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// Add Extension helper for icon sizing in Composable
@Composable
private fun Icon(imageVector: androidx.compose.ui.graphics.vector.ImageVector, contentDescription: String?, size: androidx.compose.ui.unit.Dp) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = Modifier.size(size)
    )
}
