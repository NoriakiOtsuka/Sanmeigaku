package com.example.sanmeigaku

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sanmeigaku.Util.Isouhou
import com.example.sanmeigaku.databinding.FragmentIsouhouBinding

class IsouhouFragment : Fragment() {
    private val TAG: String = "IsouhouFragment"
    private var _binding: FragmentIsouhouBinding? = null
    private val binding get() = _binding!!
    private val activity: AssessmentActivity.Companion = AssessmentActivity
    private lateinit var mContext: Context

    /** Variables of kan-shi number received from the assessment activity */
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
        val isouUtil = Isouhou()
        binding.gouhouList.sangoukaikyokuText.text = isouUtil.getSangouKaikyoku(mContext, mYearShiNo, mMonthShiNo, mDayShiNo)
        binding.gouhouList.hankaiText.text = isouUtil.getHankai(mContext, mYearShiNo, mMonthShiNo, mDayShiNo)
        binding.gouhouList.shigouText.text = isouUtil.getShigou(mContext, mYearShiNo, mMonthShiNo, mDayShiNo)
        binding.gouhouList.housaniText.text = isouUtil.getHousani(mContext, mYearShiNo, mMonthShiNo, mDayShiNo)
    }
}