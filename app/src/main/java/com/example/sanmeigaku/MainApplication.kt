package com.example.sanmeigaku

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class MainApplication : Application(), ViewModelStoreOwner {

    /** Manage ViewModelStore lifecycle */
    private val appViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    /** Get ViewModelStore value */
    override val viewModelStore: ViewModelStore
        get() = appViewModelStore
}