package com.example.p1061fragmentactivity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment


class Fragment2 : Fragment() {

    val LOG_TAG = "myLogs"

    interface OnSomeEventListener {
        fun someEvent(s: String)
    }
    lateinit var someEventListener : OnSomeEventListener

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            someEventListener = activity as OnSomeEventListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement onSomeEventListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fragment2, container, false)
        val btn = view.findViewById(R.id.button) as Button
        btn.setOnClickListener {
            someEventListener.someEvent("Test text to Fragment1")
            Log.d(LOG_TAG, "Button click in Fragment2")
        }
        return view
    }


}
