package com.sitaram.foodshare.features.dashboard.googleMap.domain

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface GoogleMapRepository {
    fun requestLocationUpdates(latitude: Double, longitude: Double): Flow<LatLng?>
}