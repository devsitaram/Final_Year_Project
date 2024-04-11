package com.sitaram.foodshare.features.dashboard.userProfileDetails.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.features.dashboard.userProfileDetails.domain.UserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(private val userDetailUseCase: UserDetailUseCase): ViewModel() {

    var isRefreshing by mutableStateOf(false)
    private var userId: Int = 0
    private var _userProfileState by mutableStateOf(UserDetailState())
    val userDetailState: UserDetailState get() = _userProfileState

    fun getUserProfileById(userId: Int) {
        this.userId = userId
        userDetailUseCase.invoke(userId).onEach { result ->
            _userProfileState = when (result) {
                is Resource.Loading -> {
                    UserDetailState(isLoading = true)
                }

                is Resource.Success -> {
                    UserDetailState(data = result.data)
                }

                is Resource.Error -> {
                    UserDetailState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSwipeToRefresh() {
        isRefreshing = true
        userDetailUseCase.invoke(userId).onEach { result ->
            delay(1000)
            repeat(3) {
                when (result) {
                    is Resource.Loading -> {
                        isRefreshing = true
                    }

                    is Resource.Success -> {
                        _userProfileState = UserDetailState(data = result.data)
                    }

                    is Resource.Error -> {
                        _userProfileState = UserDetailState(error = result.message)
                    }
                }
            }
            isRefreshing = false
        }.launchIn(viewModelScope)
    }

}