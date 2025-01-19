package com.example.sanmeigaku.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sanmeigaku.databinding.SuuriJukkanScoreRowsBinding

class JukkanScoreAdapter(private val dataSet: Array<IntArray>) :
    RecyclerView.Adapter<JukkanScoreAdapter.ViewHolder>() {
    private val TAG: String = "JukkanScoreAdapter"

    /**
     * Declaring the use of RecyclerView for ViewHolder
     */
    class ViewHolder(val binding: SuuriJukkanScoreRowsBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    /**
     * Create jukkan score view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SuuriJukkanScoreRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.i(TAG, "onCreateViewHolder: create jukkan score view")

        return ViewHolder(binding)
    }

    /**
     * Retrieve the data associated with the position and enter it in the view
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.suuriAgeText.text = position.toString()
        holder.binding.koubokuScore.text = dataSet[position][0].toString()
        holder.binding.otsubokuScore.text = dataSet[position][1].toString()
        holder.binding.heikaScore.text = dataSet[position][2].toString()
        holder.binding.teikaScore.text = dataSet[position][3].toString()
        holder.binding.bodoScore.text = dataSet[position][4].toString()
        holder.binding.kidoScore.text = dataSet[position][5].toString()
        holder.binding.koukinScore.text = dataSet[position][6].toString()
        holder.binding.shinkinScore.text = dataSet[position][7].toString()
        holder.binding.jinsuiScore.text = dataSet[position][8].toString()
        holder.binding.kisuiScore.text = dataSet[position][9].toString()
        holder.binding.totalScore.text = dataSet[position].sum().toString()
    }

    /**
     * Return the size of the dataset
     */
    override fun getItemCount() = dataSet.size

    /**
     * Return the view type of the item at position for the purposes of view recycling
     */
    override fun getItemViewType(position: Int): Int {
        return position
    }
}