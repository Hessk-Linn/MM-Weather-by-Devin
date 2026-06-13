package com.monsoonalert.myanmar.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.monsoonalert.myanmar.data.remote.dto.CurrentWeatherDto
import com.monsoonalert.myanmar.ui.WeatherUiState
import com.monsoonalert.myanmar.ui.WeatherViewModel
import com.monsoonalert.myanmar.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: WeatherViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val location by viewModel.selectedLocation.collectAsState()
    
    val pullRefreshState = rememberPullToRefreshState()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header with location
            LocationHeader(location?.nameEn ?: "Myanmar", location?.nameMy ?: "")
            
            // Weather content
            AnimatedContent(
                targetState = uiState,
                label = "weather_content"
            ) { state ->
                when (state) {
                    is WeatherUiState.Loading -> LoadingContent()
                    is WeatherUiState.Success -> WeatherContent(
                        weather = state.weather,
                        viewModel = viewModel,
                        lastUpdated = state.lastUpdated
                    )
                    is WeatherUiState.Error -> ErrorContent(
                        message = state.message,
                        onRetry = { viewModel.refresh() }
                    )
                }
            }
        }
        
        // Pull to refresh indicator
        if (pullRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                viewModel.refresh()
            }
        }
    }
}

@Composable
private fun LocationHeader(nameEn: String, nameMy: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MonsoonPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = nameEn,
                    style = MaterialTheme.typography.titleLarge,
                    color = OnDarkPrimary
                )
            }
            if (nameMy.isNotEmpty()) {
                Text(
                    text = nameMy,
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnDarkTertiary,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }
        }
    }
}

@Composable
private fun WeatherContent(
    weather: CurrentWeatherDto,
    viewModel: WeatherViewModel,
    lastUpdated: Long
) {
    val main = weather.main
    val wind = weather.wind
    val weatherDesc = weather.weather?.firstOrNull()
    
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Main weather card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Weather icon placeholder (would use actual weather icons)
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(getWeatherColor(weatherDesc?.main ?: "")),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getWeatherIcon(weatherDesc?.main ?: ""),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(56.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Temperature
                Text(
                    text = viewModel.formatTemperature(main?.temperature),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Light,
                        letterSpacing = (-2).sp
                    ),
                    color = OnDarkPrimary
                )
                
                Text(
                    text = weatherDesc?.description?.replaceFirstChar { it.uppercase() } ?: "Unknown",
                    style = MaterialTheme.typography.titleMedium,
                    color = OnDarkSecondary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Feels like ${viewModel.formatTemperature(main?.feelsLike)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnDarkTertiary
                )
            }
        }
        
        // Weather details grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WeatherDetailCard(
                icon = Icons.Default.WaterDrop,
                label = "Humidity",
                value = "${main?.humidity ?: 0}%",
                modifier = Modifier.weight(1f)
            )
            WeatherDetailCard(
                icon = Icons.Default.Air,
                label = "Wind",
                value = "${wind?.speed?.toInt() ?: 0} m/s",
                modifier = Modifier.weight(1f)
            )
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WeatherDetailCard(
                icon = Icons.Default.Compress,
                label = "Pressure",
                value = "${main?.pressure ?: 0} hPa",
                modifier = Modifier.weight(1f)
            )
            WeatherDetailCard(
                icon = Icons.Default.Visibility,
                label = "Visibility",
                value = "${(weather.visibility ?: 0) / 1000} km",
                modifier = Modifier.weight(1f)
            )
        }
        
        // Last updated
        Text(
            text = "Updated: ${java.text.SimpleDateFormat("MMM d, h:mm a", java.util.Locale.getDefault()).format(java.util.Date(lastUpdated))}",
            style = MaterialTheme.typography.bodySmall,
            color = OnDarkTertiary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun WeatherDetailCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MonsoonSecondary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = OnDarkPrimary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = OnDarkTertiary
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MonsoonPrimary)
    }
}

@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AlertSevere.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null,
                tint = AlertSevere,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = OnDarkPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = MonsoonPrimary)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Retry")
            }
        }
    }
}

private fun getWeatherColor(condition: String): androidx.compose.ui.graphics.Color {
    return when (condition.lowercase()) {
        "clear", "sunny" -> SunnyColor
        "clouds", "cloudy" -> CloudyColor
        "rain", "drizzle" -> RainyColor
        "thunderstorm" -> ThunderColor
        "snow" -> CloudyColor
        "mist", "fog" -> FoggyColor
        else -> MonsoonSecondary
    }
}

private fun getWeatherIcon(condition: String): androidx.compose.ui.graphics.vector.ImageVector {
    return when (condition.lowercase()) {
        "clear", "sunny" -> Icons.Default.WbSunny
        "clouds", "cloudy" -> Icons.Default.Cloud
        "rain", "drizzle" -> Icons.Default.WaterDrop
        "thunderstorm" -> Icons.Default.FlashOn
        "snow" -> Icons.Default.AcUnit
        "mist", "fog" -> Icons.Default.Cloud
        else -> Icons.Default.WbSunny
    }
}
