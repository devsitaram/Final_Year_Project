package com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.data.DonorHistoryPojo
import com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.domain.DonorHistoryUseCase
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.ReportDTO
import com.sitaram.foodshare.features.dashboard.localDatabase.domain.LocalDatabaseUseCase
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.HistoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonorHistoryViewModel @Inject constructor(
    private val donorHistoryUseCase: DonorHistoryUseCase,
    private val localDatabase: LocalDatabaseUseCase,
) :
    ViewModel() {

    var isRefreshing by mutableStateOf(false)
    private var id: Int? = 0
    private var _donorHistoryState by mutableStateOf(DonorHistoryState())
    val donorHistoryState: DonorHistoryState get() = _donorHistoryState

    fun getDonorHistory(id: Int?) {
        this.id = id
        _donorHistoryState = DonorHistoryState(isLoading = true)
        donorHistoryUseCase.invoke(id).onEach { response ->
            _donorHistoryState = when (response) {
                is Resource.Loading -> {
                    DonorHistoryState(isLoading = true)
                }

                is Resource.Success -> {
                    DonorHistoryState(data = response.data)
                }

                is Resource.Error -> {
                    DonorHistoryState(error = response.message)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun getSwipeToRefresh() {
        isRefreshing = true
        donorHistoryUseCase.invoke(id).onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    isRefreshing = true
                }

                is Resource.Success -> {
                    _donorHistoryState = _donorHistoryState.copy(data = response.data)
                    isRefreshing = false
                }

                is Resource.Error -> {
                    _donorHistoryState = _donorHistoryState.copy(error = response.message)
                    isRefreshing = false
                }
            }
        }.launchIn(viewModelScope)
    }

    // local database have save in foodDetails details
    fun saveFoodDetails(foods: FoodsEntity) {
        viewModelScope.launch {
            localDatabase.invoke(foods)
        }
    }

    // History
    fun saveHistoryDetails(history: HistoryEntity) {
        viewModelScope.launch {
            localDatabase.invoke(history)
        }
    }

    fun getDeleteFood(id: Int?, username: String?, itemIndex: Int?) {
        _donorHistoryState = _donorHistoryState.copy(isLoading = true)

        // Remove the item at the specified index from the data list
        val updatedDataList = _donorHistoryState.data?.data?.toMutableList()?.apply {
            itemIndex?.let { index ->
                if (index in indices) {
                    removeAt(index)
                }
            }
        }

        donorHistoryUseCase.invoke(id, username).onEach { result ->
            _donorHistoryState = when (result) {
                is Resource.Loading -> {
                    _donorHistoryState.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _donorHistoryState.copy(
                        data = DonorHistoryPojo(data = updatedDataList),
                        message = result.message,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _donorHistoryState.copy(error = result.message, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getReportToUser(reportDTO: ReportDTO) {
        _donorHistoryState = _donorHistoryState.copy(isLoading = true)
        donorHistoryUseCase.invoke(reportDTO).onEach { result ->
            _donorHistoryState = when (result) {
                is Resource.Loading -> {
                    _donorHistoryState.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _donorHistoryState.copy(message = result.data?.message, isLoading = false)
                }

                is Resource.Error -> {
                    _donorHistoryState.copy(error = result.message, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun clearMessage() {
        _donorHistoryState = _donorHistoryState.copy(message = null)
    }
}