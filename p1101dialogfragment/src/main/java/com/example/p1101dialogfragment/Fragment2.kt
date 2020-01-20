package com.example.p1101dialogfragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment


class Fragment2: DialogFragment(){
    val LOG_TAG = "myLogs"


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val adb = AlertDialog.Builder(activity)
            with (adb) {
                setTitle("Hello2")
                setPositiveButton(R.string.yes) { _, _ ->
                    Log.d(LOG_TAG, "Dialog 2: " + resources.getString(R.string.yes))
                }
                setNegativeButton(R.string.no) { _, _ ->
                    Log.d(LOG_TAG, "Dialog 2: " + resources.getString(R.string.no))
                }
                setNeutralButton(R.string.maybe) { _, _ ->
                    Log.d(LOG_TAG, "Dialog 2: " + resources.getString(R.string.maybe))
                }
            }
        return adb.create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Log.d(LOG_TAG, "Dialog 2: onDismiss")
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Log.d(LOG_TAG, "Dialog 2: onCancel")
    }
}