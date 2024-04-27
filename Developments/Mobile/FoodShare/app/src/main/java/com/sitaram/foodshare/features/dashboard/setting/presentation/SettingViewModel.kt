package com.sitaram.foodshare.features.dashboard.setting.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.features.dashboard.setting.domain.SettingUseCase
import com.sitaram.foodshare.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val settingUseCase: SettingUseCase): ViewModel() {

    private  var _settingState by mutableStateOf(SettingState())

    val settingState: SettingState get() = _settingState

   fun getLogOut() {
        _settingState = SettingState(isLoading = true)
       settingUseCase.invoke().onEach { result ->
           _settingState = when(result){
               is Resource.Loading -> SettingState(isLoading = true)
               is Resource.Success -> SettingState(data = result.data, message = result.message)
               is Resource.Error -> SettingState(error = result.message)
           }
       }.launchIn(viewModelScope)
   }

    fun clearMessage() {
        _settingState = _settingState.copy(message = null, error = null)
    }

}