package com.example.sanmeigaku.DB

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.IOException

class AppDBHelpler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val TAG: String = "AppDBHelpler"
    private val mContext: Context

    /**
     * Data class of kan-shi table
     */
    data class KanshiTable(
        var date: Int,
        var yearKanShi: Int,
        var monthKanShi: Int,
        var dateKanShi: Int
        )

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
        private const val COLUMN_KANA = "kana"
        private const val COLUMN_BIRTHDAY = "birthday"
        private const val COLUMN_GENDER = "gender"

        /**
         * Table name of kanshi and its columns
         */
        private const val TABLE_KANSHI = "kanshi"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_YEAR_KANSHI = "year_kanshi"
        private const val COLUMN_MONTH_KANSHI = "month_kanshi"
        private const val COLUMN_DATE_KANSHI = "date_kanshi"

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
                COLUMN_KANA + " TEXT NOT NULL, " +
                COLUMN_BIRTHDAY + " INTEGER NOT NULL, " +
                COLUMN_GENDER + " INTEGER NOT NULL, " +
                "UNIQUE(" + COLUMN_NAME + ", " + COLUMN_KANA + ", " + COLUMN_BIRTHDAY + ", " + COLUMN_GENDER + "))"

        /**
         * Entry name to create kanshi table
         */
        private const val CREATE_KANSHI_ENTRIES = "CREATE TABLE " + TABLE_KANSHI + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_DATE + " INTEGER NOT NULL, " +
                COLUMN_YEAR_KANSHI + " INTEGER NOT NULL, " +
                COLUMN_MONTH_KANSHI + " INTEGER NOT NULL, " +
                COLUMN_DATE_KANSHI + " INTEGER NOT NULL)"

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

        /**
         * Constant to manage causes in sqlite error log
         */
        private const val SQLITE_ERROR_UNIQUE = "UNIQUE constraint failed"
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
        val db = dbHelper.writableDatabase
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

    /**
     * Add client to registrant list
     * @return 1:success, 0:error(other), -1:error(unique)
     */
    fun addRegistrant(name: String, kana: String, birthday: Int, gender: Int): Int {
        var result = 1
        val dbHelper = AppDBHelpler(mContext)
        val db = dbHelper.writableDatabase

        val sql = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_KANA, kana)
            put(COLUMN_BIRTHDAY, birthday)
            put(COLUMN_GENDER, gender)
        }

        try {
            db.insertOrThrow(TABLE_USER, null, sql)
        } catch (e: SQLiteConstraintException) {
            result = when {
                e.message?.contains(SQLITE_ERROR_UNIQUE) == true -> -1
                else -> 0
            }
        }
        db.close()

        return result
    }

    /**
     * Make a list of registrants
     */
    fun makeRegistrantList(word: String): MutableList<ArrayList<String>> {
        val dbHelper = AppDBHelpler(mContext)
        val db = dbHelper.writableDatabase

        val selection = "$COLUMN_NAME LIKE '%$word%' OR $COLUMN_KANA LIKE '%$word%'"
        val cursor = db.query(TABLE_USER, null, selection, null, null, null, COLUMN_KANA)
        val registrantList = MutableList(cursor.count) { ArrayList<String>(3) }
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val kana = getString(getColumnIndexOrThrow(COLUMN_KANA))
                val birthday = getInt(getColumnIndexOrThrow(COLUMN_BIRTHDAY))
                val gender = getInt(getColumnIndexOrThrow(COLUMN_GENDER))

                val clientInfo = arrayListOf<String>()
                clientInfo.add(name)
                clientInfo.add(kana)
                clientInfo.add(birthday.toString())
                clientInfo.add(gender.toString())
                registrantList[cursor.position] = clientInfo
            }
        }
        cursor.close()

        return registrantList
    }

    /**
     * Get values from the kan-shi table
     */
    @SuppressLint("Range")
    fun readKanshiTable(year: Int, month: Int, day: Int): KanshiTable {
        val dbHelper = AppDBHelpler(mContext)
        val db = dbHelper.writableDatabase
        val birthday = "%04d".format(year) + "%02d".format(month) + "%02d".format(day)
        var id = 0
        var date = 0
        var yearKanShi = 0
        var monthKanShi = 0
        var dateKanShi = 0

        try {
            val sql = "SELECT * FROM kanshi WHERE date <= $birthday ORDER BY date DESC LIMIT 1"
            val cursor = db.rawQuery(sql, null)
            cursor.use { c ->
                while (c.moveToNext()) {
                    id = c.getInt(c.getColumnIndex("_id"))
                    date = c.getInt(c.getColumnIndex("date"))
                    yearKanShi = c.getInt(c.getColumnIndex("year_kanshi"))
                    monthKanShi = c.getInt(c.getColumnIndex("month_kanshi"))
                    dateKanShi = c.getInt(c.getColumnIndex("date_kanshi"))
                }
            }
        } catch (e: IOException) {
            throw Error("Unable to read database")
        }

        db.close()

        return KanshiTable(date, yearKanShi, monthKanShi, dateKanShi)
    }

    /**
     * Get the first day of the month after the birthday
     */
    @SuppressLint("Range")
    fun getNextFirstDay(year: Int, month: Int, day: Int): String {
        val dbHelper = AppDBHelpler(mContext)
        val db = dbHelper.writableDatabase
        var date: String = ""
        val birthday = "%04d".format(year) + "%02d".format(month) + "%02d".format(day)

        try {
            val sql = "SELECT * FROM kanshi WHERE date >= $birthday LIMIT 1"
            val cursor = db.rawQuery(sql, null)
            cursor.use { c ->
                while (c.moveToNext()) {
                    date = c.getInt(c.getColumnIndex("date")).toString()
                }
            }
        } catch (e: IOException) {
            throw Error("Unable to read database")
        }
        db.close()

        return date
    }

    /**
     * Get the registrant's ID from the registrant list
     * @return registrant's ID
     */
    fun getRegistrantId(array: ArrayList<String>): Int {
        val dbHelper = AppDBHelpler(mContext)
        val db = dbHelper.writableDatabase

        val name = array[0]
        val kana = array[1]
        val birthday = array[2]
        val gender = array[3]
        val selection = "$COLUMN_NAME = '$name' AND $COLUMN_KANA = '$kana' AND $COLUMN_BIRTHDAY = '$birthday' AND $COLUMN_GENDER = '$gender'"
        val cursor = db.query(TABLE_USER, null, selection, null, null, null, null)

        val id = if (cursor.moveToFirst()) {
            cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
        } else {
            0
        }
        cursor.close()
        Log.i(TAG, "getRegistrantId: registrant's ID is $id")

        return id
    }

    /**
     * Check for duplicate registrant info
     * @return 1:updatable, 0:no need to update, -1:duplicated
     */
    fun checkDuplicateRegistrant(id: Int, array: ArrayList<String>): Int {
        val dbHelper = AppDBHelpler(mContext)
        val db = dbHelper.writableDatabase

        val name = array[0]
        val kana = array[1]
        val birthday = array[2]
        val gender = array[3]
        val sql = "SELECT $_ID FROM $TABLE_USER WHERE $COLUMN_NAME = ? AND $COLUMN_KANA = ? AND $COLUMN_BIRTHDAY = ? AND $COLUMN_GENDER = ?"
        val cursor = db.rawQuery(sql, arrayOf(name, kana, birthday, gender))
        val duplicatedId = if (cursor.moveToFirst()) {
            cursor.getLong(cursor.getColumnIndexOrThrow(_ID)).toInt()
        } else {
            0
        }
        cursor.close()

        val result = when (duplicatedId) {
            0 -> 1
            id -> 0
            else -> -1
        }
        Log.i(TAG, "checkDuplicateRegistrant: whether the registrant info can be updated or not is $result")

        return result
    }

    /**
     * Update client to registrant list
     */
    fun updateRegistrant(id: Int, array: ArrayList<String>) {
        val dbHelper = AppDBHelpler(mContext)
        val db = dbHelper.writableDatabase

        val name = array[0]
        val kana = array[1]
        val birthday = array[2]
        val gender = array[3]
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_KANA, kana)
            put(COLUMN_BIRTHDAY, birthday)
            put(COLUMN_GENDER, gender)
        }
        val selection = "$_ID = ?"
        val selectionArgs = arrayOf(id.toString())
        db.update(TABLE_USER, values, selection, selectionArgs)
    }

    /**
     * Delete client to registrant list
     */
    fun deleteRegistrant(id: Int) {
        val dbHelper = AppDBHelpler(mContext)
        val db = dbHelper.writableDatabase

        val selection = "$_ID = ?"
        val selectionArgs = arrayOf(id.toString())
        db.delete(TABLE_USER, selection, selectionArgs)
    }
}