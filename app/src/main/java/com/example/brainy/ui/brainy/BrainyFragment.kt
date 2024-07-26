package com.example.brainy.ui.brainy

import android.os.Bundle
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

class BrainyFragment : Fragment() {

    private var _binding: FragmentBrainyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: BrainyViewModel
    private lateinit var questionTextView: TextView
    private lateinit var ans1Button: Button
    private lateinit var ans2Button: Button
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val brainyViewModel =
            ViewModelProvider(this).get(BrainyViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        _binding = FragmentBrainyBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        val textView: TextView = binding.textDashboard
//        brainyViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        binding.backHomeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_brainy_to_navigation_home)
        }

//The first time the app runs you cant press anything

        questionTextView = binding.question
        ans1Button = binding.ans1Btn
        ans2Button = binding.ans2Btn
        viewModel = ViewModelProvider(this).get(BrainyViewModel::class.java)
        viewModel.currentQuestion.observe(viewLifecycleOwner, Observer { mathQuestion ->
            questionTextView.text = mathQuestion.question
            val answers = listOf(mathQuestion.correctAnswer, mathQuestion.wrongAnswer).shuffled()
            ans1Button.text = answers[0].toString()
            ans2Button.text = answers[1].toString()
        })
        ans1Button.setOnClickListener { checkAnswer(ans1Button.text.toString().toInt()) }
        ans2Button.setOnClickListener { checkAnswer(ans2Button.text.toString().toInt()) }
        ////////////////Check Difficulty
        viewModel.generateRandomQuestion(Difficulty.HARD)


        // Observe difficulty changes from SharedViewModel
        sharedViewModel.difficulty.observe(viewLifecycleOwner, Observer { difficulty ->
            val difficultyLevel = when (difficulty) {
                0 -> Difficulty.EASY
                1 -> Difficulty.MEDIUM
                2 -> Difficulty.HARD
                3 -> Difficulty.EXTREME
                else -> Difficulty.EASY
            }
            viewModel.generateRandomQuestion(difficultyLevel)
        })



        // Generate the initial question based on initial difficulty
        sharedViewModel.difficulty.value?.let { difficulty ->
            val difficultyLevel = when (difficulty) {
                0 -> Difficulty.EASY
                1 -> Difficulty.MEDIUM
                2 -> Difficulty.HARD
                3 -> Difficulty.EXTREME
                else -> Difficulty.EASY
            }
            viewModel.generateRandomQuestion(difficultyLevel)
        }
        return root
    }





    private fun checkAnswer(selectedAnswer: Int) {
        if (selectedAnswer == viewModel.currentQuestion.value?.correctAnswer) {
            // Correct Answer Logic
        } else {
            // Wrong Answer Logic
        }
        sharedViewModel.difficulty.value?.let { difficulty ->
            val difficultyLevel = when (difficulty) {
                0 -> Difficulty.EASY
                1 -> Difficulty.MEDIUM
                2 -> Difficulty.HARD
                3 -> Difficulty.EXTREME
                else -> Difficulty.EASY
            }
            viewModel.generateRandomQuestion(difficultyLevel)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}