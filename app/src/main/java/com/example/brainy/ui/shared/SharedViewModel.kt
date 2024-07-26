package com.example.brainy.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _difficulty = MutableLiveData<Int>()
    val difficulty: LiveData<Int> get() = _difficulty

    init {
        // Set default value to a valid difficulty level
        _difficulty.value = 0
    }

    fun setDifficulty(level: Int) {
        _difficulty.value = level
    }
}
