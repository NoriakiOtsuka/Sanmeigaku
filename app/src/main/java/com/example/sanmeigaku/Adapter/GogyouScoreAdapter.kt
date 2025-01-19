package com.example.sanmeigaku.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sanmeigaku.databinding.SuuriGogyouScoreRowsBinding

class GogyouScoreAdapter(private val dataSet: Array<FloatArray>) :
    RecyclerView.Adapter<GogyouScoreAdapter.ViewHolder>() {
    private val TAG: String = "GogyouScoreAdapter"

    /**
     * Declaring the use of RecyclerView for ViewHolder
     */
    class ViewHolder(val binding: SuuriGogyouScoreRowsBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    /**
     * Create gogyou score view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SuuriGogyouScoreRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.i(TAG, "onCreateViewHolder: create gogyou score view")

        return ViewHolder(binding)
    }

    /**
     * Retrieve the data associated with the position and enter it in the view
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.suuriAgeText.text = position.toString()
        holder.binding.mokuScore.text = dataSet[position][0].toInt().toString()
        holder.binding.kaScore.text = dataSet[position][1].toInt().toString()
        holder.binding.doScore.text = dataSet[position][2].toInt().toString()
        holder.binding.gonScore.text = dataSet[position][3].toInt().toString()
        holder.binding.suiScore.text = dataSet[position][4].toInt().toString()
        holder.binding.differenceScore.text = dataSet[position][5].toInt().toString()
        holder.binding.displacementScore.text = dataSet[position][6].toInt().toString()
        holder.binding.capacityScore.text = dataSet[position][7].toString()
        holder.binding.totalScore.text = dataSet[position][8].toInt().toString()
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