package com.example.sanmeigaku.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sanmeigaku.R
import com.example.sanmeigaku.databinding.RegistrantListRowsBinding

class RegistrantListAdapter(context: Context, private val dataSet: Array<Array<String>>) :
    RecyclerView.Adapter<RegistrantListAdapter.ViewHolder>() {
    private val TAG: String = "RegistrantListAdapter"
    private lateinit var binding: RegistrantListRowsBinding
    private val mContext: Context = context

    /**
     * Declaring the use of RecyclerView for ViewHolder
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    /**
     * Create registrant list view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RegistrantListRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.i(TAG, "onCreateViewHolder: create registrant list view")

        return  ViewHolder(binding.root)
    }

    /**
     * Retrieve the data associated with the position and enter it in the view
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = dataSet[position][0]
        val kana = dataSet[position][1]
        val birthday =
            "${dataSet[position][2].substring(0, 4)}年" +
                    "${dataSet[position][2].substring(4, 6)}月" +
                    "${dataSet[position][2].substring(6, 8)}日"
        val gender = when (dataSet[position][3]) {
            "1" -> mContext.getString(R.string.registrant_gender_male_text)
            "2" -> mContext.getString(R.string.registrant_gender_female_text)
            else -> ""
        }
        binding.registrantNameText.text = name
        binding.registrantKanaText.text = kana
        binding.registrantBirthdayText.text = birthday
        binding.registrantGenderText.text = gender
    }

    /**
     * Return the size of the dataset
     */
    override fun getItemCount() = dataSet.size
}