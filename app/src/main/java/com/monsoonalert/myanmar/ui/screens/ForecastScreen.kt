package com.monsoonalert.myanmar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.monsoonalert.myanmar.data.remote.dto.ForecastDto
import com.monsoonalert.myanmar.data.remote.dto.ForecastItem
import com.monsoonalert.myanmar.ui.ForecastUiState
import com.monsoonalert.myanmar.ui.WeatherViewModel
import com.monsoonalert.myanmar.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ForecastScreen(viewModel: WeatherViewModel) {
    val forecastState by viewModel.forecastState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "5-Day Forecast",
            style = MaterialTheme.typography.headlineMedium,
            color = OnDarkPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Forecast content
        when (val state = forecastState) {
            is ForecastUiState.Loading -> ForecastLoadingContent()
            is ForecastUiState.Success -> ForecastList(state.forecast, viewModel)
            is ForecastUiState.Error -> ForecastErrorContent(state.message) {
                viewModel.refresh()
            }
        }
    }
}

@Composable
private fun ForecastList(forecast: ForecastDto, viewModel: WeatherViewModel) {
    val forecastItems = forecast.forecastList?.groupBy { item ->
        // Group by day
        val timestamp = (item?.timestamp ?: 0) * 1000
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(timestamp))
    } ?: emptyMap()
    
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        forecastItems.forEach { (date, items) ->
            item {
                val firstItem = items.firstOrNull()
                firstItem?.let {
                    DayForecastCard(
                        date = date,
                        forecastItems = items.filterNotNull(),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
private fun DayForecastCard(
    date: String,
    forecastItems: List<ForecastItem>,
    viewModel: WeatherViewModel
) {
    val timestamp = forecastItems.firstOrNull()?.timestamp ?: 0
    val displayDate = remember(timestamp) {
        val sdf = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
        sdf.format(Date(timestamp * 1000))
    }
    
    // Get min/max temps for the day
    val temps = forecastItems.mapNotNull { it.main?.temperature }
    val minTemp = temps.minOrNull()
    val maxTemp = temps.maxOrNull()
    
    // Get predominant weather
    val weatherMain = forecastItems
        .groupBy { it.weather?.firstOrNull()?.main }
        .maxByOrNull { it.value.size }
        ?.key ?: "Unknown"
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Day header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = displayDate,
                        style = MaterialTheme.typography.titleMedium,
                        color = OnDarkPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = weatherMain.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnDarkSecondary
                    )
                }
                
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        imageVector = getWeatherIcon(weatherMain),
                        contentDescription = null,
                        tint = getWeatherColor(weatherMain),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Temperature range
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TemperatureRange(
                    label = "Low",
                    temp = minTemp,
                    viewModel = viewModel
                )
                TemperatureRange(
                    label = "High",
                    temp = maxTemp,
                    viewModel = viewModel
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Hourly breakdown
            Text(
                text = "Hourly",
                style = MaterialTheme.typography.labelLarge,
                color = OnDarkTertiary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                forecastItems.take(4).forEach { item ->
                    HourlyForecastItem(item, viewModel)
                }
            }
        }
    }
}

@Composable
private fun TemperatureRange(label: String, temp: Double?, viewModel: WeatherViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = OnDarkTertiary
        )
        Text(
            text = temp?.let { "${it.toInt()}°" } ?: "--",
            style = MaterialTheme.typography.titleLarge,
            color = if (label == "High") TempWarm else TempCold,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun HourlyForecastItem(item: ForecastItem, viewModel: WeatherViewModel) {
    val time = remember(item.timestamp) {
        val sdf = SimpleDateFormat("h a", Locale.getDefault())
        sdf.format(Date((item.timestamp ?: 0) * 1000))
    }
    
    val weather = item.weather?.firstOrNull()
    
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = time,
            style = MaterialTheme.typography.bodySmall,
            color = OnDarkTertiary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Icon(
            imageVector = getWeatherIcon(weather?.main ?: ""),
            contentDescription = null,
            tint = getWeatherColor(weather?.main ?: ""),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = viewModel.formatTemperature(item.main?.temperature),
            style = MaterialTheme.typography.bodyMedium,
            color = OnDarkPrimary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ForecastLoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MonsoonPrimary)
    }
}

@Composable
private fun ForecastErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = null,
            tint = AlertSevere,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = OnDarkSecondary
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

private fun getWeatherColor(condition: String): androidx.compose.ui.graphics.Color {
    return when (condition.lowercase()) {
        "clear" -> SunnyColor
        "clouds" -> CloudyColor
        "rain", "drizzle" -> RainyColor
        "thunderstorm" -> ThunderColor
        "snow" -> CloudyColor
        else -> MonsoonSecondary
    }
}

private fun getWeatherIcon(condition: String): androidx.compose.ui.graphics.vector.ImageVector {
    return when (condition.lowercase()) {
        "clear" -> Icons.Default.WbSunny
        "clouds" -> Icons.Default.Cloud
        "rain", "drizzle" -> Icons.Default.WaterDrop
        "thunderstorm" -> Icons.Default.FlashOn
        "snow" -> Icons.Default.AcUnit
        else -> Icons.Default.WbSunny
    }
}
