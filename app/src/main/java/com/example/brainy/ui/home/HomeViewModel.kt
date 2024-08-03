package com.example.brainy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brainy.ui.score.Score

class HomeViewModel : ViewModel() {
    private val _scores = MutableLiveData<List<Score>>()
    val scores: LiveData<List<Score>> = _scores

    init {
        // Initialize with an empty list
        _scores.value = emptyList()
    }

    fun addScore(newScoreValue: Int, difficulty: String, time: Int) {
        val currentScores = _scores.value.orEmpty().toMutableList()
        currentScores.add(Score(newScoreValue, difficulty, time))
        _scores.value = currentScores.sortedByDescending { it.value }.take(10)
    }
}