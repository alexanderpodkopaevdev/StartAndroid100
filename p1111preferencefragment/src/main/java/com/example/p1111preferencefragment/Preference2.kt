package com.example.p1111preferencefragment

import android.os.Bundle
import androidx.preference.PreferenceFragment
import androidx.preference.PreferenceFragmentCompat


class Preference2 : PreferenceFragment() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref2)
    }
}