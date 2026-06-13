package com.monsoonalert.myanmar

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.monsoonalert.myanmar.data.location.LocationService
import com.monsoonalert.myanmar.data.model.MyanmarLocations
import com.monsoonalert.myanmar.ui.WeatherViewModel
import com.monsoonalert.myanmar.ui.WeatherViewModelFactory
import com.monsoonalert.myanmar.ui.screens.*
import com.monsoonalert.myanmar.ui.theme.MonsoonAlertTheme
import com.monsoonalert.myanmar.worker.WeatherSyncWorker
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    
    private lateinit var viewModel: WeatherViewModel
    private lateinit var locationService: LocationService
    
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            fetchCurrentLocation()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        locationService = LocationService(this)
        
        // Initialize ViewModel
        viewModel = ViewModelProvider(
            this,
            WeatherViewModelFactory(application, locationService)
        )[WeatherViewModel::class.java]
        
        // Schedule background sync
        WeatherSyncWorker.schedulePeriodicSync(this)
        
        // Request location permission and fetch location
        if (!locationService.hasLocationPermission) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            fetchCurrentLocation()
        }
        
        setContent {
            MonsoonAlertTheme {
                MonsoonAlertApp(viewModel = viewModel)
            }
        }
    }
    
    private fun fetchCurrentLocation() {
        lifecycleScope.launch {
            val location = locationService.getCurrentLocation()
            location?.let { loc ->
                // Find nearest Myanmar location and select it
                val nearest = locationService.findNearestLocation(
                    loc.latitude, loc.longitude,
                    MyanmarLocations.getAllLocations()
                )
                viewModel.selectLocation(nearest.id)
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MonsoonAlertApp(viewModel: WeatherViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Location permission handling
    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    
    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        }
    }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "MonsoonAlert Myanmar",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                val items = listOf(
                    NavigationItem("dashboard", "Dashboard", Icons.Default.Cloud),
                    NavigationItem("locations", "Locations", Icons.Default.LocationOn),
                    NavigationItem("forecast", "Forecast", Icons.Default.WbSunny),
                    NavigationItem("alerts", "Alerts", Icons.Default.Warning)
                )
                
                items.forEach { item ->
                    val selected = currentRoute == item.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("dashboard") {
                DashboardScreen(viewModel = viewModel)
            }
            composable("locations") {
                LocationsScreen(viewModel = viewModel)
            }
            composable("forecast") {
                ForecastScreen(viewModel = viewModel)
            }
            composable("alerts") {
                AlertsScreen(viewModel = viewModel)
            }
        }
    }
}

data class NavigationItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
