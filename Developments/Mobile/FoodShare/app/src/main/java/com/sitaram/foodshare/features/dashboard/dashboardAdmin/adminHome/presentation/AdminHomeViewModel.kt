package com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.data.pojo.ReportPojo
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.domain.AdminHomeUseCase
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.domain.RequestModelDAO
import com.sitaram.foodshare.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@Suppress("NAME_SHADOWING")
@HiltViewModel
class AdminHomeViewModel @Inject constructor(private val adminHomeUseCase: AdminHomeUseCase): ViewModel() {

    var isRefreshing by mutableStateOf(false)

    private var _adminHomeState by mutableStateOf(AdminHomeState())
    val adminHomeState: AdminHomeState get() = _adminHomeState

    init {
        getReportDetails()
    }

    private fun getReportDetails() {
        _adminHomeState = AdminHomeState(isLoading = true)
        adminHomeUseCase.invoke().onEach { result ->
            _adminHomeState = when(result){
                is Resource.Loading -> AdminHomeState(isLoading = true)
                is Resource.Success -> AdminHomeState(data = result.data)
                is Resource.Error -> AdminHomeState(error = result.message)
            }
        }.launchIn(viewModelScope)
    }

    fun getVerifyReport(id: Int?, isVerify: Boolean?, index: Int){
        _adminHomeState = _adminHomeState.copy(isLoading = true)
        val result = RequestModelDAO(id = id, updateQuery = isVerify)
        adminHomeUseCase.invoke(result).onEach { result ->
            _adminHomeState = when(result){
                is Resource.Loading ->  {
                    _adminHomeState.copy(isLoading = true)
                }

                is Resource.Success -> {
                    val updatedData = _adminHomeState.data?.data?.toMutableList()?.apply {
                        removeAt(index)
                    }
                    _adminHomeState.copy(data = ReportPojo(data = updatedData), message = result.message, isLoading = false)
                }

                is Resource.Error -> {
                    _adminHomeState.copy(error = result.message, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSwipeToRefresh() {
        isRefreshing = true
        adminHomeUseCase.invoke().onEach { result ->
            when(result){
                is Resource.Loading -> {
                    isRefreshing = true
                }
                is Resource.Success -> {
                    _adminHomeState = _adminHomeState.copy(data = result.data)
                    isRefreshing = false
                }
                is Resource.Error -> {
                    _adminHomeState = _adminHomeState.copy(error = result.message)
                    isRefreshing = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun clearMessage() {
        _adminHomeState = _adminHomeState.copy(message = null)
    }
}