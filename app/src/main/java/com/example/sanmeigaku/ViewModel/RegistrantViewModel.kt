package com.example.sanmeigaku.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrantViewModel : ViewModel() {
    private val TAG: String = "RegistrantViewModel"

    /** Registrant item list */
    private val _itemsList = MutableLiveData<MutableList<ArrayList<String>>?>(null)
    val itemsList: LiveData<MutableList<ArrayList<String>>?> get() = _itemsList

    /** Updated row index */
    private val _updatedRowIndex = MutableLiveData<Int?>()
    val updatedRowIndex: LiveData<Int?> get() = _updatedRowIndex

    /** Deleted row index */
    private val _deletedRowIndex = MutableLiveData<Int?>()
    val deletedRowIndex: LiveData<Int?> get() = _deletedRowIndex

    /** Registrant ID */
    private val _registrantId = MutableLiveData(0)
    val registrantId: LiveData<Int?> get() = _registrantId

    /** Registrant item position */
    private val _itemPosition = MutableLiveData(-1)
    val itemPosition: LiveData<Int?> get() = _itemPosition

    /** Registrant name */
    private val _name = MutableLiveData("")
    val name: LiveData<String> get() = _name

    /** Registrant kana */
    private val _kana = MutableLiveData("")
    val kana: LiveData<String> get() = _kana

    /** Registrant birthday */
    private val _birthday = MutableLiveData(0)
    val birthday: LiveData<Int> get() = _birthday

    /** Registrant gender */
    private val _gender = MutableLiveData(0)
    val gender: LiveData<Int> get() = _gender

    /**
     * Set registrant item list
     */
    fun setItemsList(newItemList: MutableList<ArrayList<String>>?) {
        _itemsList.value = newItemList
    }

    /**
     * Update registrant info
     */
    fun updateItem(row: Int, newItem: ArrayList<String>) {
        _itemsList.value?.let { list ->
            list[row] = newItem
            _itemsList.value = list
        }
        _updatedRowIndex.value = row
        Log.i(TAG, "updateItem: row $row updated")
    }

    /**
     * Delete registrant
     */
    fun deleteItem(row: Int) {
        _itemsList.value?.let { list ->
            if (row < list.size) {
                list.removeAt(row)
                _itemsList.value = list
                _deletedRowIndex.value = row
            }
        }
        Log.i(TAG, "deleteItem: row $row deleted")
    }

    /**
     * Set registrant ID
     */
    fun setRegistrantId(content: Int) {
        _registrantId.value = content
    }

    /**
     * Set registrant item position
     */
    fun setItemPosition(content: Int) {
        _itemPosition.value = content
    }

    /**
     * Set registrant name
     */
    fun setName(content: String) {
        _name.value = content
    }

    /**
     * Set registrant kana
     */
    fun setKana(content: String) {
        _kana.value = content
    }

    /**
     * Set registrant birthday
     */
    fun setBirthday(content: Int) {
        _birthday.value = content
    }

    /**
     * Set registrant gender
     */
    fun setGender(content: Int) {
        _gender.value = content
    }
}