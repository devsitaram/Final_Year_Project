package com.sitaram.foodshare.features.dashboard.setting.ngoProfile.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.features.dashboard.setting.ngoProfile.domain.NgoProfileUseCase
import com.sitaram.foodshare.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NgoProfileViewModel @Inject constructor(private val ngoProfileUseCase: NgoProfileUseCase) : ViewModel() {

    var isRefreshing by mutableStateOf(false)
    private var _ngoProfileState by mutableStateOf(NgoProfileState())
    val ngoProfileState: NgoProfileState get() = _ngoProfileState

    private var _numberOfDataState by mutableStateOf(NumberOfDataState())
    val numberOfDataState: NumberOfDataState get() = _numberOfDataState


    // get ngo profile
    fun getNgoProfile() {
        _ngoProfileState = NgoProfileState(isLoading = true)
        ngoProfileUseCase.invoke().onEach { result ->
            _ngoProfileState = when (result) {
                is Resource.Loading -> NgoProfileState(isLoading = true)
                is Resource.Success -> NgoProfileState(data = result.data)
                is Resource.Error -> NgoProfileState(error = result.message)
            }
        }.launchIn(viewModelScope)
    }

    // get ngo profile
    fun getNumberOfData(role: String?) {
        _numberOfDataState = NumberOfDataState(isLoading = true)
        ngoProfileUseCase.invoke(role).onEach { result ->
            _numberOfDataState = when (result) {
                is Resource.Loading -> NumberOfDataState(isLoading = true)
                is Resource.Success -> NumberOfDataState(data = result.data)
                is Resource.Error -> NumberOfDataState(error = result.message)
            }
        }.launchIn(viewModelScope)
    }

    // pull to refresh
    fun getSwipeToRefresh(role: String?){
        isRefreshing = true
        ngoProfileUseCase.invoke(role).onEach { result ->
            when (result) {
                is Resource.Loading -> isRefreshing = true

                is Resource.Success -> {
                    _numberOfDataState =  _numberOfDataState.copy(data = result.data)
                    isRefreshing = false
                }
                is Resource.Error -> {
                    _numberOfDataState = _numberOfDataState.copy(error = result.message)
                    isRefreshing = false
                }
            }
        }.launchIn(viewModelScope)
    }
}