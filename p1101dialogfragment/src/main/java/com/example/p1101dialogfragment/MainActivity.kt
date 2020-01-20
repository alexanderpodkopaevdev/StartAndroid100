package com.example.p1101dialogfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var dialog1 : DialogFragment
    lateinit var dialog2 : DialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dialog1 = Fragment1()
        dialog2 = Fragment2()

        btnDlg1.setOnClickListener {
            dialog1.show(supportFragmentManager,"dialog1")
        }

        btnDlg2.setOnClickListener {
            dialog2.show(supportFragmentManager,"dialog2")
        }
    }
}
