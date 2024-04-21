package com.example.sanmeigaku

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sanmeigaku.Enum.IjoKanShi
import com.example.sanmeigaku.Enum.KanShi
import com.example.sanmeigaku.Enum.TenChuSatsu
import com.example.sanmeigaku.Util.Isouhou
import com.example.sanmeigaku.Util.Utility
import com.example.sanmeigaku.databinding.FragmentIsouhouBinding

class IsouhouFragment : Fragment() {
    private val TAG: String = "IsouhouFragment"
    private var _binding: FragmentIsouhouBinding? = null
    private val binding get() = _binding!!
    private val activity: AssessmentActivity.Companion = AssessmentActivity
    private val mUtil: Utility = Utility()
    private val mIsouUtil = Isouhou()
    private lateinit var mContext: Context

    /** Variables of kan-shi number received from the assessment activity */
    private val mDayKanNo: Int = activity.mDayKanNo
    private val mYearKanShiNo: Int = activity.mYearKanShiNo
    private val mMonthKanShiNo: Int = activity.mMonthKanShiNo
    private val mDayKanShiNo: Int = activity.mDayKanShiNo
    private val mYearShiNo: Int = activity.mYearShiNo
    private val mMonthShiNo: Int = activity.mMonthShiNo
    private val mDayShiNo: Int = activity.mDayShiNo

    companion object {
    }

    /**
     * Create isouhou fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        Log.i(TAG, "onCreate: create isouhou fragment")
    }

    /**
     * Create isouhou fragment view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIsouhouBinding.inflate(inflater, container, false)
        Log.i(TAG, "onCreateView: isouhou fragment view create")

        return binding.root
    }

    /**
     * Created isouhou fragment view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: isouhou fragment view created")

        setGouhouList()
        setSanhouList()
        setComprehensiveList()
    }

    /**
     * Destroy isouhou fragment view
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i(TAG, "onDestroyView: isouhou fragment view destroyed")
    }

    /**
     * Set gouhou list
     */
    private fun setGouhouList() {
        binding.gouhouList.sangoukaikyokuText.text = mIsouUtil.getSangouKaikyoku(mContext, mYearShiNo, mMonthShiNo, mDayShiNo)
        binding.gouhouList.hankaiText.text = mIsouUtil.getHankai(mContext, mYearShiNo, mMonthShiNo, mDayShiNo)
        binding.gouhouList.shigouText.text = mIsouUtil.getShigou(mContext, mYearShiNo, mMonthShiNo, mDayShiNo)
        binding.gouhouList.housaniText.text = mIsouUtil.getHousani(mContext, mYearShiNo, mMonthShiNo, mDayShiNo)
    }

    /**
     * Set sanhou list
     */
    private fun setSanhouList() {
        binding.sanhouList.taichuText.text = mIsouUtil.getTaichu(mContext, mYearShiNo, mMonthShiNo, mDayShiNo)
        binding.sanhouList.keiText.text = mIsouUtil.getKei(mContext, mYearShiNo, mMonthShiNo, mDayShiNo)
        binding.sanhouList.haText.text = mIsouUtil.getHa(mYearShiNo, mMonthShiNo, mDayShiNo)
        binding.sanhouList.gaiText.text = mIsouUtil.getGai(mContext, mYearShiNo, mMonthShiNo, mDayShiNo)
    }

    /**
     * Set comprehensive list
     */
    private fun setComprehensiveList() {
        binding.comprehensiveList.tenchusatsuText.text = setTenchusatsuItem()
        binding.comprehensiveList.ijoKanshiText.text = setIjokanshiItem()
        binding.comprehensiveList.shugoshinText.text = setShugoshinItem()
    }

    /**
     * Set shukumei tenchusatsu items
     */
    private fun setTenchusatsuItem(): String {
        val array = mUtil.getTenchusatsuArray(mYearKanShiNo, mMonthKanShiNo, mDayKanShiNo)
        var result = ""
        for ((index, i) in array.withIndex()) {
            if (i)
                result += TenChuSatsu.valueOf("Ten${index + 1}").value + ", "
        }

        return result.dropLast(2)
    }

    /**
     * Set shukumei ijo-kan-shi items
     */
    private fun setIjokanshiItem(): String {
        val dayNo = mUtil.getIjokanshiNo(mDayKanShiNo)
        val monthNo = mUtil.getIjokanshiNo(mMonthKanShiNo)
        val yearNo = mUtil.getIjokanshiNo(mYearKanShiNo)
        var result = ""
        var rate = 0

        if (dayNo != 0) {
            result += IjoKanShi.valueOf("Ijo$dayNo").value + "(日), "
            rate += 50
        }
        if (monthNo != 0) {
            result += IjoKanShi.valueOf("Ijo$monthNo").value + "(月), "
            rate += 25
        }
        if (yearNo != 0) {
            result += IjoKanShi.valueOf("Ijo$yearNo").value + "(年), "
            rate += 25
        }

        if (rate > 0) {
            result = result.dropLast(2)
            result += " → $rate%"
        }

        return result
    }

    /**
     * Set shugoshin items
     */
    private fun setShugoshinItem(): String {
        val array = mUtil.getShugoshinNoArray(mDayKanNo, mDayShiNo)
        var result = ""
        for ((index, i) in array.withIndex()) {
            result += KanShi.valueOf("Kan${index + 1}").value + ", "
        }

        return result.dropLast(2)
    }
}