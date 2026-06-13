package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM townships ORDER BY nameEn ASC")
    fun getAllTownships(): Flow<List<Township>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTownships(townships: List<Township>)

    @Query("SELECT * FROM weather_current")
    fun getAllWeather(): Flow<List<WeatherData>>

    @Query("SELECT * FROM weather_current WHERE townshipId = :townshipId")
    fun getWeatherForTownship(townshipId: Int): Flow<WeatherData?>

    @Query("SELECT * FROM weather_current WHERE townshipId = :townshipId")
    suspend fun getWeatherForTownshipSync(townshipId: Int): WeatherData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: List<WeatherData>)

    @Update
    suspend fun updateWeather(weather: WeatherData)

    @Query("SELECT * FROM monsoon_alerts ORDER BY timestamp DESC")
    fun getMonsoonAlerts(): Flow<List<MonsoonAlert>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlerts(alerts: List<MonsoonAlert>)

    @Query("DELETE FROM monsoon_alerts WHERE id = :alertId")
    suspend fun deleteAlert(alertId: Int)

    @Query("SELECT * FROM traffic_alerts ORDER BY timestamp DESC")
    fun getAllTrafficAlerts(): Flow<List<TrafficAlert>>

    @Query("SELECT * FROM traffic_alerts WHERE townshipId = :townshipId ORDER BY timestamp DESC")
    fun getTrafficAlertsForTownship(townshipId: Int): Flow<List<TrafficAlert>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrafficAlerts(alerts: List<TrafficAlert>)

    @Query("SELECT * FROM historical_rainfall WHERE townshipId = :townshipId ORDER BY id ASC")
    fun getHistoricalRainfallForTownship(townshipId: Int): Flow<List<HistoricalRainfall>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoricalRainfall(history: List<HistoricalRainfall>)
}
