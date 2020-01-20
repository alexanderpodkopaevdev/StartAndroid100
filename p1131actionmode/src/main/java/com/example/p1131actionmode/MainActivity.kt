package com.example.p1131actionmode

import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val LOG_TAG = "myLogs"
    private val data = arrayOf("one", "two", "three", "four", "five")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, data)
        lvActionMode.adapter = adapter
        lvActionMode.choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL
        lvActionMode.setMultiChoiceModeListener(object : AbsListView.MultiChoiceModeListener {
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                mode?.finish()
                return false
            }

            override fun onItemCheckedStateChanged(
                mode: ActionMode?,
                position: Int,
                id: Long,
                checked: Boolean
            ) {
                Log.d(LOG_TAG, "position = $position checked = $checked");
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.menuInflater?.inflate(R.menu.context, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }



}
