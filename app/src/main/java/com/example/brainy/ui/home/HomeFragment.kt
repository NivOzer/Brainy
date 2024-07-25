package com.example.brainy.ui.home

import com.example.brainy.ScoreAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainy.R
import com.example.brainy.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var scoreAdapter: ScoreAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnStartGame.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_brainy)
        }

        setupRecyclerView()
        observeScores()

        return root
    }

    private fun setupRecyclerView() {
        scoreAdapter = ScoreAdapter()
        binding.scoresRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = scoreAdapter
        }
    }

    private fun observeScores() {
        homeViewModel.scores.observe(viewLifecycleOwner) { scores ->
            scoreAdapter.updateScores(scores)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}