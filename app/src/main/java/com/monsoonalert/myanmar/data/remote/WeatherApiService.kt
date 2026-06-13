package com.monsoonalert.myanmar.data.remote

import com.monsoonalert.myanmar.data.remote.dto.CurrentWeatherDto
import com.monsoonalert.myanmar.data.remote.dto.ForecastDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Response<CurrentWeatherDto>
    
    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("cnt") count: Int = 40
    ): Response<ForecastDto>
    
    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Response<CurrentWeatherDto>
}
