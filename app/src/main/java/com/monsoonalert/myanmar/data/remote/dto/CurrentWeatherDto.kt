package com.monsoonalert.myanmar.data.remote.dto

import com.google.gson.annotations.SerializedName

// OpenWeatherMap API Response DTOs

data class CurrentWeatherDto(
    @SerializedName("coord") val coordinates: Coordinates?,
    @SerializedName("weather") val weather: List<WeatherDescription>?,
    @SerializedName("main") val main: MainWeather?,
    @SerializedName("wind") val wind: Wind?,
    @SerializedName("clouds") val clouds: Clouds?,
    @SerializedName("rain") val rain: Rain?,
    @SerializedName("dt") val timestamp: Long?,
    @SerializedName("sys") val sys: Sys?,
    @SerializedName("timezone") val timezone: Int?,
    @SerializedName("name") val cityName: String?,
    @SerializedName("visibility") val visibility: Int?,
    @SerializedName("cod") val code: Int?
)

data class Coordinates(
    @SerializedName("lon") val longitude: Double?,
    @SerializedName("lat") val latitude: Double?
)

data class WeatherDescription(
    @SerializedName("id") val id: Int?,
    @SerializedName("main") val main: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("icon") val icon: String?
)

data class MainWeather(
    @SerializedName("temp") val temperature: Double?,
    @SerializedName("feels_like") val feelsLike: Double?,
    @SerializedName("temp_min") val tempMin: Double?,
    @SerializedName("temp_max") val tempMax: Double?,
    @SerializedName("pressure") val pressure: Int?,
    @SerializedName("humidity") val humidity: Int?,
    @SerializedName("sea_level") val seaLevel: Int?,
    @SerializedName("grnd_level") val groundLevel: Int?
)

data class Wind(
    @SerializedName("speed") val speed: Double?,
    @SerializedName("deg") val degree: Int?,
    @SerializedName("gust") val gust: Double?
)

data class Clouds(
    @SerializedName("all") val cloudiness: Int?
)

data class Rain(
    @SerializedName("1h") val oneHour: Double?,
    @SerializedName("3h") val threeHour: Double?
)

data class Sys(
    @SerializedName("country") val country: String?,
    @SerializedName("sunrise") val sunrise: Long?,
    @SerializedName("sunset") val sunset: Long?
)
