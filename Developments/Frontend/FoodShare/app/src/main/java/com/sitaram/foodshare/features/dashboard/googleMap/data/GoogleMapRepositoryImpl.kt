package com.sitaram.foodshare.features.dashboard.googleMap.data

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.sitaram.foodshare.features.dashboard.googleMap.domain.GoogleMapRepository
import com.sitaram.foodshare.helper.Permission.Companion.hasViewLocationPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/**
 * Repository implementation for managing Google Maps functionality.
 */
class GoogleMapRepositoryImpl @Inject constructor(
    private val context: Context,
    private val locationClient: FusedLocationProviderClient
) : GoogleMapRepository {

    /**
     * Requests location updates based on provided latitude and longitude.
     * @param latitude The latitude coordinate.
     * @param longitude The longitude coordinate.
     * @return A flow emitting the updated location as a LatLng object.
     */
    @SuppressLint("MissingPermission")
    override fun requestLocationUpdates(
        latitude: Double,
        longitude: Double,
    ): Flow<LatLng?> = callbackFlow {

        // Check if the app has location permission
        if (!hasViewLocationPermission(context)) {
            trySend(null)
            return@callbackFlow
        }

        // Define the location request parameters
        val request = LocationRequest.Builder(1L)
            .setIntervalMillis(1L)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        // Define the callback for location updates
        val currentLocation = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (latitude == 0.0 || longitude == 0.0) {
                    locationResult.locations.lastOrNull()?.let {
                        trySend(LatLng(it.latitude, it.longitude))
                    }
                } else {
                    trySend(LatLng(latitude, longitude))
                }
            }
        }

        // Request location updates
        locationClient.requestLocationUpdates(
            request,
            currentLocation,
            Looper.getMainLooper()
        )

        // Remove location updates when the flow is closed
        awaitClose {
            locationClient.removeLocationUpdates(currentLocation)
        }
    }
}