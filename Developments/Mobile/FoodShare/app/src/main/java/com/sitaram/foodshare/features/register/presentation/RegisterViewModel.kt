package com.sitaram.foodshare.features.register.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.features.register.domain.RegisterModelDAO
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.features.register.domain.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    ViewModel() {

    private var _registerState by mutableStateOf(RegisterState())
    val registerState: RegisterState get() = _registerState

    fun registerUser(email: String, username: String, role: String, password: String) {
        _registerState = RegisterState(isLoading = true)
        val registerModelDAO = RegisterModelDAO(email.trim(), username.trim(), password.trim(), role.trim())
        registerUseCase.invoke(registerModelDAO).onEach { result ->
            _registerState = when (result) {
                is Resource.Loading -> {
                    RegisterState(isLoading = true)
                }

                is Resource.Success -> {
                    RegisterState(data = result.data)
                }

                is Resource.Error -> {
                    RegisterState(error = result.data?.message ?: result.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}