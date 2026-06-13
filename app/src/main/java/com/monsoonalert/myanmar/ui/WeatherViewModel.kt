package com.monsoonalert.myanmar.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monsoonalert.myanmar.BuildConfig
import com.monsoonalert.myanmar.data.location.LocationService
import com.monsoonalert.myanmar.data.model.MyanmarLocation
import com.monsoonalert.myanmar.data.model.MyanmarLocations
import com.monsoonalert.myanmar.data.remote.NetworkModule
import com.monsoonalert.myanmar.data.remote.WeatherApiService
import com.monsoonalert.myanmar.data.remote.dto.CurrentWeatherDto
import com.monsoonalert.myanmar.data.remote.dto.ForecastDto
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewModel(
    application: Application,
    private val locationService: LocationService
) : AndroidViewModel(application) {
    
    private val apiService: WeatherApiService by lazy {
        val okHttpClient = NetworkModule.provideOkHttpClient()
        val retrofit = NetworkModule.provideRetrofit(okHttpClient)
        NetworkModule.provideWeatherApiService(retrofit)
    }
    
    // State holders
    private val _selectedLocationId = MutableStateFlow(MyanmarLocations.getDefaultLocation().id)
    val selectedLocationId: StateFlow<Int> = _selectedLocationId.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()
    
    private val _forecastState = MutableStateFlow<ForecastUiState>(ForecastUiState.Loading)
    val forecastState: StateFlow<ForecastUiState> = _forecastState.asStateFlow()
    
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()
    
    // Data flows
    val selectedLocation: StateFlow<MyanmarLocation?> = _selectedLocationId
        .map { id -> MyanmarLocations.getLocationById(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    
    val allLocations: StateFlow<List<MyanmarLocation>> = _searchQuery
        .map { query ->
            if (query.isBlank()) {
                MyanmarLocations.getAllLocations()
            } else {
                MyanmarLocations.searchLocations(query)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MyanmarLocations.getAllLocations())
    
    val majorCities: StateFlow<List<MyanmarLocation>> = flowOf(MyanmarLocations.getMajorCities())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    val statesAndRegions: StateFlow<List<String>> = flowOf(MyanmarLocations.getStatesAndRegions())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    init {
        fetchWeatherForCurrentLocation()
    }
    
    fun selectLocation(locationId: Int) {
        _selectedLocationId.value = locationId
        fetchWeatherForCurrentLocation()
        fetchForecast()
    }
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            fetchWeatherForCurrentLocation()
            fetchForecast()
            _isRefreshing.value = false
        }
    }
    
    private fun fetchWeatherForCurrentLocation() {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            
            val location = MyanmarLocations.getLocationById(_selectedLocationId.value)
                ?: MyanmarLocations.getDefaultLocation()
            
            val apiKey = BuildConfig.OPENWEATHER_API_KEY
            
            // If no API key configured, use mock data for demo
            if (apiKey.isEmpty() || apiKey == "YOUR_API_KEY") {
                _uiState.value = WeatherUiState.Success(
                    location = location,
                    weather = createMockWeather(location),
                    lastUpdated = System.currentTimeMillis()
                )
                return@launch
            }
            
            try {
                val response = apiService.getCurrentWeather(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    apiKey = apiKey
                )
                
                if (response.isSuccessful) {
                    response.body()?.let { weatherDto ->
                        _uiState.value = WeatherUiState.Success(
                            location = location,
                            weather = weatherDto,
                            lastUpdated = System.currentTimeMillis()
                        )
                    } ?: run {
                        _uiState.value = WeatherUiState.Error("Empty response")
                    }
                } else {
                    _uiState.value = WeatherUiState.Error(
                        "Failed to fetch weather: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching weather", e)
                _uiState.value = WeatherUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    private fun fetchForecast() {
        viewModelScope.launch {
            _forecastState.value = ForecastUiState.Loading
            
            val location = MyanmarLocations.getLocationById(_selectedLocationId.value)
                ?: MyanmarLocations.getDefaultLocation()
            
            val apiKey = BuildConfig.OPENWEATHER_API_KEY
            
            if (apiKey.isEmpty() || apiKey == "YOUR_API_KEY") {
                _forecastState.value = ForecastUiState.Success(createMockForecast())
                return@launch
            }
            
            try {
                val response = apiService.getForecast(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    apiKey = apiKey
                )
                
                if (response.isSuccessful) {
                    response.body()?.let { forecastDto ->
                        _forecastState.value = ForecastUiState.Success(forecastDto)
                    } ?: run {
                        _forecastState.value = ForecastUiState.Error("Empty forecast")
                    }
                } else {
                    _forecastState.value = ForecastUiState.Error(
                        "Failed to fetch forecast: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching forecast", e)
                _forecastState.value = ForecastUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    fun getLocationsByState(state: String): List<MyanmarLocation> {
        return MyanmarLocations.getLocationsByState(state)
    }
    
    // Helper functions for weather data formatting
    fun formatTemperature(temp: Double?): String {
        return temp?.let { "${it.toInt()}°C" } ?: "--"
    }
    
    fun formatTime(timestamp: Long?): String {
        return timestamp?.let {
            val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
            sdf.format(Date(it * 1000))
        } ?: "--"
    }
    
    fun formatDate(timestamp: Long?): String {
        return timestamp?.let {
            val sdf = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
            sdf.format(Date(it * 1000))
        } ?: "--"
    }
    
    // Mock data for demo without API key
    private fun createMockWeather(location: MyanmarLocation): CurrentWeatherDto {
        return CurrentWeatherDto(
            coordinates = null,
            weather = listOf(
                com.monsoonalert.myanmar.data.remote.dto.WeatherDescription(
                    id = 500,
                    main = "Rain",
                    description = "light rain",
                    icon = "10d"
                )
            ),
            main = com.monsoonalert.myanmar.data.remote.dto.MainWeather(
                temperature = 28.5,
                feelsLike = 32.0,
                tempMin = 26.0,
                tempMax = 31.0,
                pressure = 1008,
                humidity = 85,
                seaLevel = null,
                groundLevel = null
            ),
            wind = com.monsoonalert.myanmar.data.remote.dto.Wind(
                speed = 12.5,
                degree = 180,
                gust = null
            ),
            clouds = com.monsoonalert.myanmar.data.remote.dto.Clouds(cloudiness = 75),
            rain = null,
            timestamp = System.currentTimeMillis() / 1000,
            sys = com.monsoonalert.myanmar.data.remote.dto.Sys(
                country = "MM",
                sunrise = System.currentTimeMillis() / 1000 - 21600,
                sunset = System.currentTimeMillis() / 1000 + 21600
            ),
            timezone = 23400,
            cityName = location.nameEn,
            visibility = 8000,
            code = 200
        )
    }
    
    private fun createMockForecast(): ForecastDto {
        val now = System.currentTimeMillis() / 1000
        val mockItems = List(8) { index ->
            com.monsoonalert.myanmar.data.remote.dto.ForecastItem(
                timestamp = now + (index * 10800),
                main = com.monsoonalert.myanmar.data.remote.dto.MainWeather(
                    temperature = 28.0 + (index % 3),
                    feelsLike = 31.0,
                    tempMin = 26.0,
                    tempMax = 30.0,
                    pressure = 1008,
                    humidity = 80 + (index * 2),
                    seaLevel = null,
                    groundLevel = null
                ),
                weather = listOf(
                    com.monsoonalert.myanmar.data.remote.dto.WeatherDescription(
                        id = 500 + (index % 3),
                        main = if (index % 3 == 0) "Rain" else "Clouds",
                        description = if (index % 3 == 0) "light rain" else "scattered clouds",
                        icon = if (index % 3 == 0) "10d" else "03d"
                    )
                ),
                clouds = com.monsoonalert.myanmar.data.remote.dto.Clouds(cloudiness = 60 + (index * 5)),
                wind = com.monsoonalert.myanmar.data.remote.dto.Wind(
                    speed = 10.0 + (index % 5),
                    degree = 180,
                    gust = null
                ),
                visibility = 10000,
                precipitationProbability = 0.3 + (index * 0.05),
                rain = null,
                dateTimeText = null
            )
        }
        
        return ForecastDto(
            code = "200",
            message = 0,
            count = 8,
            forecastList = mockItems,
            city = com.monsoonalert.myanmar.data.remote.dto.City(
                id = 1,
                name = "Yangon",
                coordinates = null,
                country = "MM",
                population = 5500000,
                timezone = 23400,
                sunrise = now - 21600,
                sunset = now + 21600
            )
        )
    }
}

sealed class WeatherUiState {
    data object Loading : WeatherUiState()
    data class Success(
        val location: MyanmarLocation,
        val weather: CurrentWeatherDto,
        val lastUpdated: Long
    ) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

sealed class ForecastUiState {
    data object Loading : ForecastUiState()
    data class Success(val forecast: ForecastDto) : ForecastUiState()
    data class Error(val message: String) : ForecastUiState()
}

class WeatherViewModelFactory(
    private val application: Application,
    private val locationService: LocationService
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(application, locationService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
