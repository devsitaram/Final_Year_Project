package com.sitaram.foodshare.features.dashboard.googleMap.presentation

import com.google.android.gms.maps.model.LatLng

sealed interface LocationState {
    object Loading : LocationState
    data class Success(val location: LatLng?) : LocationState
    object RevokedPermissions : LocationState
}

sealed interface PermissionEvent {
    object Granted : PermissionEvent
    object Revoked : PermissionEvent
}