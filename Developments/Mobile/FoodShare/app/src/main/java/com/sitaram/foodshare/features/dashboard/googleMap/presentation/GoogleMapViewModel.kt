package com.sitaram.foodshare.features.dashboard.googleMap.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.features.dashboard.googleMap.domain.GoogleMapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleMapViewModel @Inject constructor(private val googleMapUseCase: GoogleMapUseCase) : ViewModel() {

    private val _locationState: MutableStateFlow<LocationState> = MutableStateFlow(LocationState.Loading)
    val viewState = _locationState.asStateFlow()

    fun getLocationInfo(
        event: PermissionEvent,
        latitude: Double,
        longitude: Double,
    ) {
        when (event) {
            PermissionEvent.Granted -> {
                viewModelScope.launch {
                    googleMapUseCase.invoke(latitude, longitude).collect { location ->
                        _locationState.value = LocationState.Success(location)
                    }
                }
            }

            PermissionEvent.Revoked -> {
                _locationState.value = LocationState.RevokedPermissions
            }
        }
    }
}