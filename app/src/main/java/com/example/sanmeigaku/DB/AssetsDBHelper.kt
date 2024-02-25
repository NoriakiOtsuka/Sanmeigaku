package com.example.sanmeigaku.DB

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class AssetsDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val TAG: String = "AssetsDBHelper"
    private val mContext: Context
    private val mDatabasePath: File
    private val mAppDatabasePath: File

    companion object {
        /**
         * Information of database
         * DATABASE_VERSION : Database version is centrally managed in the app database
         */
        const val DATABASE_VERSION = AppDBHelpler.DATABASE_VERSION
        const val DATABASE_NAME = "basic.sqlite3"
        const val APP_DATABASE_NAME = AppDBHelpler.DATABASE_NAME
    }

    override fun onCreate(db: SQLiteDatabase) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    init {
        mContext = context
        mDatabasePath = mContext.getDatabasePath(DATABASE_NAME)
        mAppDatabasePath = mContext.getDatabasePath(APP_DATABASE_NAME)
    }

    /**
     * If the app database does not exit, create a new one,
     * or update it if the version is updated.
     */
    fun changeDatabase() {
        val dbState = AppDBHelpler.dbState
        when (dbState) {
            AppDBHelpler.DB_DEFAULT -> {
                Log.i(TAG, "changeDatabase: App DB don't need to create or update")
                return
            }
        }

        try {
            copyDatabaseFromAsset()
            when (dbState) {
                AppDBHelpler.DB_CREATED -> {
                    addTable()
                    Log.i(TAG, "changeDatabase: App DB created")
                }
                AppDBHelpler.DB_UPDATED -> {
                    addTable()
                    Log.i(TAG, "changeDatabase: App DB updated")
                }
            }
        } catch (e: SQLiteException) {
            throw Error("Error copying database ${e.printStackTrace()}")
        } finally {
            mContext.deleteDatabase(DATABASE_NAME)
        }
    }

    /**
     * Copy the database in assets
     */
    private fun copyDatabaseFromAsset() {
        val inputStream: InputStream = mContext.assets.open(DATABASE_NAME)
        val outputStream: OutputStream = FileOutputStream(mDatabasePath)
        val buffer = ByteArray(1024)
        var size: Int

        while (inputStream.read(buffer).also { size = it } > 0) {
            outputStream.write(buffer, 0, size)
        }
        outputStream.flush()
        outputStream.close()
        inputStream.close()
        Log.i(TAG, "copyDatabaseFromAsset: Asset DB copy is succeeded")
    }

    /**
     * Add tables in SQLite database
     */
    @SuppressLint("Range")
    private fun addTable() {
        val appDbHelper = AppDBHelpler(mContext)
        val db: SQLiteDatabase = appDbHelper.writableDatabase
        val assetsDbHelper = AssetsDBHelper(mContext)
        val assetsDb: SQLiteDatabase = assetsDbHelper.readableDatabase

        try {
            var sql = "SELECT * FROM kanshi"
            var cursor = assetsDb.rawQuery(sql, null)
            cursor.use { c ->
                while (c.moveToNext()) {
                    val id = c.getInt(c.getColumnIndex("_id")).toString()
                    val date = c.getInt(c.getColumnIndex("date")).toString()
                    val yearKanShi = c.getInt(c.getColumnIndex("yearKanShi")).toString()
                    val monthKanShi = c.getInt(c.getColumnIndex("monthKanShi")).toString()
                    val firstDayKanShi = c.getInt(c.getColumnIndex("firstDayKanShi")).toString()

                    val values = ContentValues()
                    values.put("_id", id)
                    values.put("date", date)
                    values.put("yearKanShi", yearKanShi)
                    values.put("monthKanShi", monthKanShi)
                    values.put("firstDayKanShi", firstDayKanShi)
                    db.insert("kanshi", null, values)
                }
            }

            sql = "SELECT * FROM hoshi"
            cursor = assetsDb.rawQuery(sql, null)
            cursor.use { c ->
                while (c.moveToNext()) {
                    val id = c.getInt(c.getColumnIndex("_id")).toString()
                    val name = c.getString(c.getColumnIndex("name")).toString()
                    val detail = c.getString(c.getColumnIndex("detail")).toString()

                    val values = ContentValues()
                    values.put("_id", id)
                    values.put("name", name)
                    values.put("detail", detail)
                    db.insert("hoshi", null, values)
                }
            }
        } catch (e: IOException) {
            throw Error("Unable to read database ${e.printStackTrace()}")
        }
    }
}