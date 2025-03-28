package com.example.sanmeigaku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sanmeigaku.Adapter.RegistrantListAdapter
import com.example.sanmeigaku.DB.AppDBHelpler
import com.example.sanmeigaku.ViewModel.RegistrantViewModel
import com.example.sanmeigaku.databinding.ActivityRegistrantBinding

class RegistrantActivity : AppCompatActivity() {
    private val TAG: String = "RegistrantActivity"
    private lateinit var binding: ActivityRegistrantBinding
    private lateinit var mAppDBHelper: AppDBHelpler

    /** View model for registrant */
    private val mRegistrantViewModel: RegistrantViewModel by viewModels()

    /** Adapter for registrant list */
    private lateinit var mRegistrantListAdapter: RegistrantListAdapter

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

        mAppDBHelper = AppDBHelpler(this)

        binding.searchEdit.doAfterTextChanged { word ->
            mSearchWord = word.toString()
        }

        binding.searchButton.setOnClickListener {
            setRegistrantList()
            binding.searchEdit.clearFocus()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    /**
     * Resume registrant activity
     */
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: resume assessment activity")

        setRegistrantList()
    }

    /**
     * Destroy registrant activity
     */
    override fun onDestroy() {
        super.onDestroy()

        if (isFinishing)
            mRegistrantViewModel.setItemsList(null)
        Log.i(TAG, "onDestroy: destroy assessment activity")
    }

    /**
     * Set a list of registrants
     */
    private fun setRegistrantList() {
        val registrantList = mAppDBHelper.makeRegistrantList(mSearchWord)
        mRegistrantViewModel.setItemsList(registrantList)

        mRegistrantViewModel.itemsList.observe(this) { newList ->
            if (newList != null)
                sortRegistrantList(newList)
        }

        binding.registrantList.registrantListRows.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            mRegistrantListAdapter = RegistrantListAdapter(context, mRegistrantViewModel, mRegistrantViewModel.itemsList.value!!, supportFragmentManager) { row, newItem ->
                mRegistrantViewModel.updateItem(row, newItem)
            }
            adapter = mRegistrantListAdapter
        }

        mRegistrantViewModel.updatedRowIndex.observe(this) { updatedRow ->
            updatedRow?.let {
                mRegistrantListAdapter.notifyItemChanged(it)
                Log.i(TAG, "updateRow")
            }
        }

        mRegistrantViewModel.deletedRowIndex.observe(this) { deletedRow ->
            deletedRow?.let {
                mRegistrantListAdapter.notifyItemRemoved(it)
                val itemCount = mRegistrantListAdapter.itemCount
                mRegistrantListAdapter.notifyItemRangeChanged(it, itemCount)
                Log.i(TAG, "deletedRow")
            }
        }
    }

    /**
     * Sort registrant list in ascending order
     */
    private fun sortRegistrantList(newList: MutableList<ArrayList<String>>) {
        val originalList = newList.map { ArrayList(it) }
        newList.sortBy { it[1] }

        val needsListSort = !originalList.indices.all { originalList[it][1] == newList[it][1] }
        if (needsListSort) {
            mRegistrantListAdapter.notifyDataSetChanged()
            Log.i(TAG, "registrant list is sorted")
        }
    }
}