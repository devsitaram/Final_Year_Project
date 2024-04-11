package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.domain.HistoryCompletedDto
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.domain.UpdateFoodHistoryUseCase
import com.sitaram.foodshare.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CompletedFoodHistoryViewModel @Inject constructor(private val updateFoodHistoryUseCase: UpdateFoodHistoryUseCase) :
    ViewModel() {

    private var _historyState by mutableStateOf(HistoryCompletedState())
    val historyState: HistoryCompletedState get() = _historyState

    fun getHistoryCompleted(historyCompletedDto: HistoryCompletedDto?) {
        _historyState = HistoryCompletedState(isLoading = true)
        updateFoodHistoryUseCase.invoke(historyCompletedDto).onEach { result ->
            _historyState = when(result){
                is Resource.Loading -> HistoryCompletedState(isLoading = true)

                is Resource.Success -> {
                    HistoryCompletedState(data = result.data)
                }

                is Resource.Error -> HistoryCompletedState(error = result.message)
            }
        }.launchIn(viewModelScope)
    }

    fun clearMessage() {
        _historyState = _historyState.copy(message = null)
    }
}