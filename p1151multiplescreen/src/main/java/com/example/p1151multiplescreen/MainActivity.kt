package com.example.p1151multiplescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity(),TitlesFragment.OnItemClickListener {
    var position = 0
    var withDetails = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position")
        }
        withDetails = findViewById<FrameLayout>(R.id.cont) != null
        if (withDetails)
            showDetails(position)
    }

    private fun showDetails(position: Int) {
        if (withDetails) {

            var details = supportFragmentManager.findFragmentById(R.id.cont) as DetailsFragment?
            if (details == null || details.getPosition() != position) {
                details = DetailsFragment().newInstance(position)
                supportFragmentManager.beginTransaction().replace(R.id.cont, details).commit()
            }
        } else {
            startActivity(Intent(this,DetailsActivity::class.java).putExtra("position",position))
        }

    }

    override fun itemClick(position: Int) {
        this.position = position
        showDetails(position)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putInt("position", position)
    }
}
