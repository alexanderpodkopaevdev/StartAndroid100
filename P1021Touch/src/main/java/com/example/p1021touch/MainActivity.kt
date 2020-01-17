package com.example.p1021touch

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnTouchListener {
    var x: Float = 0f
    var y: Float = 0f
    var sDown: String? = null
    var sMove: String? = null
    var sUp: String? = null
    lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = TextView(this)
        tv.setOnTouchListener(this)
        setContentView(tv)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        x = event?.x ?: 0f
        y = event?.y ?: 0f
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                sDown = "Down: $x, $y"
                sMove = ""
                sUp = ""
            }
            MotionEvent.ACTION_MOVE -> {
                sMove = "Move: $x, $y"
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                sMove = ""
                sUp = "Up: $x,$y"
            }
        }
        tv.text = "$sDown \n  $sMove \n $sUp"
        return true
    }
}
