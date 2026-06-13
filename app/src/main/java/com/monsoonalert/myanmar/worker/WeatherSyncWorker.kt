package com.monsoonalert.myanmar.worker

import android.content.Context
import androidx.work.*
import com.monsoonalert.myanmar.data.model.MyanmarLocations
import com.monsoonalert.myanmar.data.remote.NetworkModule
import com.monsoonalert.myanmar.data.remote.WeatherApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class WeatherSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    private val apiService: WeatherApiService by lazy {
        val okHttpClient = NetworkModule.provideOkHttpClient()
        val retrofit = NetworkModule.provideRetrofit(okHttpClient)
        NetworkModule.provideWeatherApiService(retrofit)
    }
    
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            // Fetch weather for major cities
            val majorCities = MyanmarLocations.getMajorCities()
            val apiKey = BuildConfig.OPENWEATHER_API_KEY
            
            if (apiKey.isEmpty() || apiKey == "YOUR_API_KEY") {
                // Skip sync if no API key configured
                return@withContext Result.success()
            }
            
            var successCount = 0
            var failureCount = 0
            
            for (city in majorCities.take(10)) { // Limit to top 10 cities to avoid rate limits
                try {
                    val response = apiService.getCurrentWeather(
                        latitude = city.latitude,
                        longitude = city.longitude,
                        apiKey = apiKey
                    )
                    
                    if (response.isSuccessful) {
                        successCount++
                    } else {
                        failureCount++
                    }
                } catch (e: Exception) {
                    failureCount++
                }
            }
            
            // Return success if most cities were updated
            if (successCount > failureCount) {
                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }
    
    companion object {
        const val WORK_NAME = "weather_sync_worker"
        
        fun schedulePeriodicSync(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()
            
            val syncWorkRequest = PeriodicWorkRequestBuilder<WeatherSyncWorker>(
                3, TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .addTag(WORK_NAME)
                .build()
            
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                syncWorkRequest
            )
        }
        
        fun cancelSync(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
        }
    }
}
