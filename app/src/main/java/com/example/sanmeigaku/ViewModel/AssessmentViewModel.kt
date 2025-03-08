package com.example.sanmeigaku.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AssessmentViewModel : ViewModel() {
    /** Assessment name */
    private val _name = MutableLiveData("")
    val name: LiveData<String> get() = _name

    /** Assessment kana */
    private val _kana = MutableLiveData("")
    val kana: LiveData<String> get() = _kana

    /** Assessment birth year */
    private val _year = MutableLiveData(0)
    val year: LiveData<Int> get() = _year

    /** Assessment birth month */
    private val _month = MutableLiveData(0)
    val month: LiveData<Int> get() = _month

    /** Assessment birth day */
    private val _day = MutableLiveData(0)
    val day: LiveData<Int> get() = _day

    /** Assessment gender */
    private val _gender = MutableLiveData(0)
    val gender: LiveData<Int> get() = _gender

    /**
     * Set assessment name
     */
    fun setName(content: String) {
        _name.value = content
    }

    /**
     * Set assessment kana
     */
    fun setKana(content: String) {
        _kana.value = content
    }

    /**
     * Set assessment birth year
     */
    fun setYear(content: Int) {
        _year.value = content
    }

    /**
     * Set assessment birth month
     */
    fun setMonth(content: Int) {
        _month.value = content
    }

    /**
     * Set assessment birth day
     */
    fun setDay(content: Int) {
        _day.value = content
    }

    /**
     * Set assessment gender
     */
    fun setGender(content: Int) {
        _gender.value = content
    }
}