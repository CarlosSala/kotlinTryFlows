package com.example.tryflows.ui

sealed class MainUIState {

    data class Success(val numbSubscribers: Int) : MainUIState()
    data object  Loading : MainUIState()
    data class Error(val message: String) : MainUIState()
}