package com.example.sanmeigaku

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sanmeigaku.databinding.FragmentMeishikiBinding

class MeishikiFragment : Fragment() {
    private val TAG: String = "MeishikiFragment"
    private var _binding: FragmentMeishikiBinding? = null
    private val binding get() = _binding!!
    private val activity: AssessmentActivity.Companion = AssessmentActivity

    /** Variables of user info received from the assessment activity */
    private val mName: String = activity.mName
    private val mYear: Int = activity.mYear
    private val mMonth: Int = activity.mMonth
    private val mDay: Int = activity.mDay
    private var mGender: Int = activity.mGender
    private var mAge: Int = activity.mAge

    /**
     * Create meishiki fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: create meishiki fragment")
    }

    /**
     * Create meishiki fragment view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeishikiBinding.inflate(inflater, container, false)
        Log.i(TAG, "onCreateView: meishiki fragment view create")

        return binding.root
    }

    /**
     * Created meishiki fragment view
     */
    @SuppressLint("StringFormatMatches")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: meishiki fragment view created")

        binding.nameText.text = mName
        binding.birthdayText.text = getString(R.string.meishiki_birthday_text, mYear, mMonth, mDay)
        binding.ageText.text = getString(R.string.meishiki_age_text, mAge)
        binding.genderText.text = when(mGender) {
            1 -> getString(R.string.meishiki_gender_male_text)
            2 -> getString(R.string.meishiki_gender_female_text)
            else -> ""
        }
    }

    /**
     * Destroy meishiki fragment view
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i(TAG, "onDestroyView: meishiki fragment view destroyed")
    }
}