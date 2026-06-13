package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "townships")
data class Township(
    @PrimaryKey val id: Int,
    val nameEn: String,
    val nameMy: String,
    val district: String,
    val latitude: Double,
    val longitude: Double
)

@Entity(tableName = "weather_current")
data class WeatherData(
    @PrimaryKey val townshipId: Int,
    val temperature: Double,
    val rainLevel: Double, // in mm
    val rainProbability: Int, // 0 to 100
    val windSpeed: Double, // km/h
    val humidity: Int, // %
    val aqi: Int,
    val aqiStatus: String, // Good, Moderate, Unhealthy, Hazardous
    val weatherCondition: String, // Torrential Rain, Thunderstorm, Monsoon Storm, Heavy Rain, Cloudy, Fair
    val lastUpdated: Long
)

@Entity(tableName = "monsoon_alerts")
data class MonsoonAlert(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val townshipId: Int?, // NULL or 0 for general region-wide alerts
    val description: String,
    val severity: String, // Moderate, Severe, Extreme
    val isPushSimulated: Boolean = false,
    val timestamp: Long
)

@Entity(tableName = "traffic_alerts")
data class TrafficAlert(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val townshipId: Int,
    val title: String,
    val roadName: String,
    val congestionLevel: String, // Low, Moderate, Heavy, Blocked
    val floodingDepth: String, // None, Ankle-deep, Knee-deep, Severe Flooding
    val alternateRoute: String,
    val timestamp: Long
)

@Entity(tableName = "historical_rainfall")
data class HistoricalRainfall(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val townshipId: Int, // 0 for general Yangon average, or specific townshipId
    val monthName: String, // May, June, July, August, September, October
    val averageRainfall: Double, // in mm
    val averageTemp: Double // in C
)
