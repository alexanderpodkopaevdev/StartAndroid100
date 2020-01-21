package com.example.p1171simplewidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.RemoteViews
import java.util.*

class MyWidget : AppWidgetProvider() {


    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Log.d(LOG_TAG, "onEnabled")
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d(LOG_TAG, " onUpdate ${Arrays.toString(appWidgetIds)}")
        val sp = context?.getSharedPreferences(ConfigActivity.WIDGET_PREF,Context.MODE_PRIVATE)
        if (appWidgetIds != null) {
            for (id in appWidgetIds) {
                updateWidget(context, appWidgetManager, sp, id)
            }
        }
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.d(LOG_TAG, "onDelete ${Arrays.toString(appWidgetIds)}")
        val editor = context?.getSharedPreferences(ConfigActivity.WIDGET_PREF,Context.MODE_PRIVATE)?.edit()
        if (appWidgetIds != null) {
            for (widgetId in appWidgetIds) {
                editor?.remove(ConfigActivity.WIDGET_TEXT + widgetId)
                editor?.remove(ConfigActivity.WIDGET_COLOR + widgetId)
            }
        }
        editor?.apply()
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        Log.d(LOG_TAG, "onDisabled")
    }

    companion object {
        const val LOG_TAG = "myLogs"
        fun updateWidget(
            context: Context?,
            appWidgetManager: AppWidgetManager?,
            sp: SharedPreferences?,
            widgetId: Int
        ) {
            Log.d(LOG_TAG, "updateWidget $widgetId")
            if (sp != null) {
                val widgetText = sp.getString(ConfigActivity.WIDGET_TEXT + widgetId,null) ?: return
                val widgetColor = sp.getInt(ConfigActivity.WIDGET_COLOR + widgetId, 0)
                val widgetView = RemoteViews(context?.packageName,R.layout.widget)
                widgetView.setTextViewText(R.id.tv, widgetText)
                widgetView.setInt(R.id.tv, "setBackgroundColor", widgetColor)

                appWidgetManager?.updateAppWidget(widgetId,widgetView)
            }
        }
    }
}