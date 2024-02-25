package com.example.sanmeigaku.DB

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.IOException

class AppDBHelpler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val TAG: String = "AppDBHelpler"
    private val mContext: Context

    companion object {
        /**
         * Information of database
         * DATABASE_VERSION : increase version number when asset DB is upgraded.
         */
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "app.sqlite3"
        const val DB_DEFAULT = 0
        const val DB_CREATED = 1
        const val DB_UPDATED = 2

        /**
         * Table name of user and its columns
         */
        private const val TABLE_USER = "user"
        private const val _ID = "_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_BIRTHDAY = "birthday"
        private const val COLUMN_GENDER = "gender"

        /**
         * Table name of kanshi and its columns
         */
        private const val TABLE_KANSHI = "kanshi"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_YEAR_KANSHI = "yearKanShi"
        private const val COLUMN_MONTH_KANSHI = "monthKanShi"
        private const val COLUMN_FIRST_DAY_KANSHI = "firstDayKanShi"

        /**
         * Table name of hoshi and its columns
         */
        private const val TABLE_HOSHI = "hoshi"
        private const val COLUMN_DETAIL = "detail"

        /**
         * Entry name to create user table
         */
        private const val CREATE_USER_ENTRIES = "CREATE TABLE " + TABLE_USER + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_BIRTHDAY + " INTEGER NOT NULL, " +
                COLUMN_GENDER + " INTEGER NOT NULL)"

        /**
         * Entry name to create kanshi table
         */
        private const val CREATE_KANSHI_ENTRIES = "CREATE TABLE " + TABLE_KANSHI + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_DATE + " INTEGER NOT NULL, " +
                COLUMN_YEAR_KANSHI + " INTEGER NOT NULL, " +
                COLUMN_MONTH_KANSHI + " INTEGER NOT NULL, " +
                COLUMN_FIRST_DAY_KANSHI + " INTEGER NOT NULL)"

        /**
         * Entry name to create hoshi table
         */
        private const val CREATE_HOSHI_ENTRIES = "CREATE TABLE " + TABLE_HOSHI + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_DETAIL + " TEXT NOT NULL)"

        /**
         * Entry name to delete kanshi table
         */
        private const val DELETE_KANSHI_ENTRIES = "DROP TABLE $TABLE_KANSHI"

        /**
         * Entry name to delete hoshi table
         */
        private const val DELETE_HOSHI_ENTRIES = "DROP TABLE $TABLE_HOSHI"

        /**
         * manage App DB state
         */
        var appDBCreate: Boolean = false
        var isDBUpdated: Boolean = false
        var dbState: Int = DB_DEFAULT
    }

    init {
        mContext = context
    }

    /**
     * Create a database for the app when the app is launched for the first time
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_ENTRIES)
        db.execSQL(CREATE_KANSHI_ENTRIES)
        db.execSQL(CREATE_HOSHI_ENTRIES)
        appDBCreate = true
        dbState = DB_CREATED
        Log.i(TAG, "onCreate: DB created")
    }

    /**
     * Detect "DATABASE_VERSION" upgrades and pass subsequent processing to AssetsDBHelper
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // assetのDBを更新したらDATABASE_VERSIONを繰り上げてここを通るようにする
        // するとここで更新されたことがわかるので　checkDatabaseUpdated()は不要
        db.execSQL(DELETE_KANSHI_ENTRIES)
        db.execSQL(DELETE_HOSHI_ENTRIES)
        db.execSQL(CREATE_KANSHI_ENTRIES)
        db.execSQL(CREATE_HOSHI_ENTRIES)
        isDBUpdated = true
        dbState = DB_UPDATED
        Log.i(TAG, "onUpgrade: DB updated to version $newVersion")
    }

    /**
     * Set the range of dates that can be selected in the date picker dialog
     */
    @SuppressLint("Range")
    fun setDateRange(activity: Activity) {
        val dbHelper = AppDBHelpler(mContext)
        val db: SQLiteDatabase = dbHelper.writableDatabase
        var startDate = 0
        var endDate = 0

        try {
            var sql = "SELECT date FROM kanshi LIMIT 1"
            var cursor = db.rawQuery(sql, null)
            cursor.use { c ->
                while (c.moveToNext()) {
                    startDate = c.getInt(c.getColumnIndex("date"))
                }
            }

            sql = "SELECT date FROM kanshi ORDER BY date DESC LIMIT 1"
            cursor = db.rawQuery(sql, null)
            cursor.use { c ->
                while (c.moveToNext()) {
                    endDate = c.getInt(c.getColumnIndex("date")) -1
                }
            }
        } catch (e: IOException) {
            throw Error("Unable to read database ${e.printStackTrace()}")
        }

        val sharedPref = activity.getSharedPreferences("app_database", Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putInt("start_date", startDate)
            putInt("end_date", endDate)
            apply()
        }

        db.close()
    }
}