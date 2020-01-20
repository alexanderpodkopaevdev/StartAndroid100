package com.example.p1091listfragment

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment

class MainFragment : ListFragment() {
    val data = arrayOf("one", "two", "three", "four")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,data)
        listAdapter = adapter
    }
}