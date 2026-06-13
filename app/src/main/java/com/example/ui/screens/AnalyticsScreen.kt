package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.WeatherViewModel
import com.example.ui.components.RainfallChart

@Composable
fun AnalyticsScreen(viewModel: WeatherViewModel) {
    val selectedTownship by viewModel.selectedTownship.collectAsState()
    val historicalData by viewModel.historicalTrends.collectAsState()
    val generalData by viewModel.generalHistoricaltrends.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Title Banner
        Column {
            Text(
                text = "${selectedTownship?.nameEn ?: "Yangon"} Analytics",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Township historical data trends (May - October Monsoon span)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // 2. Custom Canvas Graph Block
        if (historicalData.isNotEmpty()) {
            RainfallChart(
                historicalData = historicalData,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Initializing local trend analytics...", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // 3. Comparative Analytics Metric Card
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CompareArrows,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Comparative Regional Index",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                val totalTownshipRain = historicalData.sumOf { it.averageRainfall }
                val totalYangonRain = generalData.sumOf { it.averageRainfall }

                Text(
                    text = if (totalTownshipRain > totalYangonRain) {
                        "⚠️ HIGH WATER ACCUMULATION BOUND\nYour selected township '${selectedTownship?.nameEn}' averages ${totalTownshipRain.toInt()}mm cumulative monsoonal load, exceeding the general Yangon average of ${totalYangonRain.toInt()}mm. Silt clay soils will saturate rapidly. Clear supplementary field channels."
                    } else {
                        "✅ STANDARD HYDROLOGIC DISCHARGE\nYour selected township '${selectedTownship?.nameEn}' averages ${totalTownshipRain.toInt()}mm, slightly below the district max levels. Ideal for rapid irrigation draining during crop maturation phases."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (totalTownshipRain > totalYangonRain) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                )
            }
        }

        // 4. Farmer & Commuter Monthly Scheduling Matrix (UTTERLY COMPLETE METRIC)
        Text(
            text = "MONSOON SCHEDULING INTERVENTION MATRIX",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
        )

        val phases = listOf(
            PhaseItem(
                title = "1. SOWING PHASE (May)",
                color = Color(0xFF10B981),
                farmers = "Prepare nursery beds. Administer organic compost. Ensure complete drainage ditches from paddy limits.",
                commuters = "Service car brakes. Swap expired wiper blades. Stock emergency offline map indexes."
            ),
            PhaseItem(
                title = "2. TRANSPLANTING ARRIVAL (June - July)",
                color = Color(0xFF3B82F6),
                farmers = "Transplant rice seedlings to soaked acreage. Ensure standing water doesn't climb above nursery crown heights.",
                commuters = "Expect daily intersection floods. Commute with a 30-min schedule margin. Avoid driving during tidal peaks."
            ),
            PhaseItem(
                title = "3. HIGH MONSOON OUTAGES (Aug - Sept)",
                color = Color(0xFFF59E0B),
                farmers = "Surveil fields for bacterial rot. Protect grains under tarp cache. Refrain from applying chemical foliar spray.",
                commuters = "High threat of grid collapse and blockages. Check alternative routes on road map. Avoid flooded low roads."
            ),
            PhaseItem(
                title = "4. HARVEST PHASE (October)",
                color = Color(0xFFEF4444),
                farmers = "Drain excess field water 2 weeks prior. Dry harvested paddy kernels immediately under clear sun parameters.",
                commuters = "Monsoon winds subside. Clear vision restored on Yangon road nodes. Drive safe."
            )
        )

        phases.forEach { phase ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, phase.color.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = phase.title,
                        color = phase.color,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Farmer column
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Agriculture, contentDescription = null, tint = phase.color, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Agricultural", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(phase.farmers, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 15.sp)
                        }

                        // Commuter column
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Schedule, contentDescription = null, tint = phase.color, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Transit Plan", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(phase.commuters, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 15.sp)
                        }
                    }
                }
            }
        }
    }
}

data class PhaseItem(
    val title: String,
    val color: Color,
    val farmers: String,
    val commuters: String
)

// Helper Custom Icon
@Composable
private fun Icon(imageVector: androidx.compose.ui.graphics.vector.ImageVector, contentDescription: String?, tint: Color, size: androidx.compose.ui.unit.Dp) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = tint,
        modifier = Modifier.size(size)
    )
}
