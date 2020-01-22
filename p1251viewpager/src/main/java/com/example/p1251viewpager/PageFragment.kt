package com.example.p1251viewpager

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.random.Random

class PageFragment : Fragment() {
    var pageNumber : Int = 0
    var backColor : Int = 0


    companion object {
        const val ARGUMENT_PAGE_NUMBER = "arg_page_number"

        fun newInstance(page: Int) : PageFragment {
            val pageFragment = PageFragment()
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_PAGE_NUMBER,page)
            pageFragment.arguments = arguments
            return pageFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = arguments?.getInt(ARGUMENT_PAGE_NUMBER) ?: 0
        backColor = Color.argb(40, Random(256).nextInt(),Random(256).nextInt(),Random(256).nextInt())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment,null)
        val tvPage = view.findViewById<TextView>(R.id.tvPage)
        tvPage.text = pageNumber.toString()
        tvPage.setBackgroundColor(backColor)
        return view
    }
}