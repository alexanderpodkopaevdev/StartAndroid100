package com.example.p1151multiplescreen

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment

class TitlesFragment : ListFragment() {

    interface OnItemClickListener {
        fun itemClick(position: Int)
    }

    lateinit var listener : OnItemClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,resources.getStringArray(R.array.headers))
        listAdapter = adapter
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        listener.itemClick(position)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            listener = activity as OnItemClickListener
        }
    }
}