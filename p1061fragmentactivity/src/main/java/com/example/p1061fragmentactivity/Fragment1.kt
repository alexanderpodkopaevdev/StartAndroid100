package com.example.p1061fragmentactivity


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 */
class Fragment1 : Fragment() {

    val LOG_TAG = "myLogs"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fragment1, container, false)
        val btn = view.findViewById(R.id.button) as Button
        btn.setOnClickListener {
            Log.d(LOG_TAG, "Button click in Fragment1")
            activity?.findViewById<Button>(R.id.btnFind)?.text = "Access from Fragment1"
        }
        return view
    }


}
