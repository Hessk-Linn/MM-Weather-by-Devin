package com.monsoonalert.myanmar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.monsoonalert.myanmar.data.model.MyanmarLocation
import com.monsoonalert.myanmar.ui.WeatherViewModel
import com.monsoonalert.myanmar.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(viewModel: WeatherViewModel) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val locations by viewModel.allLocations.collectAsState()
    val states by viewModel.statesAndRegions.collectAsState()
    val selectedId by viewModel.selectedLocationId.collectAsState()
    var selectedState by remember { mutableStateOf<String?>(null) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search cities and townships...") },
            leadingIcon = { 
                Icon(Icons.Default.Search, contentDescription = null, tint = OnDarkTertiary) 
            },
            trailingIcon = if (searchQuery.isNotEmpty()) {
                {
                    IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear", tint = OnDarkTertiary)
                    }
                }
            } else null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MonsoonPrimary,
                unfocusedBorderColor = OutlineDark,
                focusedContainerColor = DarkSurface,
                unfocusedContainerColor = DarkSurface,
                focusedTextColor = OnDarkPrimary,
                unfocusedTextColor = OnDarkPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // State filter chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                FilterChip(
                    selected = selectedState == null,
                    onClick = { selectedState = null },
                    label = { Text("All") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MonsoonPrimary,
                        selectedLabelColor = Color.White,
                        containerColor = DarkSurfaceVariant,
                        labelColor = OnDarkSecondary
                    )
                )
            }
            items(states) { state ->
                FilterChip(
                    selected = selectedState == state,
                    onClick = { selectedState = if (selectedState == state) null else state },
                    label = { Text(state) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MonsoonPrimary,
                        selectedLabelColor = Color.White,
                        containerColor = DarkSurfaceVariant,
                        labelColor = OnDarkSecondary
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Results count
        val filteredLocations = if (selectedState != null) {
            locations.filter { it.stateRegion == selectedState }
        } else {
            locations
        }
        
        Text(
            text = "${filteredLocations.size} locations found",
            style = MaterialTheme.typography.bodySmall,
            color = OnDarkTertiary
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Locations list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            if (filteredLocations.isEmpty()) {
                item {
                    EmptyLocationsState()
                }
            } else {
                items(filteredLocations) { location ->
                    LocationCard(
                        location = location,
                        isSelected = location.id == selectedId,
                        onClick = {
                            viewModel.selectLocation(location.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun LocationCard(
    location: MyanmarLocation,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MonsoonPrimary.copy(alpha = 0.15f) else DarkSurface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = location.nameEn,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isSelected) MonsoonPrimary else OnDarkPrimary,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                    if (isSelected) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MonsoonPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                
                if (location.nameMy.isNotEmpty()) {
                    Text(
                        text = location.nameMy,
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnDarkTertiary
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = OnDarkTertiary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${location.stateRegion} • ${location.district}",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnDarkTertiary
                    )
                }
            }
            
            if (location.isMajorCity) {
                Surface(
                    color = MonsoonTertiary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "MAJOR",
                        style = MaterialTheme.typography.labelSmall,
                        color = MonsoonTertiary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyLocationsState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.SearchOff,
                contentDescription = null,
                tint = OnDarkTertiary,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No locations found",
                style = MaterialTheme.typography.titleMedium,
                color = OnDarkSecondary
            )
            Text(
                text = "Try a different search term",
                style = MaterialTheme.typography.bodyMedium,
                color = OnDarkTertiary
            )
        }
    }
}
