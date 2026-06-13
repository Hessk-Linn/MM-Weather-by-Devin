package com.example.ui

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

class WeatherViewModel(
    private val application: Application,
    private val repository: WeatherRepository
) : AndroidViewModel(application) {

    private val CHANNEL_ID = "monsoon_alerts_channel"

    private val _selectedTownshipId = MutableStateFlow(1) // Defaults to Kamayut
    val selectedTownshipId: StateFlow<Int> = _selectedTownshipId.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _simulatedNotification = MutableStateFlow<MonsoonAlert?>(null)
    val simulatedNotification: StateFlow<MonsoonAlert?> = _simulatedNotification.asStateFlow()

    // 1. All Townships reactive flow
    val allTownships: StateFlow<List<Township>> = repository.allTownships
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 2. Filtered Townships for search coverage representation
    val filteredTownships: StateFlow<List<Township>> = combine(
        repository.allTownships,
        _searchQuery
    ) { townships, query ->
        if (query.isBlank()) {
            townships
        } else {
            townships.filter {
                it.nameEn.contains(query, ignoreCase = true) ||
                it.nameMy.contains(query) ||
                it.district.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 3. Selected Township detailed flow
    val selectedTownship: StateFlow<Township?> = combine(
        repository.allTownships,
        _selectedTownshipId
    ) { townships, id ->
        townships.find { it.id == id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // 4. Current detailed weather metrics for commuters / farmers
    val currentWeather: StateFlow<WeatherData?> = _selectedTownshipId
        .flatMapLatest { id -> repository.getWeatherForTownship(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // 5. Historical Trends metrics (May-Oct) for Monthly Rainfall Charting
    val historicalTrends: StateFlow<List<HistoricalRainfall>> = _selectedTownshipId
        .flatMapLatest { id -> repository.getHistoricalRainfallForTownship(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 6. Generic Yangon Average trends for comparison analytics
    val generalHistoricaltrends: StateFlow<List<HistoricalRainfall>> = repository.generalHistoricalRainfall
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 7. Simulated Traffic warnings and blockages for selected township
    val townshipTrafficAlerts: StateFlow<List<TrafficAlert>> = _selectedTownshipId
        .flatMapLatest { id -> repository.getTrafficAlertsForTownship(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 8. General Traffic warnings across all routes
    val allTrafficAlerts: StateFlow<List<TrafficAlert>> = repository.allTrafficAlerts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 9. Severe Monsoon warnings reactive center
    val monsoonAlerts: StateFlow<List<MonsoonAlert>> = repository.allMonsoonAlerts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 10. Reactive stream of all weather data (enforces offline search previews)
    val allWeather: StateFlow<List<WeatherData>> = repository.allWeather
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Enforce notification channel creation
        createNotificationChannel()
        // Ensure database is populated with Yangon townships for offline accessibility
        viewModelScope.launch {
            repository.seedDatabaseIfNeeded()
        }
    }

    fun selectTownship(townshipId: Int) {
        _selectedTownshipId.value = townshipId
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /**
     * Simulation Event: Simulates a sudden extreme sub-monsoon cell arriving in Yangon,
     * triggering heavy rain and traffic washouts in multiple townships, and raising red alerts.
     */
    fun simulateExtremeStorm() {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val targetedTownships = listOf(1, 2, 3, 4, 8, 9, 20, 26) // Kamayut, Bahan, Sanchaung, Hlaing, Kyauktada, Pabedan, North Okkalapa, Hlaingthaya
            
            // 1. Update weather data in Room DB
            for (tid in targetedTownships) {
                val current = repository.getWeatherForTownshipSync(tid)
                val newWeather = WeatherData(
                    townshipId = tid,
                    temperature = 25.4, // drop temp
                    rainLevel = 78.5, // severe localized rain rate (mm)
                    rainProbability = 100, // absolute probability
                    windSpeed = 48.0, // storm winds
                    humidity = 98,
                    aqi = 25, // rainfall washes pollutants
                    aqiStatus = "Good",
                    weatherCondition = "Monsoon Storm",
                    lastUpdated = now
                )
                repository.updateTownshipWeather(newWeather)
            }

            // 2. Insert new severe traffic and route blockage alerts
            val newTrafficPoints = listOf(
                TrafficAlert(
                    townshipId = 3, // Sanchaung
                    title = "Severe Hydrated Blockage near Dagon Center",
                    roadName = "Pyay Road & Bagaya Road junction",
                    congestionLevel = "Blocked",
                    floodingDepth = "Severe Flooding",
                    alternateRoute = "Divert eastward via Bahan or Link Roads. Heavy vehicle transit restricted.",
                    timestamp = now
                ),
                TrafficAlert(
                    townshipId = 8, // Kyauktada
                    title = "Sub-Tidal Seawater Backflow Inundation",
                    roadName = "Merchant Road & Pansodan corner",
                    congestionLevel = "Heavy",
                    floodingDepth = "Knee-deep",
                    alternateRoute = "Avoid Sule Pagoda approach, use Strand Road elevated ramps.",
                    timestamp = now
                )
            )

            for (ta in newTrafficPoints) {
                repository.insertTrafficAlert(ta)
            }

            // 3. Insert and display Critical Red Monsoon Notification Alert
            val alert = MonsoonAlert(
                title = "CRITICAL: Urgent Localized Storm Incursion",
                townshipId = 0, // regional
                description = "Severe weather convective cells have intensified over Western & Downtown Yangon. Rainfall rate exceeding 70mm/hour reported at Kamayut and Sanchaung. Immediate flood safety plans should be activated.",
                severity = "Extreme",
                isPushSimulated = true,
                timestamp = now
            )
            repository.insertMonsoonAlert(alert)

            // Trigger visual in-app banner alert
            _simulatedNotification.value = alert

            // Push actual Android System Notification tray alert
            sendSystemNotification(
                title = "🚨 Monsoon RED ALERT: Extreme Rainfall",
                message = "Torrential Storm active over Yangon. Core junctions (Hledan, Sule, Dagon Center) reported blocked by severe flooding. Postpone unnecessary transit."
            )
        }
    }

    /**
     * Clear weather and traffic simulator
     */
    fun simulateClearSky() {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val allList = repository.allTownships.firstOrNull() ?: emptyList()
            
            // Restore weather to moderate rainy or cloudy state
            for (t in allList) {
                val baseRain = 2.0 + Random.nextDouble(5.0)
                val baseProb = 20 + Random.nextInt(40)
                val baseTemp = 29.0 + Random.nextDouble(3.0)
                val baseAqi = 40 + Random.nextInt(45)
                val current = WeatherData(
                    townshipId = t.id,
                    temperature = String.format("%.1f", baseTemp).toDouble(),
                    rainLevel = String.format("%.1f", baseRain).toDouble(),
                    rainProbability = baseProb,
                    windSpeed = 12.0 + Random.nextDouble(10.0),
                    humidity = 75 + Random.nextInt(15),
                    aqi = baseAqi,
                    aqiStatus = if (baseAqi < 50) "Good" else "Moderate",
                    weatherCondition = if (baseProb > 50) "Cloudy" else "Fair",
                    lastUpdated = now
                )
                repository.updateTownshipWeather(current)
            }

            // Remove recent active storm alerts
            val currentAlerts = repository.allMonsoonAlerts.firstOrNull() ?: emptyList()
            for (alert in currentAlerts) {
                if (alert.isPushSimulated) {
                    repository.deleteMonsoonAlert(alert.id)
                }
            }

            _simulatedNotification.value = null
            
            sendSystemNotification(
                title = "☀️ Weather System Restored",
                message = "Regional Monsoon storm cell has dissipated. Traffic routes in Sanchaung and Downtown are recovering. Drive safe!"
            )
        }
    }

    /**
     * Allows custom reports for Farmers & Commuters (offline reporting module)
     */
    fun insertUserReport(townshipId: Int, condition: String, level: Double, prob: Int, wind: Double, hum: Int) {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val updated = WeatherData(
                townshipId = townshipId,
                temperature = 27.0,
                rainLevel = level,
                rainProbability = prob,
                windSpeed = wind,
                humidity = hum,
                aqi = 40,
                aqiStatus = "Good",
                weatherCondition = condition,
                lastUpdated = now
            )
            repository.updateTownshipWeather(updated)
            
            // Auto add a secondary user traffic warning if raining heavy
            if (level > 25.0) {
                val tName = allTownships.value.find { it.id == townshipId }?.nameEn ?: "Local Township"
                val ta = TrafficAlert(
                    townshipId = townshipId,
                    title = "User Reported: Heavy Rain Flooding",
                    roadName = "Main Township Avenue",
                    congestionLevel = "Heavy",
                    floodingDepth = "Ankle-deep",
                    alternateRoute = "Exercise caution. Slow speeds due to visibility.",
                    timestamp = now
                )
                repository.insertTrafficAlert(ta)
            }
        }
    }

    fun dismissNotificationBanner() {
        _simulatedNotification.value = null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Monsoon Severity Warning Channel"
            val descriptionText = "Channel for real-time monsoon rainfall and severe flash flood warning push notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendSystemNotification(title: String, message: String) {
        try {
            val builder = NotificationCompat.Builder(application, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)

            val notificationManager = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(Random.nextInt(1000), builder.build())
        } catch (e: SecurityException) {
            Log.e("WeatherViewModel", "Failed to send notification due to permissions: ${e.message}")
        } catch (e: Exception) {
            Log.e("WeatherViewModel", "Error sending notification: ${e.message}")
        }
    }
}

class WeatherViewModelFactory(
    private val application: Application,
    private val repository: WeatherRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
