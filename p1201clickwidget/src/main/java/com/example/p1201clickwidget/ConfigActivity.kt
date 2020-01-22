package com.example.p1201clickwidget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_config.*


class ConfigActivity : AppCompatActivity() {

    companion object {
        const val WIDGET_PREF = "widget_pref"
        const val WIDGET_TIME_FORMAT = "widget_time_format_"
        const val WIDGET_COUNT = "widget_count_"
    }
    var widgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    lateinit var resultValue : Intent
    lateinit var sp : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        if (extras!= null) {
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID)
        }
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
        }

        resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId)
        setResult(Activity.RESULT_CANCELED,resultValue)
        setContentView(R.layout.activity_config)

        sp = getSharedPreferences(WIDGET_PREF, Context.MODE_PRIVATE)
        etFormat.setText(sp.getString(WIDGET_TIME_FORMAT + widgetId, "HH:mm:ss"))
        val count = sp.getInt(WIDGET_COUNT + widgetId, -1)
        if (count == -1) {
            sp.edit().putInt(WIDGET_COUNT,0).apply()
        }

        btnOk.setOnClickListener {
            sp.edit().putString(WIDGET_TIME_FORMAT + widgetId,etFormat.text.toString()).apply()
            setResult(Activity.RESULT_OK,resultValue)
            MyWidget.updateWidget(this, AppWidgetManager.getInstance(this),widgetId)
            finish()
        }

    }
}
