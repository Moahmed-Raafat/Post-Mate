package com.example.postmate.common.netwrork_and_internet

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class NetworkViewModel (context: Context) : ViewModel() {
    private val monitor = NetworkMonitor(context)

    val isConnected: StateFlow<Boolean> =
        monitor.isConnected.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )
}