package com.sitaram.foodshare.features.firebase.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FcmNotificationViewModel @Inject constructor(): ViewModel() {

    var state by mutableStateOf(SentNotificationState())
//    private val api: FcmApi = Retrofit.Builder()
//        .baseUrl("http://10.0.2.2:8080")
//        .addConverterFactory(MoshiConverterFactory.create())
//        .build()
//        .create()
//
//    fun onRemoteTokenChange(newToke: String){
//        state = state.copy(remoteToken = newToke)
//    }
//
//    fun onSumbitRemoteToken(){
//
//    }
}