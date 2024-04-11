package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.volunteerHistory

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.DistributedHistoryUseCase
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.presentation.DistributedHistoryState
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.features.dashboard.localDatabase.domain.LocalDatabaseUseCase
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.HistoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VolunteerHistoryViewModel @Inject constructor(
    private val historyUseCase: DistributedHistoryUseCase,
    private val localDatabase: LocalDatabaseUseCase,
) : ViewModel() {

    var isRefreshing by mutableStateOf(false)
    private var userId: Int? = 0
    private var status: String? = null

    private var _getHistoryState by mutableStateOf(DistributedHistoryState())
    val getHistoryState: DistributedHistoryState get() = _getHistoryState

    fun getPendingFood(userId: Int, status: String) {
        this.userId = userId
        this.status = status
        historyUseCase.invoke(userId, status.trim()).onEach { result ->
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
        historyUseCase.invoke(userId, status).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    isRefreshing = true
                }

                is Resource.Success -> {
                    _getHistoryState = DistributedHistoryState(data = result.data?.history)
                    isRefreshing = false
                }

                is Resource.Error -> {
                    _getHistoryState = DistributedHistoryState(error = result.message)
                    isRefreshing = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveHistoryDetails(history: HistoryEntity) {
        viewModelScope.launch {
            localDatabase.invoke(history)
        }
    }

    fun saveFoodDetails(foods: FoodsEntity) {
        viewModelScope.launch {
            localDatabase.invoke(foods)
        }
    }

    fun getDeleteHistory(historyId: Int, username: String, itemIndex: Int, context: Context) {
        _getHistoryState = _getHistoryState.copy(isLoading = true)
        // Remove the item at the specified index from the data list
        val updatedDataList = _getHistoryState.data?.toMutableList()?.apply {
            itemIndex.let { index ->
                if (index in indices) { removeAt(index) }
            }
        }
        historyUseCase.invoke(historyId, username, context).onEach { result ->
            _getHistoryState = when (result) {
                is Resource.Loading -> {
                    _getHistoryState.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _getHistoryState.copy(
                        data = updatedDataList,
                        message = result.message,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _getHistoryState.copy(error = result.message, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun clearMessage() {
        _getHistoryState = _getHistoryState.copy(message = null)
    }
}