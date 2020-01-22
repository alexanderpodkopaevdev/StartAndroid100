package com.example.p1201clickwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*


class MyWidget : AppWidgetProvider() {

    companion object {
        const val ACTION_CHANGE = "com.example.p1201clickwidget.change_count"
        fun updateWidget(
            context: Context?,
            appWidgetManager: AppWidgetManager?,
            widgetID: Int,
            isOnlyCount: Boolean = false
        ) {
            val sp = context?.getSharedPreferences(ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE)
            val timeFormat = sp?.getString(ConfigActivity.WIDGET_TIME_FORMAT  + widgetID, null) ?: return
            val sdf = SimpleDateFormat(timeFormat,Locale("ru", "RU"))
            val currentTime = sdf.format(System.currentTimeMillis())
            val count = sp.getInt(ConfigActivity.WIDGET_COUNT + widgetID,0).toString()
            val widgetView = RemoteViews(context.packageName,R.layout.widget)
            if (!isOnlyCount) widgetView.setTextViewText(R.id.tvTime,currentTime)
            widgetView.setTextViewText(R.id.tvCount,count)

            val configIntent = Intent(context,ConfigActivity::class.java)
            configIntent.action = AppWidgetManager.ACTION_APPWIDGET_CONFIGURE
            configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetID)
            var pIntent = PendingIntent.getActivity(context,widgetID,configIntent,0)
            widgetView.setOnClickPendingIntent(R.id.tvPressConfig, pIntent)

            val updateIntent = Intent(context,MyWidget::class.java)
            updateIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,	intArrayOf(widgetID))
            pIntent = PendingIntent.getBroadcast(context,widgetID,updateIntent,0)
            widgetView.setOnClickPendingIntent(R.id.tvPressUpdate,pIntent)

            val countIntent = Intent(context,MyWidget::class.java)
            countIntent.action = ACTION_CHANGE
            countIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetID)
            pIntent = PendingIntent.getBroadcast(context,widgetID,countIntent,0)
            widgetView.setOnClickPendingIntent(R.id.tvPressCount,pIntent)

            val googleIntent = Intent()
            googleIntent.action = Intent.ACTION_VIEW
            googleIntent.data = Uri.parse("http://www.google.com")
            pIntent = PendingIntent.getActivity(context,widgetID,googleIntent,0)
            widgetView.setOnClickPendingIntent(R.id.tvPressGoogle,pIntent)

            appWidgetManager?.updateAppWidget(widgetID,widgetView)

        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if(intent?.action== ACTION_CHANGE) {
            var mAppWidgetID = AppWidgetManager.INVALID_APPWIDGET_ID
            val extras = intent.extras
            if (extras != null) {
                mAppWidgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID)
            }

            if (mAppWidgetID != AppWidgetManager.INVALID_APPWIDGET_ID) {
                val sp = context?.getSharedPreferences(ConfigActivity.WIDGET_PREF,Context.MODE_PRIVATE)
                var count = sp?.getInt(ConfigActivity.WIDGET_COUNT + mAppWidgetID,0) ?: 0
                sp?.edit()?.putInt(ConfigActivity.WIDGET_COUNT + mAppWidgetID,++count)?.apply()
                updateWidget(context, AppWidgetManager.getInstance(context),mAppWidgetID, true)
            }
        }
    }

    override fun onUpdate(
        context: Context?, appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        // обновляем все экземпляры
        for (i in appWidgetIds) {
            updateWidget(context, appWidgetManager, i)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        val editor = context.getSharedPreferences(
            ConfigActivity.WIDGET_PREF,
            Context.MODE_PRIVATE
        ).edit()
        for (widgetID in appWidgetIds) {
            editor.remove(ConfigActivity.WIDGET_TIME_FORMAT + widgetID)
            editor.remove(ConfigActivity.WIDGET_COUNT + widgetID)
        }
        editor.apply()
    }


}