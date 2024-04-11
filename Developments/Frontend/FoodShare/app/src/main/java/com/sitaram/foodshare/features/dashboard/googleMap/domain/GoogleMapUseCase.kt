package com.sitaram.foodshare.features.dashboard.googleMap.domain

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

class GoogleMapUseCase (private val googleMapRepository: GoogleMapRepository) {

    operator fun invoke(latitude: Double, longitude: Double): Flow<LatLng?> = googleMapRepository.requestLocationUpdates(latitude, longitude)

}