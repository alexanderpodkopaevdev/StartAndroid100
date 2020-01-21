package com.example.p1171simplewidget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.config_activity.*


class ConfigActivity : Activity() {


    private var widgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var resultValue: Intent

    companion object {
        const val LOG_TAG = "myLogs"
        const val WIDGET_PREF = "widget_pref"
        const val WIDGET_TEXT = "widget_text_"
        const val WIDGET_COLOR = "widget_color_"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate config")
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            widgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
        }
        resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)

        setResult(RESULT_CANCELED, resultValue)
        setContentView(R.layout.config_activity)
        btnOk.setOnClickListener {
            val color = when (rgColor.checkedRadioButtonId) {
                R.id.radioRed -> Color.RED
                R.id.radioGreen -> Color.GREEN
                R.id.radioBlue -> Color.BLUE
                else -> Color.BLUE
            }
            val sp = getSharedPreferences(WIDGET_PREF, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString(WIDGET_TEXT + widgetId, etText.text.toString())
            editor.putInt(WIDGET_COLOR + widgetId, color)
            editor.apply()
            val appWidgetManager = AppWidgetManager.getInstance(this)
            MyWidget.updateWidget(this, appWidgetManager, sp, widgetId)
            setResult(RESULT_OK, resultValue)
            Log.d(LOG_TAG, "finish config $widgetId")
            finish()
        }

    }
}