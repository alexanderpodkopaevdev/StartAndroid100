package com.example.p1101dialogfragment


import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment


/**
 * A simple [Fragment] subclass.
 */
class Fragment1 : DialogFragment(), View.OnClickListener {

    val LOG_TAG = "myLogs"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog?.setTitle("Helllllo")
        val view = inflater.inflate(R.layout.fragment_fragment1, container, false)
        view.findViewById<Button>(R.id.btnYes).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnNo).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnMaybe).setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        Log.d(LOG_TAG, "Dialog 1: " + (v as Button).text)
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Log.d(LOG_TAG, "Dialog 1: onDismiss")
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Log.d(LOG_TAG, "Dialog 1: onCancel")
    }


}
