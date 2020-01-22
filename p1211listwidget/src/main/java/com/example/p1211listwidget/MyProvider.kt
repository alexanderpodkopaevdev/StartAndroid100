package com.example.p1211listwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MyProvider : AppWidgetProvider() {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale("ru", "RU"))
    companion object {
        const val ACTION_ON_CLICK = "com.example.p1211listwidget.itemonclick"
        const val ITEM_POSITION = "item_position"
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        if (appWidgetIds != null) {
            for (widgetID in appWidgetIds) {
                updateWidget(context, appWidgetManager, widgetID)
            }
        }
    }

    private fun updateWidget(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        widgetID: Int
    ) {
        val rView = RemoteViews(context?.packageName, R.layout.widget)
        setUpdateTV(rView, context, widgetID)
        setList(rView, context, widgetID)
        setListClick(rView, context, widgetID)
        appWidgetManager?.updateAppWidget(widgetID, rView)
        appWidgetManager?.notifyAppWidgetViewDataChanged(widgetID,R.id.lvList)
    }

    private fun setListClick(rView: RemoteViews, context: Context?, widgetID: Int) {
        val listClickIntent = Intent(context,MyProvider::class.java)
        listClickIntent.action = ACTION_ON_CLICK
        val listClickPendingIntent = PendingIntent.getBroadcast(context,widgetID,listClickIntent,0)
        rView.setPendingIntentTemplate(R.id.lvList,listClickPendingIntent)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action == ACTION_ON_CLICK) {
            val itemPos = intent.getIntExtra(ITEM_POSITION,-1)
            if (itemPos != -1) {
                Toast.makeText(context, "Clicked on item  $itemPos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setList(rView: RemoteViews, context: Context?, widgetID: Int) {
        val adapter = Intent(context,MyService::class.java)
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetID)
        val data = Uri.parse(adapter.toUri(Intent.URI_INTENT_SCHEME))
        adapter.data = data
        rView.setRemoteAdapter(R.id.lvList,adapter)
    }

    private fun setUpdateTV(rView: RemoteViews, context: Context?, widgetID: Int) {
        rView.setTextViewText(R.id.tvUpdate, sdf.format(Date(System.currentTimeMillis())))
        val updateIntent = Intent(context,MyProvider::class.java)
        updateIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(widgetID))
        val updPendingIntent = PendingIntent.getBroadcast(context,widgetID,updateIntent,0)
        rView.setOnClickPendingIntent(R.id.tvUpdate,updPendingIntent)
    }
}


