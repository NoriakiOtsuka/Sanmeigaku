package com.example.sanmeigaku.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sanmeigaku.AssessmentActivity
import com.example.sanmeigaku.Enum.GouSan
import com.example.sanmeigaku.Enum.KanShi
import com.example.sanmeigaku.Enum.MainStar
import com.example.sanmeigaku.Enum.SecondStar
import com.example.sanmeigaku.MeishikiFragment
import com.example.sanmeigaku.Util.Isouhou
import com.example.sanmeigaku.Util.Utility
import com.example.sanmeigaku.databinding.MeishikiTaiunListRowsBinding

class TaiunListAdapter(private val dataSet: Array<IntArray>) :
    RecyclerView.Adapter<TaiunListAdapter.ViewHolder>() {
    private val TAG: String = "TaiunListAdapter"
    private lateinit var binding: MeishikiTaiunListRowsBinding
    private val activity: AssessmentActivity.Companion = AssessmentActivity
    private val fragment: MeishikiFragment.Companion = MeishikiFragment

    /** Variables of kan-shi number received from the assessment activity */
    private val mDayKanNo: Int = activity.mDayKanNo
    private val mYearShiNo: Int = activity.mYearShiNo
    private val mMonthShiNo: Int = activity.mMonthShiNo
    private val mDayShiNo: Int = activity.mDayShiNo

    /** Variables of taiun cycle array received from the meishiki fragment */
    private val mCycleArray: Array<String> = fragment.mTaiunCycleArray

    /**
     * Declaring the use of RecyclerView for ViewHolder
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    /**
     * Create taiun list view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = MeishikiTaiunListRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.i(TAG, "onCreateViewHolder: create taiun list view")

        return ViewHolder(binding.root)
    }

    /**
     * Retrieve the data associated with the position and enter it in the view
     */
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val util = Utility()
        val isouUtil = Isouhou()
        val taiAge = dataSet[position][0]
        val taiKanNo = dataSet[position][1]
        val taiShiNo = dataSet[position][2]

        binding.taiunCycleText.text = mCycleArray[position]
        binding.taiunAgeText.text = taiAge.toString()
        binding.taiunKanShiText.text =
            KanShi.valueOf("Kan$taiKanNo").value +
                    KanShi.valueOf("Shi$taiShiNo").value
        binding.taiunMainStarText.text =
            MainStar.valueOf("Main" + util.getMainStarNo(mDayKanNo, taiKanNo)).value
        binding.taiunSecontStarText.text =
            SecondStar.valueOf("Second" + util.getSecondStarNo(mDayKanNo, taiShiNo)).value
        binding.taiunDayGouSanText.text =
            GouSan.valueOf("Isou${isouUtil.getKouTenUnIsouNo(mDayShiNo, taiShiNo)}").value
        binding.taiunMonthGouSanText.text =
            GouSan.valueOf("Isou${isouUtil.getKouTenUnIsouNo(mMonthShiNo, taiShiNo)}").value
        binding.taiunYearGouSanText.text =
            GouSan.valueOf("Isou${isouUtil.getKouTenUnIsouNo(mYearShiNo, taiShiNo)}").value

        Log.i(TAG, "onBindViewHolder: ${position + 1} rounds of data have been reflected in the View")
    }

    /**
     * Return the size of the dataset
     */
    override fun getItemCount() = dataSet.size
}