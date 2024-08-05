package com.example.brainy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brainy.ui.score.Score

import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel : ViewModel() {
    private val _scores = MutableLiveData<List<Score>>()
    val scores: LiveData<List<Score>> = _scores
    private val firestore = FirebaseFirestore.getInstance()

    init {
        // Initialize with an empty list
//        _scores.value = emptyList()
        fetchScores()

    }

    private fun fetchScores() {
        firestore.collection("scores")
            .orderBy("score", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(10)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    println("Listen failed: $e")
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val scoreList = snapshot.documents.mapNotNull { doc ->
                        doc.getLong("score")?.let { score ->
                            Score(score.toInt())
                        }
                    }
                    _scores.value = scoreList
                } else {
                    println("Current data: null")
                }
            }
    }

    fun addScore(newScoreValue: Int, difficulty: String, time: Int) {
        val currentScores = _scores.value.orEmpty().toMutableList()
        currentScores.add(Score(newScoreValue))
        _scores.value = currentScores.sortedByDescending { it.value }.take(10)
    }
}