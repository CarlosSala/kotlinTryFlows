package com.example.tryflows.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tryflows.data.SubscribeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val subscribeRepository = SubscribeRepository()

    private val _uiState = MutableStateFlow<MainUIState>(MainUIState.Loading)
    val uiState: StateFlow<MainUIState> = _uiState

    // example
    // the flow is the source of the data
    // the receiver is the consumer of the data (the collect is the consumer of the data)
    // and in the middle can be intermediate data (before to the collect)

    fun example() {

        /*     viewModelScope.launch {
                 subscribeRepository.counter.collect {

                     Log.i("test", it.toString())
                 }
             }*/

        viewModelScope.launch {
            subscribeRepository.counter
                .map { it.toString() }
                .collect {
                    Log.i("test", it)
                }
        }
    }

    fun example2() {
        viewModelScope.launch {
            subscribeRepository.counter
                .map { it.toString() }
                // onEach allow secondary actions without transform data
                .onEach { save(it) }
                .catch { error -> Log.i("TestError", "Error: ${error.message}") }
                .collect {
                    Log.i("test", it)
                }
        }
    }

    fun example3() {

        viewModelScope.launch {
            subscribeRepository.counter
                .catch { _uiState.value = MainUIState.Error(it.message.orEmpty()) }
                .flowOn(Dispatchers.IO) // since here to up
                .collect {
                    _uiState.value = MainUIState.Success(it)
                }
        }
    }

    private fun save(into: String) {

    }

    // stateFlows is who change the UI
    // sharedFlows allows too many subscribed



}