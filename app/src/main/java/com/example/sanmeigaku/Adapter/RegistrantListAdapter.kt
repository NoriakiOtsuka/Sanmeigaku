package com.example.sanmeigaku.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.sanmeigaku.AssessmentActivity
import com.example.sanmeigaku.MainApplication
import com.example.sanmeigaku.R
import com.example.sanmeigaku.Util.RegistrantDialog
import com.example.sanmeigaku.ViewModel.AssessmentViewModel
import com.example.sanmeigaku.ViewModel.RegistrantViewModel
import com.example.sanmeigaku.databinding.RegistrantListRowsBinding

class RegistrantListAdapter(
    private val context: Context,
    private val viewModel: RegistrantViewModel,
    private val dataSet: MutableList<ArrayList<String>>,
    private val fragmentManager: FragmentManager,
    private val updateCallback: (Int, ArrayList<String>) -> Unit
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
        val year = dataSet[position][2].substring(0, 4)
        val month = dataSet[position][2].substring(4, 6)
        val day = dataSet[position][2].substring(6, 8)
        val birthday = "${year}年${month}月${day}日"
        val gender = when (dataSet[position][3]) {
            "1" -> context.getString(R.string.registrant_gender_male_text)
            "2" -> context.getString(R.string.registrant_gender_female_text)
            else -> ""
        }
        holder.binding.registrantNameText.text = name
        holder.binding.registrantKanaText.text = kana
        holder.binding.registrantBirthdayText.text = birthday
        holder.binding.registrantGenderText.text = gender

        holder.binding.registrantSettingButton.setOnClickListener {
            viewModel.setItemPosition(position)
            viewModel.setName(name)
            viewModel.setKana(kana)
            viewModel.setBirthday(dataSet[position][2].toInt())
            viewModel.setGender(dataSet[position][3].toInt())

            val dialog = RegistrantDialog()
            dialog.show(fragmentManager, "UpdateRegistrantTag")
            Log.i(TAG, "onBindViewHolder: tap select button on line $position")
        }

        holder.binding.registrantDivineButton.setOnClickListener {
            val app = context.applicationContext as MainApplication
            val assessmentViewModel = ViewModelProvider(app)[AssessmentViewModel::class.java]
            assessmentViewModel.setName(name)
            assessmentViewModel.setKana(kana)
            assessmentViewModel.setYear(year.toInt())
            assessmentViewModel.setMonth(month.toInt())
            assessmentViewModel.setDay(day.toInt())
            assessmentViewModel.setGender(dataSet[position][3].toInt())

            val intent = Intent(context, AssessmentActivity::class.java)
            context.startActivity(intent)
            Log.i(TAG, "onBindViewHolder: tap divine button on line $position")
        }
    }

    /**
     * Return the size of the dataset
     */
    override fun getItemCount() = dataSet.size
}