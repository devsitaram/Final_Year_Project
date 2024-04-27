package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.DistributedHistoryUseCase
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.ReportDTO
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.features.dashboard.localDatabase.domain.LocalDatabaseUseCase
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.HistoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PendingFoodViewModel @Inject constructor(private val distributedHistoryUseCase: DistributedHistoryUseCase, private val localDatabase: LocalDatabaseUseCase): ViewModel() {

    var isRefreshing by mutableStateOf(false)
    private var userId: Int? = 0
    private var status: String? = null

    private var _getHistoryState by mutableStateOf(DistributedHistoryState())
    val getHistoryState: DistributedHistoryState get() = _getHistoryState

    // ReportDetails
    private var _getReportUserStateDetails by mutableStateOf(ReportUserState())
    val getReportUserState: ReportUserState get() = _getReportUserStateDetails

    fun getPendingFood(userId: Int, status: String) {
        this.userId = userId
        this.status = status
        distributedHistoryUseCase.invoke(userId, status.trim()).onEach { result ->
            _getHistoryState = when (result) {
                is Resource.Loading -> {
                    DistributedHistoryState(isLoading = true)
                }

                is Resource.Success -> {
                    DistributedHistoryState(data = result.data?.history)
                }

                is Resource.Error -> {
                    DistributedHistoryState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSwipeToRefresh() {
        isRefreshing = true
        distributedHistoryUseCase.invoke(userId, status).onEach { result ->
            delay(1000L)
            repeat(3) {
                when (result) {
                    is Resource.Loading -> {
                        isRefreshing = true
                    }

                    is Resource.Success -> {
                        _getHistoryState = DistributedHistoryState(data = result.data?.history)
                    }

                    is Resource.Error -> {
                        _getHistoryState =  DistributedHistoryState(error = result.message)
                    }
                }
            }
            isRefreshing = false
        }.launchIn(viewModelScope)
    }

    fun getReportToUser(reportDTO: ReportDTO?) {
        _getReportUserStateDetails = ReportUserState(isLoading = true)
        distributedHistoryUseCase.invoke(reportDTO).onEach { result ->
            _getReportUserStateDetails = when(result){
                is Resource.Loading -> {
                    ReportUserState(isLoading = true)
                }
                is Resource.Success -> {
                    ReportUserState(data = result.data, message = result.data?.message)
                }
                is Resource.Error -> {
                    ReportUserState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveHistoryDetails (history: HistoryEntity) {
        viewModelScope.launch {
            localDatabase.invoke(history)
        }
    }

    fun saveFoodDetails(foods: FoodsEntity) {
        viewModelScope.launch {
            localDatabase.invoke(foods)
        }
    }

    fun clearMessage() {
        _getReportUserStateDetails = ReportUserState(message = null)
    }
}