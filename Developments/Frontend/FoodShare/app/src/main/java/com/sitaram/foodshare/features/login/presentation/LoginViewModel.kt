package com.sitaram.foodshare.features.login.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.features.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private var _signInState by mutableStateOf(LogInState())
    val signInState: LogInState get() = _signInState

    fun getLoginUserAuth(email: String, password: String) {
        _signInState = LogInState(isLoading = true)
        loginUseCase.invoke(email.trim(), password.trim()).onEach { result ->
            _signInState = when (result) {
                is Resource.Loading -> {
                    LogInState(isLoading = true)
                }

                is Resource.Success -> {
                    LogInState(data = result.data)
                }

                is Resource.Error -> {
                    LogInState(error = result.data?.message ?: result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setDeviceFcmToken(userId: Int?, fcmToken: String?, userName: String?) {
        loginUseCase.invoke(userId, fcmToken, userName).onEach { result ->
            _signInState = when (result) {
                is Resource.Loading -> {
                    _signInState.copy(message = null)
                }

                is Resource.Success -> {
                    _signInState.copy(message = result.message, isLoading = false)
                }

                is Resource.Error -> {
                    _signInState.copy(error = result.message, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun clearMessage(){
        _signInState = _signInState.copy(message = null)
    }
}