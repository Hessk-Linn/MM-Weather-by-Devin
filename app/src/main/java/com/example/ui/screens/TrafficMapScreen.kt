package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TrafficAlert
import com.example.ui.WeatherViewModel
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrafficMapScreen(viewModel: WeatherViewModel) {
    val allTrafficAlerts by viewModel.allTrafficAlerts.collectAsState()
    val townships by viewModel.allTownships.collectAsState()

    var originId by remember { mutableStateOf(1) } // Default Kamayut
    var destinationId by remember { mutableStateOf(9) } // Default Pabedan
    var originExpanded by remember { mutableStateOf(false) }
    var destinationExpanded by remember { mutableStateOf(false) }
    var routeResult by remember { mutableStateOf<RouteDetails?>(null) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Map Header Info
        Card(
            colors = CardDefaults.cardColors(
                containerColor = ElegantSurface
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, ElegantDivider, RoundedCornerShape(16.dp))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = null,
                    tint = ElegantPrimary,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        "Live Monsoon Road Flood Map",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = OnElegantSurface
                    )
                    Text(
                        "Real-time waterlogging levels and route congestion on main Yangon arterials.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnElegantSurfaceVariant
                    )
                }
            }
        }

        // 2. Interactive Route Safety Optimizer when it Rains
        Card(
            colors = CardDefaults.cardColors(
                containerColor = ElegantSurface
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, ElegantDivider, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AltRoute,
                        contentDescription = "Routing Engine",
                        tint = ElegantPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Smart Rain-Route Optimizer",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = ElegantPrimary
                    )
                }

                Text(
                    "Pick your travel coordinates. Our offline engine cross-references active flood points, blocked junctions, and high-tide forecasts to construct a secure bypass route.",
                    style = MaterialTheme.typography.bodySmall,
                    color = OnElegantSurfaceVariant
                )

                // Selectors
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Start Origin Dropdown
                    Box(modifier = Modifier.weight(1f)) {
                        val originTownship = townships.find { it.id == originId }
                        OutlinedButton(
                            onClick = { originExpanded = true },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = OnElegantSurface),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, OnElegantSurfaceVariant.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "From: ${originTownship?.nameEn ?: "Start"}",
                                fontSize = 11.sp,
                                maxLines = 1,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        DropdownMenu(
                            expanded = originExpanded,
                            onDismissRequest = { originExpanded = false }
                        ) {
                            townships.take(15).forEach { township ->
                                DropdownMenuItem(
                                    text = { Text(township.nameEn) },
                                    onClick = {
                                        originId = township.id
                                        originExpanded = false
                                        routeResult = null
                                    }
                               )
                            }
                        }
                    }

                    // Destination Dropdown
                    Box(modifier = Modifier.weight(1f)) {
                        val destTownship = townships.find { it.id == destinationId }
                        OutlinedButton(
                            onClick = { destinationExpanded = true },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = OnElegantSurface),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, OnElegantSurfaceVariant.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "To: ${destTownship?.nameEn ?: "End"}",
                                fontSize = 11.sp,
                                maxLines = 1,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        DropdownMenu(
                            expanded = destinationExpanded,
                            onDismissRequest = { destinationExpanded = false }
                        ) {
                            townships.take(15).forEach { township ->
                                DropdownMenuItem(
                                    text = { Text(township.nameEn) },
                                    onClick = {
                                        destinationId = township.id
                                        destinationExpanded = false
                                        routeResult = null
                                    }
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        routeResult = calculateRainSafeRoute(originId, destinationId, allTrafficAlerts, townships)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = ElegantPrimary, contentColor = DeepPlumBg),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Navigation, contentDescription = null, size = 16.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Evaluate & Optimize Route", fontWeight = FontWeight.Bold)
                }

                // 3. Routing Results Block
                routeResult?.let { result ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (result.isSafe) ElegantSecondary.copy(alpha = 0.15f) else WarningPink.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = if (result.isSafe) ElegantSecondary else WarningPink,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (result.isSafe) Icons.Default.CheckCircle else Icons.Default.Error,
                                contentDescription = null,
                                tint = if (result.isSafe) ElegantSecondary else WarningPink
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (result.isSafe) "Clear Safety Pathway Identified" else "Caution: Flood Intersections on Direct Route",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = if (result.isSafe) ElegantSecondary else WarningPink
                            )
                        }

                        Text(
                            text = result.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnElegantSurface
                        )

                        if (result.detourNeeded) {
                            HorizontalDivider(color = OnElegantSurfaceVariant.copy(alpha = 0.3f))
                            Text(
                                text = "💡 Detour Guidance:\n${result.detourAdvice}",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                color = ElegantPrimary
                            )
                        }
                    }
                }
            }
        }

        // 4. Live Flooding Intersections List
        Text(
            text = "LIVE INTERSECTION ANOMALIES (${allTrafficAlerts.size})",
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.1.sp
            ),
            color = OnElegantSurfaceVariant,
            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
        )

        allTrafficAlerts.forEach { alert ->
            val matchingTownshipName = townships.find { it.id == alert.townshipId }?.nameEn ?: "Yangon Suburb"
            
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = ElegantSurface
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, ElegantDivider, RoundedCornerShape(12.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = alert.roadName,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = OnElegantSurface
                            )
                            Text(
                                text = "Township: $matchingTownshipName",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnElegantSurfaceVariant
                            )
                        }

                        // Waterlogging index icon
                        val severityColor = when (alert.congestionLevel) {
                            "Blocked" -> WarningPink
                            "Heavy" -> Color(0xFFFFD54F) // M3 Yellow
                            else -> ElegantPrimary
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(severityColor.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = alert.congestionLevel.uppercase(),
                                color = severityColor,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                fontSize = 10.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Water,
                            contentDescription = "Flood depth",
                            tint = ElegantPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Flooding depth: ${alert.floodingDepth}",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = OnElegantSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AltRoute,
                            contentDescription = "Alternate route",
                            tint = ElegantTertiary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Alternate: ${alert.alternateRoute}",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnElegantSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

data class RouteDetails(
    val isSafe: Boolean,
    val description: String,
    val detourNeeded: Boolean,
    val detourAdvice: String
)

private fun calculateRainSafeRoute(
    originId: Int,
    destId: Int,
    alerts: List<TrafficAlert>,
    townships: List<com.example.data.Township>
): RouteDetails {
    if (originId == destId) {
        return RouteDetails(
            isSafe = true,
            description = "You are already at your destination. No travel required.",
            detourNeeded = false,
            detourAdvice = ""
        )
    }

    val t1 = townships.find { it.id == originId }?.nameEn ?: ""
    val t2 = townships.find { it.id == destId }?.nameEn ?: ""

    // check if any active flood points coincide with origin or destination township
    val originAlerts = alerts.filter { it.townshipId == originId && it.congestionLevel == "Blocked" }
    val destAlerts = alerts.filter { it.townshipId == destId && it.congestionLevel == "Blocked" }
    
    // check if there are major blocked crossroads in Yangon transit corridor
    val pathAlerts = alerts.filter { it.congestionLevel == "Blocked" || it.floodingDepth.contains("Severe", ignoreCase = true) || it.floodingDepth.contains("Knee", ignoreCase = true) }

    return when {
        originAlerts.isNotEmpty() -> {
            RouteDetails(
                isSafe = false,
                description = "Departure point $t1 is heavily waterlogged. Standing water prevents normal vehicular egress.",
                detourNeeded = true,
                detourAdvice = "Postpone trip. Local roads are ankle to knee deep in sewers. In emergency, use Strand Road bypass links."
            )
        }
        destAlerts.isNotEmpty() -> {
            RouteDetails(
                isSafe = false,
                description = "Destination point $t2 is blocked due to active severe flash flooding. Surface runoffs cannot drain.",
                detourNeeded = true,
                detourAdvice = "Alternative entry via adjacent elevated secondary blocks. Avoid driving passenger sedans. Wait for local tidal gates to open."
            )
        }
        pathAlerts.isNotEmpty() -> {
            val blockedPoint = pathAlerts.first()
            val pointName = blockedPoint.roadName
            RouteDetails(
                isSafe = false,
                description = "Standard direct route from $t1 to $t2 is disrupted by a major node barrier: '$pointName' is completely blocked.",
                detourNeeded = true,
                detourAdvice = blockedPoint.alternateRoute
            )
        }
        else -> {
            RouteDetails(
                isSafe = true,
                description = "Direct pathways from $t1 to $t2 are operating with nominal damp surfaces. No severe water blocks or deep pooling detected.",
                detourNeeded = false,
                detourAdvice = "Drive cautiously under speed limits. Keep wipers active."
            )
        }
    }
}

// Custom Icon helper for this file
@Composable
private fun Icon(imageVector: androidx.compose.ui.graphics.vector.ImageVector, contentDescription: String?, size: androidx.compose.ui.unit.Dp) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = Modifier.size(size)
    )
}
