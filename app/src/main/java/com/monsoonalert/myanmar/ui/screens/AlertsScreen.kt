package com.monsoonalert.myanmar.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.monsoonalert.myanmar.ui.WeatherViewModel
import com.monsoonalert.myanmar.ui.theme.*

// Monsoon alert types for Myanmar
sealed class MonsoonAlert(
    val title: String,
    val description: String,
    val severity: AlertSeverity,
    val region: String
) {
    class SevereWeather(title: String, desc: String, region: String) : 
        MonsoonAlert(title, desc, AlertSeverity.SEVERE, region)
    class ModerateWeather(title: String, desc: String, region: String) : 
        MonsoonAlert(title, desc, AlertSeverity.MODERATE, region)
    class Advisory(title: String, desc: String, region: String) : 
        MonsoonAlert(title, desc, AlertSeverity.ADVISORY, region)
    class AllClear(title: String, desc: String, region: String) : 
        MonsoonAlert(title, desc, AlertSeverity.NORMAL, region)
}

enum class AlertSeverity {
    SEVERE, MODERATE, ADVISORY, NORMAL
}

@Composable
fun AlertsScreen(viewModel: WeatherViewModel) {
    var selectedSeverity by remember { mutableStateOf<AlertSeverity?>(null) }
    
    // Sample alerts data - in production this would come from API
    val alerts = remember {
        listOf(
            MonsoonAlert.SevereWeather(
                "Heavy Rainfall Warning",
                "Heavy to very heavy rainfall expected over Yangon, Bago and Ayeyarwady regions. Risk of flooding in low-lying areas.",
                "Yangon, Bago, Ayeyarwady"
            ),
            MonsoonAlert.SevereWeather(
                "Strong Wind Alert",
                "Strong winds (40-50 km/h) expected in coastal areas of Rakhine and Tanintharyi regions.",
                "Rakhine, Tanintharyi"
            ),
            MonsoonAlert.ModerateWeather(
                "Thunderstorm Warning",
                "Thunderstorms with lightning expected in Mandalay and Magway regions.",
                "Mandalay, Magway"
            ),
            MonsoonAlert.Advisory(
                "High Temperature Advisory",
                "Temperatures expected to reach 35-38°C in central Myanmar regions. Stay hydrated.",
                "Mandalay, Magway, Sagaing"
            ),
            MonsoonAlert.AllClear(
                "Normal Conditions",
                "Weather conditions are normal in Kachin, Kayah, and Chin states.",
                "Kachin, Kayah, Chin"
            )
        )
    }
    
    val filteredAlerts = if (selectedSeverity != null) {
        alerts.filter { it.severity == selectedSeverity }
    } else {
        alerts
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Weather Alerts",
            style = MaterialTheme.typography.headlineMedium,
            color = OnDarkPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Alert summary cards
        AlertSummaryRow(alerts)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Filter chips
        Text(
            text = "Filter by Severity",
            style = MaterialTheme.typography.labelLarge,
            color = OnDarkTertiary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = selectedSeverity == null,
                onClick = { selectedSeverity = null },
                label = { Text("All") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MonsoonPrimary,
                    selectedLabelColor = Color.White,
                    containerColor = DarkSurfaceVariant,
                    labelColor = OnDarkSecondary
                )
            )
            FilterChip(
                selected = selectedSeverity == AlertSeverity.SEVERE,
                onClick = { selectedSeverity = if (selectedSeverity == AlertSeverity.SEVERE) null else AlertSeverity.SEVERE },
                label = { Text("Severe") },
                leadingIcon = if (selectedSeverity == AlertSeverity.SEVERE) {
                    { Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(18.dp)) }
                } else null,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = AlertSevere,
                    selectedLabelColor = Color.White,
                    containerColor = DarkSurfaceVariant,
                    labelColor = OnDarkSecondary
                )
            )
            FilterChip(
                selected = selectedSeverity == AlertSeverity.MODERATE,
                onClick = { selectedSeverity = if (selectedSeverity == AlertSeverity.MODERATE) null else AlertSeverity.MODERATE },
                label = { Text("Moderate") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = AlertModerate,
                    selectedLabelColor = Color.White,
                    containerColor = DarkSurfaceVariant,
                    labelColor = OnDarkSecondary
                )
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Alerts list
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            filteredAlerts.forEach { alert ->
                AlertCard(alert = alert)
            }
            
            if (filteredAlerts.isEmpty()) {
                EmptyAlertsState()
            }
        }
    }
}

@Composable
private fun AlertSummaryRow(alerts: List<MonsoonAlert>) {
    val severeCount = alerts.count { it.severity == AlertSeverity.SEVERE }
    val moderateCount = alerts.count { it.severity == AlertSeverity.MODERATE }
    val advisoryCount = alerts.count { it.severity == AlertSeverity.ADVISORY }
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SummaryCard(
            count = severeCount,
            label = "Severe",
            color = AlertSevere,
            icon = Icons.Default.Warning,
            modifier = Modifier.weight(1f)
        )
        SummaryCard(
            count = moderateCount,
            label = "Moderate",
            color = AlertModerate,
            icon = Icons.Default.Notifications,
            modifier = Modifier.weight(1f)
        )
        SummaryCard(
            count = advisoryCount,
            label = "Advisory",
            color = AlertAdvisory,
            icon = Icons.Default.Info,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SummaryCard(
    count: Int,
    label: String,
    color: androidx.compose.ui.graphics.Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.15f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = color,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = OnDarkSecondary
            )
        }
    }
}

@Composable
private fun AlertCard(alert: MonsoonAlert) {
    var expanded by remember { mutableStateOf(false) }
    
    val (backgroundColor, iconColor, icon) = when (alert.severity) {
        AlertSeverity.SEVERE -> Triple(AlertSevere.copy(alpha = 0.15f), AlertSevere, Icons.Default.Warning)
        AlertSeverity.MODERATE -> Triple(AlertModerate.copy(alpha = 0.15f), AlertModerate, Icons.Default.Notifications)
        AlertSeverity.ADVISORY -> Triple(AlertAdvisory.copy(alpha = 0.15f), AlertAdvisory, Icons.Default.Info)
        AlertSeverity.NORMAL -> Triple(AlertNormal.copy(alpha = 0.15f), AlertNormal, Icons.Default.CheckCircle)
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = alert.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = iconColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = alert.region,
                        style = MaterialTheme.typography.bodySmall,
                        color = OnDarkTertiary
                    )
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Show less" else "Show more",
                        tint = OnDarkTertiary
                    )
                }
            }
            
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = OutlineDark)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = alert.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnDarkSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = { /* View detailed alert */ },
                            colors = ButtonDefaults.textButtonColors(contentColor = iconColor)
                        ) {
                            Text("View Details")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyAlertsState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = AlertNormal,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No alerts in this category",
                style = MaterialTheme.typography.titleMedium,
                color = OnDarkSecondary
            )
            Text(
                text = "Check other severity levels for more alerts",
                style = MaterialTheme.typography.bodyMedium,
                color = OnDarkTertiary
            )
        }
    }
}
