package com.example.sanmeigaku.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sanmeigaku.R
import com.example.sanmeigaku.Util.RegistrantDialog
import com.example.sanmeigaku.databinding.RegistrantListRowsBinding

class RegistrantListAdapter(
    private val context: Context,
    private val dataSet: Array<Array<String>>,
    private val fragmentManager: FragmentManager
    ) : RecyclerView.Adapter<RegistrantListAdapter.ViewHolder>() {
    private val TAG: String = "RegistrantListAdapter"

    /**
     * Declaring the use of RecyclerView for ViewHolder
     */
    class ViewHolder(val binding: RegistrantListRowsBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    /**
     * Create registrant list view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RegistrantListRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.i(TAG, "onCreateViewHolder: create registrant list view")

        return  ViewHolder(binding)
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
            "1" -> context.getString(R.string.registrant_gender_male_text)
            "2" -> context.getString(R.string.registrant_gender_female_text)
            else -> ""
        }
        holder.binding.registrantNameText.text = name
        holder.binding.registrantKanaText.text = kana
        holder.binding.registrantBirthdayText.text = birthday
        holder.binding.registrantGenderText.text = gender

        holder.binding.registrantSelectButton.setOnClickListener {
            val registrantArray = arrayOf(dataSet[position][0], dataSet[position][1], dataSet[position][2], dataSet[position][3])
            val dialog = RegistrantDialog.newInstance(registrantArray)
            dialog.show(fragmentManager, "")
        }
    }

    /**
     * Return the size of the dataset
     */
    override fun getItemCount() = dataSet.size
}