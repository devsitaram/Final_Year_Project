package com.sitaram.foodshare.features.forgotpassword.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.features.forgotpassword.domain.ForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val forgotPasswordUseCase: ForgotPasswordUseCase) : ViewModel() {

    private var _emailVerifyState by mutableStateOf(ForgotPasswordState())
    val emailVerifyState: ForgotPasswordState get() = _emailVerifyState

    private var _forgotPasswordState by mutableStateOf(ForgotPasswordState())
    val forgotPasswordState: ForgotPasswordState get() = _forgotPasswordState

    fun getVerifyEmail(email: String){
        forgotPasswordUseCase(email.trim()).onEach { result ->
            _emailVerifyState = when (result) {
                is Resource.Loading -> {
                    ForgotPasswordState(isLoading = true)
                }

                is Resource.Success -> {
                    ForgotPasswordState(data = result.data) //
                }

                is Resource.Error -> {
                    ForgotPasswordState(isError = result.data?.message ?: result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    // White space remove
    fun setForgotPassword(email: String?, password: String?){
        forgotPasswordUseCase(email?.trim(), password?.trim()).onEach { result ->
            _forgotPasswordState = when (result) {
                is Resource.Loading -> {
                    ForgotPasswordState(isLoading = true)
                }

                is Resource.Success -> {
                    ForgotPasswordState(data = result.data)
                }

                is Resource.Error -> {
                    ForgotPasswordState(isError = result.data?.message ?: result.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}