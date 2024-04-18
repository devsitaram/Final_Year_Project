package com.sitaram.foodshare.features.dashboard.setting.manageAccount.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.features.dashboard.setting.manageAccount.domain.ManageAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ManageAccountViewModel @Inject constructor(private val manageAccountUseCase: ManageAccountUseCase) : ViewModel() {

    private var _updateAccountState by mutableStateOf(UpdateAccountState())
    val updateAccountState: UpdateAccountState get() = _updateAccountState

    private var _deleteAccountState by mutableStateOf(DeleteAccountState())
    val deleteAccountState: DeleteAccountState get() = _deleteAccountState

    // update password
    fun updatePassword(email: String, newPassword: String) {
        // second way
        manageAccountUseCase.invoke(email.trim(), newPassword.trim()).onEach { result ->
            _updateAccountState = when (result) {
                is Resource.Loading -> {
                    UpdateAccountState(isLoading = true)
                }

                is Resource.Success -> {
                    UpdateAccountState(data = result.data)
                }

                is Resource.Error -> {
                    UpdateAccountState(isError = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setDeleteAccount(email: String) {
        // Clear update password state
        _updateAccountState = UpdateAccountState(isLoading = true)
        manageAccountUseCase(email.trim()).onEach { result ->
            _deleteAccountState = when (result) {
                is Resource.Loading -> {
                    DeleteAccountState(isLoading = true)
                }

                is Resource.Success -> {
                    DeleteAccountState(data = result.data)
                }

                is Resource.Error -> {
                    DeleteAccountState(isError = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}