package com.example.p1151multiplescreen

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.FragmentActivity

class DetailsActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish()
            return
        }
        if (savedInstanceState == null) {
            val details = DetailsFragment().newInstance(intent.getIntExtra("position",0))
            supportFragmentManager.beginTransaction().add(android.R.id.content,details).commit()
        }
    }
}