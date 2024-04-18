package com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data.Users
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data.UsersPojo
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.local.ProfileEntity
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.domain.UsersUseCase
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.presentation.state.UsersState
import com.sitaram.foodshare.features.dashboard.localDatabase.domain.LocalDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val usersUseCase: UsersUseCase, private val localDatabase: LocalDatabaseUseCase) :
    ViewModel() {

    var isRefreshing by mutableStateOf(false)
    private var _usersState by mutableStateOf(UsersState())
    val usersState: UsersState get() = _usersState

    init {
        getAllTypesOfUsers()
    }

    private fun getAllTypesOfUsers() {
        _usersState = UsersState(isLoading = true)
        usersUseCase.invoke().onEach { result ->
            _usersState = when (result) {
                is Resource.Loading -> {
                    UsersState(isLoading = true)
                }

                is Resource.Success -> {
                    UsersState(data = result.data)
                }

                is Resource.Error -> {
                    UsersState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setAccountVerify(userId: Int?, isVerify: Boolean) {
        viewModelScope.launch {
            _usersState = _usersState.copy(isLoading = true)
            usersUseCase.invoke(userId, isVerify).collect { result ->
                _usersState = when (result) {
                    is Resource.Loading -> {
                        _usersState.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _usersState.data?.users?.forEach { user ->
                            if (user?.id == userId) {
                                user?.isActive = true
                            }
                        }
                        _usersState.copy(data = usersState.data, isLoading = false)
                    }

                    else -> {
                        _usersState.copy(error = result.message, isLoading = false)
                    }
                }
            }
        }
    }

    fun getSwipeToRefresh() {
        isRefreshing = true
        usersUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    isRefreshing = true
                }

                is Resource.Success -> {
                    _usersState = _usersState.copy(data = result.data)
                    isRefreshing = false
                }

                is Resource.Error -> {
                    _usersState = _usersState.copy(error = result.message)
                    isRefreshing = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUpdateState(sortedList: MutableList<Users?>) {
        _usersState = _usersState.copy(data = UsersPojo(users = sortedList))
    }

    fun saveUserProfile(profile: ProfileEntity) {
        viewModelScope.launch {
            localDatabase.invoke(profile)
        }
    }
}