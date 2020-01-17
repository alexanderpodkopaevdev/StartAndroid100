package com.example.p1061fragmentactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),Fragment2.OnSomeEventListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment2 = Fragment2()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.fragment2,fragment2)
        ft.commit()
        btnFind.setOnClickListener {
            val frag1 = supportFragmentManager.findFragmentById(R.id.fragment1)
            (frag1?.view?.findViewById(R.id.textView) as TextView).text = "Access to Fragment 1 from Activity"
            val frag2 = supportFragmentManager.findFragmentById(R.id.fragment2)
            (frag2?.view?.findViewById(R.id.textView) as TextView).text = "Access to Fragment 2 from Activity"
        }

    }

    override fun someEvent(s: String) {
        val frag1 = supportFragmentManager.findFragmentById(R.id.fragment1)
        (frag1?.view?.findViewById(R.id.textView) as TextView).text = "Text from Fragment 2: $s"
    }
}
