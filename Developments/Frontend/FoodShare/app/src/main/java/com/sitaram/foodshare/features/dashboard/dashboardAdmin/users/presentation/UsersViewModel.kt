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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
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
        val updateUsers = _usersState.data?.users?.toMutableStateList()
        _usersState = UsersState(isLoading = true, UsersPojo(users = updateUsers))
        usersUseCase().onEach { result ->
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
            val updateUsers = _usersState.data?.users?.toMutableList()
            _usersState = UsersState(isLoading = true, data = UsersPojo(users = updateUsers))
            usersUseCase(userId, isVerify).collect { result ->
                _usersState = when (result) {
                    is Resource.Loading -> {
                        UsersState(isLoading = true, data = UsersPojo(users = updateUsers))
                    }

                    is Resource.Success -> {
                        _usersState.data?.users?.forEach { user ->
                            if (user?.id == userId) {
                                user?.isActive = true
                            }
                        }
                        UsersState(data = usersState.data)
                    }

                    else -> {
                        UsersState(error = result.message)
                    }
                }
            }
        }
    }

    fun getSwipeToRefresh() {
        isRefreshing = true
        usersUseCase.invoke().onEach { result ->
            delay(1000)
            repeat(3) {
                when (result) {
                    is Resource.Loading -> {
                        isRefreshing = true
                    }

                    is Resource.Success -> {
                        _usersState = UsersState(data = result.data)
                    }

                    is Resource.Error -> {
                        _usersState = UsersState(error = result.message)
                    }
                }
                isRefreshing = false
            }
        }.launchIn(viewModelScope)
    }

    fun getUpdateState(sortedList: MutableList<Users?>) {
        _usersState = UsersState(data = UsersPojo(users = sortedList))
    }

    fun saveUserProfile(profile: ProfileEntity) {
        viewModelScope.launch {
            localDatabase.invoke(profile)
        }
    }
}