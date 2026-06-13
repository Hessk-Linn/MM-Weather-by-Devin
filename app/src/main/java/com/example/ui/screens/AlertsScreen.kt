package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsScreen(viewModel: WeatherViewModel) {
    val alerts by viewModel.monsoonAlerts.collectAsState()
    val townships by viewModel.allTownships.collectAsState()

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Form states
    var selectedTownshipIdForReport by remember { mutableStateOf(1) }
    var townshipDropdownExpanded by remember { mutableStateOf(false) }
    var selectedCondition by remember { mutableStateOf("Monsoon Storm") }
    var conditionDropdownExpanded by remember { mutableStateOf(false) }
    var rainLevel by remember { mutableStateOf(40f) }
    var rainProbability by remember { mutableStateOf(95) }
    var windSpeed by remember { mutableStateOf(35f) }
    var humidity by remember { mutableStateOf(90) }

    val conditions = listOf("Monsoon Storm", "Torrential Rain", "Heavy Rain", "Thunderstorm", "Cloudy", "Fair")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Alert Threats Panel
        Text(
            text = "ACTIVE MONSOON ALERTS (${alerts.size})",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )

        if (alerts.isEmpty()) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.padding(24.dp), contentAlignment = Alignment.Center) {
                    Text(
                        "No active monsoon threats recorded. Sky systems nominal.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            alerts.forEach { alert ->
                val severityColor = when (alert.severity) {
                    "Extreme" -> Color(0xFFEF4444)
                    "Severe" -> Color(0xFFF97316)
                    else -> Color(0xFFEAB308)
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = severityColor.copy(alpha = 0.08f)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, severityColor.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = severityColor,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = alert.title,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = severityColor
                                )
                            }

                            // Timestamp formatter
                            val dateString = SimpleDateFormat("HH:mm • MMM d", Locale.US).format(Date(alert.timestamp))
                            Text(
                                text = dateString,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                fontSize = 10.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = alert.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // 2. Offline Participatory Crowd-Sourced Weather reporter form
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Campaign,
                        contentDescription = "Report Alert",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Local Crowdsourced Wetter Report",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    "Isolated storm cells occur rapidly. Logging an offline weather observation directly informs nearby farmers and commuters. Saves directly to your offline local cache.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                // Select Township
                Column {
                    Text("Select Your Township Area", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        val selectedTownship = townships.find { it.id == selectedTownshipIdForReport }
                        OutlinedButton(
                            onClick = { townshipDropdownExpanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = selectedTownship?.nameEn ?: "Select Township")
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                        DropdownMenu(
                            expanded = townshipDropdownExpanded,
                            onDismissRequest = { townshipDropdownExpanded = false }
                        ) {
                            townships.take(20).forEach { township ->
                                DropdownMenuItem(
                                    text = { Text("${township.nameEn} (ကမာရွတ်)") },
                                    onClick = {
                                        selectedTownshipIdForReport = township.id
                                        townshipDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Select Condition
                Column {
                    Text("Current Sky Status", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { conditionDropdownExpanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = selectedCondition)
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                        DropdownMenu(
                            expanded = conditionDropdownExpanded,
                            onDismissRequest = { conditionDropdownExpanded = false }
                        ) {
                            conditions.forEach { cond ->
                                DropdownMenuItem(
                                    text = { Text(cond) },
                                    onClick = {
                                        selectedCondition = cond
                                        conditionDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Sliding selectors: rain level, probability
                Column {
                    Text("Rain Intensity Speed: ${rainLevel.toInt()} mm/h", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                    Slider(
                        value = rainLevel,
                        onValueChange = { rainLevel = it },
                        valueRange = 0f..100f,
                        steps = 9
                    )
                }

                Column {
                    Text("Precipitation Chance: $rainProbability %", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                    Slider(
                        value = rainProbability.toFloat(),
                        onValueChange = { rainProbability = it.toInt() },
                        valueRange = 0f..100f,
                        steps = 9
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Wind: ${windSpeed.toInt()} km/h", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                        Slider(
                            value = windSpeed,
                            onValueChange = { windSpeed = it },
                            valueRange = 0f..60f
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Humidity: $humidity %", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                        Slider(
                            value = humidity.toFloat(),
                            onValueChange = { humidity = it.toInt() },
                            valueRange = 50f..100f
                        )
                    }
                }

                Button(
                    onClick = {
                        viewModel.insertUserReport(
                            townshipId = selectedTownshipIdForReport,
                            condition = selectedCondition,
                            level = String.format("%.1f", rainLevel.toDouble()).toDouble(),
                            prob = rainProbability,
                            wind = String.format("%.1f", windSpeed.toDouble()).toDouble(),
                            hum = humidity
                        )
                        val tName = townships.find { it.id == selectedTownshipIdForReport }?.nameEn ?: "Local"
                        Toast.makeText(context, "Successfully updated database cache for $tName!", Toast.LENGTH_LONG).show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.CloudUpload, contentDescription = null, size = 18.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Publish Offline Incident Report")
                }
            }
        }
    }
}

// Icon adapter
@Composable
private fun Icon(imageVector: androidx.compose.ui.graphics.vector.ImageVector, contentDescription: String?, size: androidx.compose.ui.unit.Dp) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = Modifier.size(size)
    )
}
