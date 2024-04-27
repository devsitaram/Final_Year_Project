package com.sitaram.foodshare.helper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState

// This is the permission class
class Permission {

    companion object {
        /**
         * Centers the camera on the specified location with animation.
         * @param location The location to center the camera on.
         */
        suspend fun CameraPositionState.centerOnLocation(location: LatLng) = animate(
            update = CameraUpdateFactory.newLatLngZoom(location, 15f),
            durationMs = 1000
        )

        /**
         * Checks if the app has permission to access the device's location.
         * @param context The context.
         * @return True if the app has permission to access the device's location, false otherwise.
         */
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