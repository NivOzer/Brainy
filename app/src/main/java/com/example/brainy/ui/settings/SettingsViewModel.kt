package com.example.brainy.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    private val _difficulty = MutableLiveData<Int>().apply {
        value = 0 // Default to Easy
    }
    val difficulty: LiveData<Int> = _difficulty
    fun setDifficulty(newDifficulty: Int) {
        _difficulty.value = newDifficulty
    }



    private val _timeDifficulty = MutableLiveData<Int>().apply {
        value = 0 // Default to 20 sec
    }
    val timeDifficulty: LiveData<Int> = _timeDifficulty

    fun setTimeDifficulty(newTimeDifficulty: Int) {
        _timeDifficulty.value = newTimeDifficulty
    }
}