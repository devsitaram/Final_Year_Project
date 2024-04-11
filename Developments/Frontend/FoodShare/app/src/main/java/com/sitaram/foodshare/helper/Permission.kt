package com.sitaram.foodshare.helper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState

class Permission {

    companion object {
        suspend fun CameraPositionState.centerOnLocation(location: LatLng) = animate(
            update = CameraUpdateFactory.newLatLngZoom(location, 15f),
            durationMs = 1000
        )

        // from compose
        fun hasViewLocationPermission(context: Context): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION // DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION // ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}