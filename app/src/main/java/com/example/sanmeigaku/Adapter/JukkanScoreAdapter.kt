package com.example.sanmeigaku.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sanmeigaku.databinding.SuuriJukkanScoreRowsBinding

class JukkanScoreAdapter(private val dataSet: Array<IntArray>) :
    RecyclerView.Adapter<JukkanScoreAdapter.ViewHolder>() {
    private val TAG: String = "JukkanScoreAdapter"
    private lateinit var binding: SuuriJukkanScoreRowsBinding

    /**
     * Declaring the use of RecyclerView for ViewHolder
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    /**
     * Create jukkan score view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = SuuriJukkanScoreRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.i(TAG, "onCreateViewHolder: create jukkan score view")

        return ViewHolder(binding.root)
    }

    /**
     * Retrieve the data associated with the position and enter it in the view
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.suuriAgeText.text = position.toString()
        binding.koubokuScore.text = dataSet[position][0].toString()
        binding.otsubokuScore.text = dataSet[position][1].toString()
        binding.heikaScore.text = dataSet[position][2].toString()
        binding.teikaScore.text = dataSet[position][3].toString()
        binding.bodoScore.text = dataSet[position][4].toString()
        binding.kidoScore.text = dataSet[position][5].toString()
        binding.koukinScore.text = dataSet[position][6].toString()
        binding.shinkinScore.text = dataSet[position][7].toString()
        binding.jinsuiScore.text = dataSet[position][8].toString()
        binding.kisuiScore.text = dataSet[position][9].toString()
        binding.totalScore.text = dataSet[position].sum().toString()
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