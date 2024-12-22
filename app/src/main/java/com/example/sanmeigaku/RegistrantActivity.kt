package com.example.sanmeigaku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sanmeigaku.Adapter.RegistrantListAdapter
import com.example.sanmeigaku.DB.AppDBHelpler
import com.example.sanmeigaku.databinding.ActivityRegistrantBinding

class RegistrantActivity : AppCompatActivity() {
    private val TAG: String = "RegistrantActivity"
    private lateinit var binding: ActivityRegistrantBinding

    /**
     * Create registrant activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i(TAG, "onCreate: create registrant activity")

        val appDBHelper = AppDBHelpler(this)
        val registrantList = appDBHelper.readRegistrantList()
        binding.registrantList.registrantList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = RegistrantListAdapter(context, registrantList)
        }
    }
}