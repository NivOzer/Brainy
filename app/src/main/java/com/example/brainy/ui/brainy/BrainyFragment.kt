package com.example.brainy.ui.brainy

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.brainy.R
import com.example.brainy.databinding.FragmentBrainyBinding
import com.example.brainy.ui.shared.SharedViewModel
import com.example.brainy.ui.home.HomeViewModel
import com.example.brainy.ui.score.Score

class BrainyFragment : Fragment() {

    private var _binding: FragmentBrainyBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BrainyViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var questionTextView: TextView
    private lateinit var ans1Button: Button
    private lateinit var ans2Button: Button

    private var countdownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 20000 // Default 20 seconds

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(BrainyViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        _binding = FragmentBrainyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initializeUI()
        setupObservers()

        return root
    }

    private fun initializeUI() {
        binding.backHomeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_brainy_to_navigation_home)
        }
        binding.playAgainBtn.setOnClickListener {
            restartGame()
        }
        questionTextView = binding.question
        ans1Button = binding.ans1Btn
        ans2Button = binding.ans2Btn
        ans1Button.setOnClickListener { checkAnswer(ans1Button.text.toString().toInt()) }
        ans2Button.setOnClickListener { checkAnswer(ans2Button.text.toString().toInt()) }
    }

    private fun setupObservers() {
        sharedViewModel.difficulty.observe(viewLifecycleOwner, Observer { difficulty ->
            viewModel.generateRandomQuestion(getDifficultyLevel(difficulty))
        })
        sharedViewModel.timeDifficulty.observe(viewLifecycleOwner, Observer { time ->
            timeLeftInMillis = time * 1000L
        })

        viewModel.currentQuestion.observe(viewLifecycleOwner, Observer { mathQuestion ->
            updateQuestionUI(mathQuestion)
            startTimer()
        })
    }

    private fun startTimer() {
        cancelTimer()
        timeLeftInMillis = sharedViewModel.timeDifficulty.value?.times(1000L) ?: 20000L
        initializeTimer()
        countdownTimer?.start()
    }

    private fun initializeTimer() {
        countdownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                gameOver()
            }
        }
    }

    private fun updateTimerText() {
        val secondsLeft = (timeLeftInMillis / 1000).toInt() + 1
        binding.timerTv.text = "Time: $secondsLeft"
    }

    private fun cancelTimer() {
        countdownTimer?.cancel()
    }

    private fun checkAnswer(selectedAnswer: Int) {
        if (selectedAnswer == viewModel.currentQuestion.value?.correctAnswer) {
            viewModel.incrementCorrectAnswers()
            generateNextQuestion()
        } else {
            gameOver()
        }
    }

    private fun generateNextQuestion() {
        viewModel.generateRandomQuestion(getCurrentDifficulty())
        startTimer()
    }

    private fun getCurrentDifficulty(): Difficulty {
        return getDifficultyLevel(sharedViewModel.difficulty.value)
    }

    private fun getDifficultyLevel(difficulty: Int?): Difficulty {
        return when (difficulty) {
            0 -> Difficulty.EASY
            1 -> Difficulty.MEDIUM
            2 -> Difficulty.HARD
            3 -> Difficulty.EXTREME
            else -> Difficulty.EASY
        }
    }

    private fun updateQuestionUI(mathQuestion: MathQuestion) {
        questionTextView.text = mathQuestion.question
        val answers = listOf(mathQuestion.correctAnswer, mathQuestion.wrongAnswer).shuffled()
        ans1Button.text = answers[0].toString()
        ans2Button.text = answers[1].toString()
    }

    private fun restartGame() {
        binding.game.visibility = View.VISIBLE
        binding.endgameLayout.visibility = View.GONE
        viewModel.resetCorrectAnswers()
        generateNextQuestion()
    }

    private fun gameOver() {
        binding.game.visibility = View.GONE
        binding.endgameLayout.visibility = View.VISIBLE
        cancelTimer()

        val difficulty = getDifficultyString(sharedViewModel.difficulty.value ?: 0)
        val time = sharedViewModel.timeDifficulty.value ?: 20
        val correctAnswers = viewModel.correctAnswers

        val score = calculateScore(difficulty, time, correctAnswers)


        // Update the score TextView
        binding.scoreTv.text = "Your score is: $score"

        homeViewModel.addScore(score, difficulty, time)
    }

    private fun getDifficultyString(difficultyValue: Int): String {
        return when (difficultyValue) {
            0 -> "EASY"
            1 -> "MEDIUM"
            2 -> "HARD"
            3 -> "EXTREME"
            else -> "EASY"
        }
    }

    private fun calculateScore(difficulty: String, time: Int, correctAnswers: Int): Int {
        val difficultyMultiplier = when (difficulty) {
            "EASY" -> 1
            "MEDIUM" -> 2
            "HARD" -> 3
            "EXTREME" -> 4
            else -> 1
        }

        val timeMultiplier = when (time) {
            20 -> 1
            15 -> 2
            10 -> 3
            5 -> 4
            else -> 1
        }

        return correctAnswers * difficultyMultiplier * timeMultiplier
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cancelTimer()
        _binding = null
    }
}