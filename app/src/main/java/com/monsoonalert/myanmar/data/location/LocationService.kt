package com.monsoonalert.myanmar.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationService(private val context: Context) {
    
    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }
    
    private val _locationState = MutableStateFlow<LocationResult>(LocationResult.Loading)
    val locationState: Flow<LocationResult> = _locationState.asStateFlow()
    
    val hasLocationPermission: Boolean
        get() = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    
    val isLocationEnabled: Boolean
        get() {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }
    
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? = suspendCancellableCoroutine { continuation ->
        if (!hasLocationPermission) {
            continuation.resume(null)
            return@suspendCancellableCoroutine
        }
        
        try {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        _locationState.value = LocationResult.Success(location)
                        continuation.resume(location)
                    } else {
                        // Request a fresh location
                        requestNewLocation(continuation)
                    }
                }
                .addOnFailureListener { exception ->
                    _locationState.value = LocationResult.Error(exception.message ?: "Unknown error")
                    continuation.resume(null)
                }
        } catch (e: Exception) {
            _locationState.value = LocationResult.Error(e.message ?: "Location error")
            continuation.resume(null)
        }
        
        continuation.invokeOnCancellation {
            // Cleanup if needed
        }
    }
    
    @SuppressLint("MissingPermission")
    private fun requestNewLocation(continuation: kotlinx.coroutines.CancellableContinuation<Location?>) {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 10000
        ).apply {
            setWaitForAccurateLocation(false)
            setMinUpdateIntervalMillis(5000)
        }.build()
        
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                val location = result.lastLocation
                _locationState.value = if (location != null) {
                    LocationResult.Success(location)
                } else {
                    LocationResult.Error("Could not get location")
                }
                fusedLocationProviderClient.removeLocationUpdates(this)
                if (continuation.isActive) {
                    continuation.resume(location)
                }
            }
            
            override fun onLocationAvailability(availability: LocationAvailability) {
                if (!availability.isLocationAvailable && continuation.isActive) {
                    _locationState.value = LocationResult.Error("Location not available")
                }
            }
        }
        
        try {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: Exception) {
            _locationState.value = LocationResult.Error(e.message ?: "Request failed")
            if (continuation.isActive) {
                continuation.resume(null)
            }
        }
    }
    
    fun findNearestLocation(lat: Double, lon: Double, locations: List<com.monsoonalert.myanmar.data.model.MyanmarLocation>): com.monsoonalert.myanmar.data.model.MyanmarLocation {
        return locations.minByOrNull { location ->
            calculateDistance(lat, lon, location.latitude, location.longitude)
        } ?: locations.first()
    }
    
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lon1, lat2, lon2, results)
        return results[0] // distance in meters
    }
}

sealed class LocationResult {
    data object Loading : LocationResult()
    data class Success(val location: Location) : LocationResult()
    data class Error(val message: String) : LocationResult()
}
