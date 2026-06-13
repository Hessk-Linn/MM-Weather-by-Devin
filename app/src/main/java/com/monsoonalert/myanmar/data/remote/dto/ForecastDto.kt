package com.monsoonalert.myanmar.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForecastDto(
    @SerializedName("cod") val code: String?,
    @SerializedName("message") val message: Int?,
    @SerializedName("cnt") val count: Int?,
    @SerializedName("list") val forecastList: List<ForecastItem>?,
    @SerializedName("city") val city: City?
)

data class ForecastItem(
    @SerializedName("dt") val timestamp: Long?,
    @SerializedName("main") val main: MainWeather?,
    @SerializedName("weather") val weather: List<WeatherDescription>?,
    @SerializedName("clouds") val clouds: Clouds?,
    @SerializedName("wind") val wind: Wind?,
    @SerializedName("visibility") val visibility: Int?,
    @SerializedName("pop") val precipitationProbability: Double?,
    @SerializedName("rain") val rain: Rain?,
    @SerializedName("dt_txt") val dateTimeText: String?
)

data class City(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("coord") val coordinates: Coordinates?,
    @SerializedName("country") val country: String?,
    @SerializedName("population") val population: Int?,
    @SerializedName("timezone") val timezone: Int?,
    @SerializedName("sunrise") val sunrise: Long?,
    @SerializedName("sunset") val sunset: Long?
)
