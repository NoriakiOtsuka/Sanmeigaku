package com.example.sanmeigaku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sanmeigaku.DB.AppDBHelpler
import com.example.sanmeigaku.DB.AssetsDBHelper
import com.example.sanmeigaku.Util.MessageDialog
import com.example.sanmeigaku.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    /**
     * Create main activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkDatabaseExist()
        Log.i(TAG, "onCreate: main activity started")
    }

    /**
     * Check if the database exists in assets
     */
    fun checkDatabaseExist() {
        val assetsDbHelper = AssetsDBHelper(this)
        var assetDBExist = false
        val assets = resources.assets.list("")
        for (asset in assets!!) {
            if (asset.equals(assetsDbHelper.databaseName)) {
                Log.i(TAG, "checkDatabaseExist: database should be created or updated")
                val appDBHelper = AppDBHelpler(this)
                appDBHelper.writableDatabase
                assetsDbHelper.changeDatabase()
                assetDBExist = true

                break
            }
        }

        if (!assetDBExist) {
            val title = getString(R.string.dialog_caution_title)
            val message = getString(R.string.dialog_db_setup_failed_message)
            val dialog = MessageDialog(title, message, "OK", {}, "", {})
            dialog.isCancelable = false
            dialog.show(supportFragmentManager, "database_dialog")
        }
    }
}