package com.sitaram.foodshare.features.dashboard.dashboardDonor.post.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationModelDAO
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationUseCase
import com.sitaram.foodshare.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DonationViewModel @Inject constructor(private val donationUseCase: DonationUseCase): ViewModel() {

    private var _donationState by mutableStateOf(DonationState())
    val donationState: DonationState get() = _donationState
    fun setDonationFoodDetail(file: File?, donationModel: DonationModelDAO?){
        _donationState = DonationState(isLoading = true)
        donationUseCase.invoke(file, donationModel).onEach { result ->
            _donationState = when (result) {
                is Resource.Loading -> {
                    DonationState(isLoading = true)
                }

                is Resource.Success -> {
                    DonationState(data = result.data)
                }

                is Resource.Error -> {
                    DonationState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}