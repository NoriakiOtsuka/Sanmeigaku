package com.example.sanmeigaku.Util

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MessageDialog(private val title: String,
                    private val message: String,
                    private val okLabel: String,
                    private val okSelected: ()-> Unit,
                    private val ngLabel: String,
                    private val ngSelected: ()-> Unit
                    ) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(okLabel) { dialog, which ->
                okSelected()
            }
            .setNegativeButton(ngLabel) { dialog, which ->
                ngSelected()
            }

        return builder.create()
    }
}