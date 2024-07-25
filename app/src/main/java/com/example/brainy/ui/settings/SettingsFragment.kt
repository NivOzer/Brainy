package com.example.brainy.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.brainy.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupDifficultySlider()
        setupTimeDifficultySlider()

        return root
    }

    private fun setupDifficultySlider() {
        val difficulties = listOf("Easy", "Medium", "Hard", "Extreme")

        binding.difficultySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val difficultyText = "Difficulty: ${difficulties[progress]}"
                binding.difficultyText.text = difficultyText
                settingsViewModel.setDifficulty(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Set initial difficulty
        settingsViewModel.difficulty.observe(viewLifecycleOwner) { difficulty ->
            binding.difficultySeekBar.progress = difficulty
            binding.difficultyText.text = "Difficulty: ${difficulties[difficulty]}"
        }
    }

    private fun setupTimeDifficultySlider() {
        val timeDifficulties = listOf("20 sec", "15 sec", "10 sec", "5 sec")

        binding.TimedifficultySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val timeDifficultyText = "Time Difficulty: ${timeDifficulties[progress]}"
                binding.difficultyTimeText.text = timeDifficultyText
                settingsViewModel.setTimeDifficulty(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Set initial time difficulty
        settingsViewModel.timeDifficulty.observe(viewLifecycleOwner) { timeDifficulty ->
            binding.TimedifficultySeekBar.progress = timeDifficulty
            binding.difficultyTimeText.text = "Time Difficulty: ${timeDifficulties[timeDifficulty]}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}