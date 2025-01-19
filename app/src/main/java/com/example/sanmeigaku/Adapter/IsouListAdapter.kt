package com.example.sanmeigaku.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sanmeigaku.AssessmentActivity
import com.example.sanmeigaku.Enum.GouSan
import com.example.sanmeigaku.Enum.KanShi
import com.example.sanmeigaku.Enum.MainStar
import com.example.sanmeigaku.Enum.SecondStar
import com.example.sanmeigaku.Util.Isouhou
import com.example.sanmeigaku.Util.Utility
import com.example.sanmeigaku.databinding.IsouhouIsouListRowsBinding

class IsouListAdapter(private val dataSet: Array<IntArray>) :
    RecyclerView.Adapter<IsouListAdapter.ViewHolder>() {
    private val TAG: String = "IsouListAdapter"
    private val activity: AssessmentActivity.Companion = AssessmentActivity
    private val mUtil: Utility = Utility()
    private val mIsouUtil: Isouhou = Isouhou()

    /** Variables of kan-shi number received from the assessment activity */
    private val mDayKanNo: Int = activity.mDayKanNo
    private val mYearShiNo: Int = activity.mYearShiNo
    private val mMonthShiNo: Int = activity.mMonthShiNo
    private val mDayShiNo: Int = activity.mDayShiNo

    /**
     * Declaring the use of RecyclerView for ViewHolder
     */
    class ViewHolder(val binding: IsouhouIsouListRowsBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    /**
     * Create isou list view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = IsouhouIsouListRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.i(TAG, "onCreateViewHolder: create isou list view")

        return ViewHolder(binding)
    }

    /**
     * Retrieve the data associated with the position and enter it in the view
     */
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val year = dataSet[position][0]
        val taiAge = dataSet[position][1]
        val taiKanNo = dataSet[position][2]
        val taiShiNo = dataSet[position][3]
        val nenKanNo = dataSet[position][4]
        val nenShiNo = dataSet[position][5]

        holder.binding.isouAgeText.text = "${position.toString().padStart(2, '\t')} ${year + position}"

        if (taiAge != -1) {
            val taiKanshi = "${KanShi.valueOf("Kan$taiKanNo").value}${KanShi.valueOf("Shi$taiShiNo").value}"
            val taiMainStar = MainStar.valueOf("Main" + mUtil.getMainStarNo(mDayKanNo, taiKanNo)).value.substring(0, 2)
            val taiSecondStar = SecondStar.valueOf("Second" + mUtil.getSecondStarNo(mDayKanNo, taiShiNo)).value.substring(0, 2)
            val taiDayGouSan = makeTaiGouSan(mDayShiNo, taiShiNo)
            val taiMonthGouSan = makeTaiGouSan(mMonthShiNo, taiShiNo)
            val taiYearGouSan = makeTaiGouSan(mYearShiNo, taiShiNo)
            holder.binding.isouTaiunText.text = "$taiKanshi $taiMainStar $taiSecondStar $taiDayGouSan / $taiMonthGouSan / $taiYearGouSan"
        }

        val nenKanshi = "${KanShi.valueOf("Kan$nenKanNo").value}${KanShi.valueOf("Shi$nenShiNo").value}"
        val nenMainStar = MainStar.valueOf("Main" + mUtil.getMainStarNo(mDayKanNo, nenKanNo)).value.substring(0, 2)
        val nenSecondStar = SecondStar.valueOf("Second" + mUtil.getSecondStarNo(mDayKanNo, nenShiNo)).value.substring(0, 2)
        holder.binding.isouNenunText.text = "$nenKanshi $nenMainStar $nenSecondStar"

        val nenDayGouSan = makeNenGouSan(mDayShiNo, nenShiNo)
        val nenMonthGouSan = makeNenGouSan(mMonthShiNo, nenShiNo)
        val nenYearGouSan = makeNenGouSan(mYearShiNo, nenShiNo)
        holder.binding.isouNenunDayText.text = nenDayGouSan
        holder.binding.isouNenunMonthText.text = nenMonthGouSan
        holder.binding.isouNenunYearText.text = nenYearGouSan
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

    /**
     * Return the result of Gouhou and Sanhou in taiun
     */
    private fun makeTaiGouSan(meiShiNo: Int, kouShiNo: Int): String {
        var result = ""
        val gouSanNo = mIsouUtil.getKouTenUnIsouNo(meiShiNo, kouShiNo)
        if (gouSanNo != 0) {
            val taiGouSanNoList = gouSanNo.toString().chunked(2)
            for (num in taiGouSanNoList) {
                var gouSan = GouSan.valueOf("Isou${num}").value
                if (gouSan.contains("(")) {
                    gouSan = gouSan.dropLastWhile { x -> x != '(' }.dropLast(1)
                }
                if (!result.contains(gouSan))
                    result += "$gouSan "
            }
            result = result.dropLast(1)
        } else {
            result = "  -  "
        }

        return result
    }

    /**
     * Return the result of Gouhou and Sanhou in nenun
     */
    private fun makeNenGouSan(meiShiNo: Int, kouShiNo: Int): String {
        val gouSanNo = mIsouUtil.getKouTenUnIsouNo(meiShiNo, kouShiNo)
        val gouSan = GouSan.valueOf("Isou${gouSanNo}").value

        return gouSan.replace("\n", " ")
    }
}