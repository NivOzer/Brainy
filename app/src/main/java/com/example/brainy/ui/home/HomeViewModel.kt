package com.example.brainy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brainy.ui.score.Score
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel : ViewModel() {
    private val _scores = MutableLiveData<List<Score>>()
    val scores: LiveData<List<Score>> = _scores

    init {
        // Initialize with some dummy data
        _scores.value = listOf(
            Score(100),
            Score(90),
            Score(80),
            Score(110),
            Score(95),
            Score(95),
            Score(95),
            Score(95),
            Score(95),
            Score(95),
            Score(95),
            Score(95),
            Score(95),
            Score(95),
            Score(95),
            Score(70)
        ).sortedByDescending { it.value }
    }

    fun addScore(newScore: Int) {
        val currentScores = _scores.value.orEmpty().toMutableList()


        currentScores.add(Score(newScore))
        _scores.value = currentScores.sortedByDescending { it.value }
    }
}