package com.example.brainy.ui.brainy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

data class MathQuestion(
    val question: String,
    val correctAnswer: Int,
    val wrongAnswer: Int
)

class BrainyViewModel : ViewModel() {

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is dashboard Fragment"
//    }
//    val text: LiveData<String> = _text

    private val _currentQuestion = MutableLiveData<MathQuestion>()
    val currentQuestion: LiveData<MathQuestion> get() = _currentQuestion

    fun generateRandomQuestion() {
        val num1 = Random.nextInt(1, 20)
        val num2 = Random.nextInt(1, 20)
        val correctAnswer = num1 + num2
        val wrongAnswer = correctAnswer + Random.nextInt(1, 5)
        val question = "What is $num1 + $num2?"
        _currentQuestion.value = MathQuestion(question, correctAnswer, wrongAnswer)
    }

}