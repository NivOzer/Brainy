package com.example.brainy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brainy.ui.score.Score

class ScoreAdapter : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    private var scores: List<Score> = emptyList()

    class ScoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val scoreTextView: TextView = view.findViewById(R.id.score_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.score_item, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        holder.scoreTextView.text = "${score.value}"
    }

    override fun getItemCount() = scores.size

    fun updateScores(newScores: List<Score>) {
        scores = newScores.sortedByDescending { it.value }
        notifyDataSetChanged()
    }
}