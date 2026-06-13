package com.example.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class WeatherRepository(private val weatherDao: WeatherDao) {

    val allTownships: Flow<List<Township>> = weatherDao.getAllTownships()
    val allWeather: Flow<List<WeatherData>> = weatherDao.getAllWeather()
    val allMonsoonAlerts: Flow<List<MonsoonAlert>> = weatherDao.getMonsoonAlerts()
    val allTrafficAlerts: Flow<List<TrafficAlert>> = weatherDao.getAllTrafficAlerts()

    fun getWeatherForTownship(townshipId: Int): Flow<WeatherData?> {
        return weatherDao.getWeatherForTownship(townshipId)
    }

    suspend fun getWeatherForTownshipSync(townshipId: Int): WeatherData? {
        return weatherDao.getWeatherForTownshipSync(townshipId)
    }

    val generalHistoricalRainfall: Flow<List<HistoricalRainfall>> = 
        weatherDao.getHistoricalRainfallForTownship(0)

    fun getHistoricalRainfallForTownship(townshipId: Int): Flow<List<HistoricalRainfall>> {
        return weatherDao.getHistoricalRainfallForTownship(townshipId)
    }

    fun getTrafficAlertsForTownship(townshipId: Int): Flow<List<TrafficAlert>> {
        return weatherDao.getTrafficAlertsForTownship(townshipId)
    }

    suspend fun updateTownshipWeather(weatherData: WeatherData) {
        weatherDao.updateWeather(weatherData)
    }

    suspend fun insertMonsoonAlert(alert: MonsoonAlert) {
        weatherDao.insertAlerts(listOf(alert))
    }

    suspend fun deleteMonsoonAlert(id: Int) {
        weatherDao.deleteAlert(id)
    }

    suspend fun insertTrafficAlert(alert: TrafficAlert) {
        weatherDao.insertTrafficAlerts(listOf(alert))
    }

    /**
     * Seeds the local Room database with comprehensive Myanmar/Yangon township weather datasets
     * if the database is empty, ensuring fully functional offline mode.
     */
    suspend fun seedDatabaseIfNeeded() {
        try {
            val existingTownships = weatherDao.getAllTownships().firstOrNull()?.size ?: 0
            if (existingTownships == 0) {
                Log.d("WeatherRepository", "Database is empty. Seeding initial Yangon townships and weather maps data.")
                
                // 1. Seed all 36 Townships
                weatherDao.insertTownships(InitialData.townships)
                
                val now = System.currentTimeMillis()
                
                // 2. Seed initial weather statuses (AQI, Rain levels, Humidities)
                weatherDao.insertWeather(InitialData.generateInitialWeather(now))
                
                // 3. Seed active extreme monsoon events
                weatherDao.insertAlerts(InitialData.generateInitialAlerts(now))
                
                // 4. Seed Live rainfall flood and traffic blockages
                weatherDao.insertTrafficAlerts(InitialData.generateInitialTraffic(now))
                
                // 5. Seed historical monthly rainfall graphs metrics (May-October)
                weatherDao.insertHistoricalRainfall(InitialData.generateHistoricalRainfall())
                
                Log.d("WeatherRepository", "Successfully completed database seeding for offline coverage.")
            } else {
                Log.d("WeatherRepository", "Database already seeded with $existingTownships townships.")
            }
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error seeding database: ${e.message}", e)
        }
    }
}
