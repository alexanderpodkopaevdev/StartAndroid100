package com.example.p1251viewpager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : FragmentActivity() {

    companion object {
        const val TAG = "myLogs"
        const val PAGE_COUNT = 10
    }


    lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pagerAdapter = MyFragmentPagerAdapter(supportFragmentManager)
        pager.adapter = pagerAdapter
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                Log.d(TAG, "onPageScrollStateChanged, state = $state")
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
              /*  Log.d(
                    TAG,
                    "onPageScrolled, position = $position positionOffset = $positionOffset positionOffsetPixels = $positionOffsetPixels"
                )*/
            }

            override fun onPageSelected(position: Int) {
                Log.d(TAG, "onPageSelected, position = $position")
            }

        })
    }

    class MyFragmentPagerAdapter(supportFragmentManager: FragmentManager) : FragmentPagerAdapter(
        supportFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getItem(position: Int): Fragment = PageFragment.newInstance(position)


        override fun getCount(): Int = PAGE_COUNT

        override fun getPageTitle(position: Int): CharSequence? = "Title $position"
    }

}
