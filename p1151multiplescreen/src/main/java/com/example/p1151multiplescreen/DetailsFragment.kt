package com.example.p1151multiplescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.details,container,false)
        val tv : TextView = view.findViewById(R.id.tvText)
        tv.text = resources.getStringArray(R.array.content)[getPosition()]
        return view
    }

    fun getPosition(): Int {
        return arguments?.getInt("position",0) ?: 0
    }

    fun newInstance(pos: Int) : DetailsFragment {
        val details = DetailsFragment()
        var args = Bundle()
        args.putInt("position",pos)
        details.arguments = args
        return details
    }
}