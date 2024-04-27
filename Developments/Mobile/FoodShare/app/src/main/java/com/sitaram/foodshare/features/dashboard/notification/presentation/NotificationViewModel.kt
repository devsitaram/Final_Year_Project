package com.sitaram.foodshare.features.dashboard.notification.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.features.dashboard.localDatabase.domain.LocalDatabaseUseCase
import com.sitaram.foodshare.features.dashboard.notification.domain.NotificationUseCase
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.local.NotificationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationUseCase: NotificationUseCase,
    private val localDatabaseUseCase: LocalDatabaseUseCase,
) : ViewModel() {

    var isRefreshing by mutableStateOf(false)
    private var _notificationState by mutableStateOf(NotificationState())
    val notificationState: NotificationState get() = _notificationState

    private var _notificationLocalState by mutableStateOf(NotificationLocalState())
    val notificationLocalState: NotificationLocalState get() = _notificationLocalState

//    init {
//        getNotification()
//        getViewNotification()
//    }

    fun getNotification() {
        _notificationState = NotificationState(isLoading = true)
        notificationUseCase.invoke().onEach { result ->
            _notificationState = when (result) {
                is Resource.Loading -> {
                    NotificationState(isLoading = true)
                }

                is Resource.Success -> {
                    NotificationState(data = result.data)
                }

                is Resource.Error -> {
                    NotificationState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getViewNotification() {
        _notificationLocalState = NotificationLocalState(isLoading = true)
        localDatabaseUseCase.invoke().onEach { result ->
            _notificationLocalState = when (result) {
                is Resource.Loading -> {
                    NotificationLocalState(isLoading = true)
                }

                is Resource.Success -> {
                    NotificationLocalState(data = result.data)
                }

                is Resource.Error -> {
                    NotificationLocalState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSwipeToRefresh() {
        // Start both coroutines concurrently
        viewModelScope.launch {
            // Start the first coroutine to fetch data from the network

            // Handle the network response
            notificationUseCase.invoke().onEach { networkResponse ->
                when (networkResponse) {
                    is Resource.Loading -> {
                        isRefreshing = true
                    }

                    is Resource.Success -> {
                        _notificationState = _notificationState.copy(data = networkResponse.data)
                        isRefreshing = false
                    }

                    is Resource.Error -> {
                        _notificationState =
                            _notificationState.copy(error = networkResponse.message)
                        isRefreshing = false
                    }
                }

                // Handle the local database response
                localDatabaseUseCase.invoke().onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _notificationLocalState =
                                _notificationLocalState.copy(data = result.data)
                            isRefreshing = false
                        }

                        is Resource.Error -> {
                            _notificationLocalState =
                                _notificationLocalState.copy(error = result.message)
                            isRefreshing = false
                        }

                        else -> {

                        }
                    }
                }
            }
        }
    }

    fun setViewNotification(notification: NotificationEntity) {
        val updatedData = _notificationLocalState.data.orEmpty() + notification
        viewModelScope.launch {
            localDatabaseUseCase.invoke(notification)
            _notificationLocalState = _notificationLocalState.copy(data = updatedData)
        }
    }
}