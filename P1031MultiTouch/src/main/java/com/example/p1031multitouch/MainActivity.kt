package com.example.p1031multitouch

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnTouchListener {
    var sb = StringBuilder()
    lateinit var tv: TextView
    var upPI = 0
    var downPI = 0
    var inTouch = false
    var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = TextView(this)
        tv.textSize = 30f
        tv.setOnTouchListener(this)
        setContentView(tv)

    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        var actionMask = event?.actionMasked
        var pointerIndex = event?.actionIndex
        var pointerCount = event?.pointerCount

        when (actionMask) {
            MotionEvent.ACTION_DOWN -> {
                inTouch = true
                downPI = pointerIndex ?: 0
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                downPI = pointerIndex ?: 0
            }
            MotionEvent.ACTION_UP -> {
                inTouch = false
                sb.setLength(0)
                upPI = pointerIndex ?: 0
            }
            MotionEvent.ACTION_POINTER_UP -> { // прерывания касаний
                upPI = pointerIndex ?: 0
            }
            MotionEvent.ACTION_MOVE -> {
                sb.setLength(0)

                for (i in 0..9) {
                    sb.append("Index = $i")
                    if (i < pointerCount?:0) {
                        sb.append(", ID = " + event?.getPointerId(i))
                        sb.append(", X = " + event?.getX(i))
                        sb.append(", Y = " + event?.getY(i))
                    } else {
                        sb.append(", ID = ")
                        sb.append(", X = ")
                        sb.append(", Y = ")
                    }
                    sb.append("\r\n")
                }
            }
        }
        result = "down: $downPI\nup: $upPI\n"
        if (inTouch) {
            result += "pointerCount = $pointerCount\n$sb"
        }
        tv.text = result
        return true
    }
}
