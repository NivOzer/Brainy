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

class BrainyFragment : Fragment() {

    private var _binding: FragmentBrainyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: BrainyViewModel
    private lateinit var questionTextView: TextView
    private lateinit var ans1Button: Button
    private lateinit var ans2Button: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val brainyViewModel =
            ViewModelProvider(this).get(BrainyViewModel::class.java)

        _binding = FragmentBrainyBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        val textView: TextView = binding.textDashboard
//        brainyViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        binding.backHomeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_brainy_to_navigation_home)
        }



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
        viewModel.generateRandomQuestion()
        return root
    }





    private fun checkAnswer(selectedAnswer: Int) {
        if (selectedAnswer == viewModel.currentQuestion.value?.correctAnswer) {
            // Correct Answer Logic
        } else {
            // Wrong Answer Logic
        }
        viewModel.generateRandomQuestion()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}