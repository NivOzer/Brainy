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

enum class Difficulty {
    EASY, MEDIUM, HARD, EXTREME
}

class BrainyViewModel : ViewModel() {
    private val _currentQuestion = MutableLiveData<MathQuestion>()
    val currentQuestion: LiveData<MathQuestion> get() = _currentQuestion

    var correctAnswers = 0
        private set

    private val operators = listOf("+", "-", "*", "/")

    fun generateRandomQuestion(difficulty: Difficulty) {
        var num1: Int
        var num2: Int
        var operator: String
        var correctAnswer: Int
        do {
            val numbers = generateNumbers(difficulty)
            num1 = numbers.first
            num2 = numbers.second
            operator = selectOperator(difficulty)
            correctAnswer = calculateAnswer(num1, num2, operator)
        } while (operator == "/" && num1 % num2 != 0) // Ensure the division results in a whole number

        // Generate a wrong answer ensuring it's different from the correct one
        var wrongAnswer: Int
        do {
            wrongAnswer = correctAnswer + Random.nextInt(1, 10) * if (Random.nextBoolean()) 1 else -1
        } while (wrongAnswer == correctAnswer)

        val question = "What is $num1 $operator $num2?"
        _currentQuestion.value = MathQuestion(question, correctAnswer, wrongAnswer)
    }

    private fun generateNumbers(difficulty: Difficulty): Pair<Int, Int> {
        return when (difficulty) {
            Difficulty.EASY -> Pair(Random.nextInt(1, 10), Random.nextInt(1, 10))
            Difficulty.MEDIUM -> Pair(Random.nextInt(10, 50), Random.nextInt(10, 50))
            Difficulty.HARD -> Pair(Random.nextInt(50, 100), Random.nextInt(50, 100))
            Difficulty.EXTREME -> Pair(Random.nextInt(100, 500), Random.nextInt(1, 100))
        }
    }

    private fun selectOperator(difficulty: Difficulty): String {
        return when (difficulty) {
            Difficulty.EASY -> operators[Random.nextInt(0, 2)] // +, -
            Difficulty.MEDIUM -> operators[Random.nextInt(0, 3)] // +, -, *
            Difficulty.HARD, Difficulty.EXTREME -> operators[Random.nextInt(0, 4)] // +, -, *, /
        }
    }

    private fun calculateAnswer(num1: Int, num2: Int, operator: String): Int {
        return when (operator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> num1 / num2
            else -> 0
        }
    }

    fun incrementCorrectAnswers() {
        correctAnswers++
    }

    fun resetCorrectAnswers() {
        correctAnswers = 0
    }
}