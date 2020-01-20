package com.example.p1111preferencefragment

import android.preference.PreferenceActivity


class MainActivity : PreferenceActivity() {

    override fun onBuildHeaders(target: MutableList<Header>?) {
        loadHeadersFromResource(R.xml.pref_head,target)
    }

    override fun isValidFragment(fragmentName: String?): Boolean {
        return Preference1::class.java.name == fragmentName || Preference2::class.java.name == fragmentName

    }
}
