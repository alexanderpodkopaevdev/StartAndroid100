package com.example.p1051fragmentdynamic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var frag1 : Fragment
    private lateinit var frag2 : Fragment
    private lateinit var fTrans : FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        frag1 = Fragment1()
        frag2 = Fragment2()

        btnAdd.setOnClickListener {
            fTrans = supportFragmentManager.beginTransaction()
            fTrans.add(R.id.frgmCont,frag1)
            if (chbStack.isChecked) fTrans.addToBackStack(null)
            fTrans.commit()
        }
        btnRemove.setOnClickListener {
            fTrans = supportFragmentManager.beginTransaction()
            fTrans.remove(frag1)
            if (chbStack.isChecked) fTrans.addToBackStack(null)
            fTrans.commit()
        }
        btnReplace.setOnClickListener {
            fTrans = supportFragmentManager.beginTransaction()
            fTrans.replace(R.id.frgmCont,frag2)
            if (chbStack.isChecked) fTrans.addToBackStack(null)
            fTrans.commit()
        }
    }
}
