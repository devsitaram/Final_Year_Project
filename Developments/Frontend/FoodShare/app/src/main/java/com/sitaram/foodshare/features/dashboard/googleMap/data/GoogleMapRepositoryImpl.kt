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

class GoogleMapRepositoryImpl @Inject constructor(
    private val context: Context,
    private val locationClient: FusedLocationProviderClient
) : GoogleMapRepository {
    @SuppressLint("MissingPermission")
    override fun requestLocationUpdates(
        latitude: Double,
        longitude: Double,
    ): Flow<LatLng?> = callbackFlow {

        // compose
        if (!hasViewLocationPermission(context)) {
            trySend(null)
            return@callbackFlow
        }

        val request = LocationRequest.Builder(1L)
            .setIntervalMillis(1L)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

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

        locationClient.requestLocationUpdates(
            request,
            currentLocation,
            Looper.getMainLooper()
        )

        awaitClose {
            locationClient.removeLocationUpdates(currentLocation)
        }
    }
}