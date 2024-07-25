package com.example.brainy.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.brainy.databinding.FragmentSettingsBinding
import com.example.brainy.ui.shared.SharedViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupDifficultySlider()

        return root
    }

    private fun setupDifficultySlider() {
        val difficulties = listOf("Easy", "Medium", "Hard", "Extreme")

        binding.difficultySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val difficultyText = "Difficulty: ${difficulties[progress]}"
                binding.difficultyText.text = difficultyText
                sharedViewModel.setDifficulty(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Set initial difficulty from SharedViewModel
        sharedViewModel.difficulty.observe(viewLifecycleOwner) { difficulty ->
            binding.difficultySeekBar.progress = difficulty
            binding.difficultyText.text = "Difficulty: ${difficulties[difficulty]}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
