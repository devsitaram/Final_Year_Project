package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.domain.FoodDonateRatingDto
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.domain.UpdateFoodHistoryUseCase
import com.sitaram.foodshare.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DonationRatingViewModel @Inject constructor(private val updateFoodHistoryUseCase: UpdateFoodHistoryUseCase) :
    ViewModel() {

    private var _historyState by mutableStateOf(DonationRatingState())
    val donationRatingState: DonationRatingState get() = _historyState

    fun getDonationRating(foodDonateRatingDto: FoodDonateRatingDto?) {
        _historyState = DonationRatingState(isLoading = true)
        updateFoodHistoryUseCase.invoke(foodDonateRatingDto).onEach { result ->
            _historyState = when(result){
                is Resource.Loading -> DonationRatingState(isLoading = true)

                is Resource.Success -> {
                    DonationRatingState(data = result.data, message = result.data?.message)
                }

                is Resource.Error -> DonationRatingState(error = result.message)
            }
        }.launchIn(viewModelScope)
    }

    fun clearMessage() {
        _historyState = _historyState.copy(message = null)
    }
}