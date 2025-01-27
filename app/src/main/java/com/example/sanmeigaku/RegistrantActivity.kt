package com.example.sanmeigaku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sanmeigaku.Adapter.RegistrantListAdapter
import com.example.sanmeigaku.DB.AppDBHelpler
import com.example.sanmeigaku.databinding.ActivityRegistrantBinding

class RegistrantActivity : AppCompatActivity() {
    private val TAG: String = "RegistrantActivity"
    private lateinit var binding: ActivityRegistrantBinding

    /** Adapter for registrant list */
    lateinit var registrantListAdapter: RegistrantListAdapter

    /** Variable of search word */
    private var mSearchWord: String = ""

    /**
     * Create registrant activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i(TAG, "onCreate: create registrant activity")

        setRegistrantList()

        binding.searchEdit.doAfterTextChanged { word ->
            mSearchWord = word.toString()
        }

        binding.searchButton.setOnClickListener {
            setRegistrantList()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    /**
     * Set a list of registrants
     */
    private fun setRegistrantList() {
        val appDBHelper = AppDBHelpler(this)
        val registrantList = appDBHelper.makeRegistrantList(mSearchWord)
        binding.registrantList.registrantListRows.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            registrantListAdapter = RegistrantListAdapter(context, registrantList, supportFragmentManager)
            adapter = registrantListAdapter
        }
    }
}