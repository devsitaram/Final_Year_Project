package com.sitaram.foodshare.features.dashboard.profile.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.features.dashboard.profile.domain.ProfileUseCase
import com.sitaram.foodshare.features.dashboard.profile.domain.ProfileModelDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileUseCase: ProfileUseCase): ViewModel() {

    var isRefreshing: Boolean = false

    private var _profileState by mutableStateOf(ProfileState())
    val profileState: ProfileState get() = _profileState

    init {
        getUserProfileDetails()
    }

    private fun getUserProfileDetails() {
        _profileState = ProfileState(isLoading = true)
        viewModelScope.launch {
            profileUseCase().onEach { result ->
                _profileState = when (result) {
                    is Resource.Loading -> {
                        ProfileState(isLoading = true)
                    }
                    is Resource.Success -> {
                        ProfileState(data = result.data)
                    }
                    is Resource.Error -> {
                        ProfileState(error = result.message)
                    }
                }
            }.collect()
        }
    }

    fun updateProfileDetails(userId: Int?, profileModelDAO: ProfileModelDAO?) {
        _profileState = ProfileState(isProgress = true, data = _profileState.data)
        profileUseCase(userId, profileModelDAO).onEach { result ->
            _profileState = when (result) {
                is Resource.Loading -> {
                    ProfileState(isProgress = true, data = _profileState.data)
                }

                is Resource.Success -> {
                    ProfileState(data = result.data)
                }

                is Resource.Error -> {
                    ProfileState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateProfilePicture(userId: Int, imageFile: File?) {
        _profileState = ProfileState(isProgress = true, data = _profileState.data)
        profileUseCase.invoke(userId, imageFile).onEach { result ->
            _profileState = when (result) {
                is Resource.Loading -> {
                    ProfileState(isProgress = true, data = _profileState.data)
                }

                is Resource.Success -> {
                    ProfileState(data = result.data)
                }

                is Resource.Error -> {
                    ProfileState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSwipeToRefresh() {
        isRefreshing = true
        profileUseCase().onEach { result ->
            delay(1000)
            repeat(3) {
                when (result) {
                    is Resource.Loading -> {
                        isRefreshing = true
                    }

                    is Resource.Success -> {
                        _profileState = ProfileState(data = result.data)
                    }

                    is Resource.Error -> {
                        _profileState = ProfileState(error = result.message)
                    }
                }
            }
            isRefreshing = false
        }.launchIn(viewModelScope)
    }
}