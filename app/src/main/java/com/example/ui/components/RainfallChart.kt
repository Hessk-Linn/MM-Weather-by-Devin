package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.HistoricalRainfall

@Composable
fun RainfallChart(
    historicalData: List<HistoricalRainfall>,
    modifier: Modifier = Modifier,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    accentColor: Color = MaterialTheme.colorScheme.tertiary
) {
    if (historicalData.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "No historical data available",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
        }
        return
    }

    val maxRainfall = (historicalData.maxOfOrNull { it.averageRainfall } ?: 600.0).coerceAtLeast(100.0)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Monthly Rainfall Analytics (Precipitation)",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Custom canvas graph
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val paddingLeft = 60f
                val paddingBottom = 60f
                val paddingTop = 20f
                val paddingRight = 20f

                val graphWidth = canvasWidth - paddingLeft - paddingRight
                val graphHeight = canvasHeight - paddingTop - paddingBottom

                // Grid Lines & Y-Axis Labels
                val gridLinesCount = 4
                for (i in 0..gridLinesCount) {
                    val y = paddingTop + graphHeight * (1 - i.toFloat() / gridLinesCount)
                    val value = (maxRainfall * (i.toFloat() / gridLinesCount)).toInt()
                    
                    // Draw grid line
                    drawLine(
                        color = Color.Gray.copy(alpha = 0.2f),
                        start = Offset(paddingLeft, y),
                        end = Offset(canvasWidth - paddingRight, y),
                        strokeWidth = 1.dp.toPx()
                    )

                    // Draw text labels manually on Canvas isn't as easily formatted as text composables,
                    // but we can draw simple anchor markers or use layout bounds
                }

                // Bar computation
                val barCount = historicalData.size
                val barWidth = (graphWidth / barCount) * 0.55f
                val spacing = (graphWidth / barCount) * 0.45f

                historicalData.forEachIndexed { index, item ->
                    val barHeight = (item.averageRainfall / maxRainfall) * graphHeight
                    val x = paddingLeft + (index * (barWidth + spacing)) + spacing / 2
                    val y = paddingTop + graphHeight - barHeight

                    // Determine peak rainfall month to highlight in bright lavender (matches HTML styling)
                    val isMax = item.averageRainfall == historicalData.maxOf { it.averageRainfall }

                    val barFillColor = if (isMax) primaryColor else accentColor.copy(alpha = 0.6f)
                    val barStrokeColor = if (isMax) primaryColor else accentColor.copy(alpha = 0.9f)

                    // Beautiful gradient fill for rainfall bars
                    val gradient = Brush.verticalGradient(
                        colors = listOf(
                            barFillColor,
                            barFillColor.copy(alpha = 0.25f)
                        ),
                        startY = y.toFloat(),
                        endY = paddingTop + graphHeight
                    )

                    drawRoundRect(
                        brush = gradient,
                        topLeft = Offset(x, y.toFloat()),
                        size = Size(barWidth, barHeight.toFloat()),
                        cornerRadius = CornerRadius(8f, 8f)
                    )

                    // Overlay boundary stroke to give professional punch
                    drawRoundRect(
                        color = barStrokeColor,
                        topLeft = Offset(x, y.toFloat()),
                        size = Size(barWidth, barHeight.toFloat()),
                        cornerRadius = CornerRadius(8f, 8f),
                        style = Stroke(width = 1.dp.toPx())
                    )
                }

                // Baseline axis
                drawLine(
                    color = Color.Gray.copy(alpha = 0.5f),
                    start = Offset(paddingLeft, paddingTop + graphHeight),
                    end = Offset(canvasWidth - paddingRight, paddingTop + graphHeight),
                    strokeWidth = 2.dp.toPx()
                )
            }

            // Overlay Y-Axis labels using Row and Alignment to avoid hard drawing text bounding calculations
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 24.dp) // align with paddingBottom
                    .align(Alignment.TopStart),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Text("${maxRainfall.toInt()}mm", color = Color.Gray, fontSize = 9.sp)
                Text("${(maxRainfall * 0.66).toInt()}mm", color = Color.Gray, fontSize = 9.sp)
                Text("${(maxRainfall * 0.33).toInt()}mm", color = Color.Gray, fontSize = 9.sp)
                Text("0mm", color = Color.Gray, fontSize = 9.sp)
            }
        }

        // Monthly labels matching spacing
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 28.dp, top = 2.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            historicalData.forEach { item ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = item.monthName,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 11.sp
                    )
                    Text(
                        text = "${item.averageRainfall.toInt()} mm",
                        style = MaterialTheme.typography.bodySmall,
                        color = primaryColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
