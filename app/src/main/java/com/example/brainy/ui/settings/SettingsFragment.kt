package com.example.brainy.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatDelegate
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
        setupTimeDifficultySlider()

        binding.nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
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

    private fun setupTimeDifficultySlider() {
        val times = listOf(20, 15, 10, 5)
        binding.TimedifficultySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val time = times[progress]
                sharedViewModel.setTimeDifficulty(time)
                binding.difficultyTimeText.text = "Time Difficulty: $time sec"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        // Set initial time difficulty from SharedViewModel
        sharedViewModel.timeDifficulty.observe(viewLifecycleOwner) { time ->
            val progress = times.indexOf(time)
            binding.TimedifficultySeekBar.progress = progress
            binding.difficultyTimeText.text = "Time Difficulty: $time sec"
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
