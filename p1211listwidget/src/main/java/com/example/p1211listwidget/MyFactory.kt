package com.example.p1211listwidget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyFactory(private var context: Context, intent: Intent?) :
    RemoteViewsService.RemoteViewsFactory {


    lateinit var data: ArrayList<String>
    private val sdf: SimpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale("ru", "RU"))
    var widgetID: Int = AppWidgetManager.INVALID_APPWIDGET_ID

    init {
        widgetID = intent?.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
    }

    override fun onCreate() {
        data = ArrayList()
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onDataSetChanged() {
        data.clear()
        data.add(sdf.format(Date(System.currentTimeMillis())))
        data.add(hashCode().toString())
        data.add(widgetID.toString())
        for (i in 3..14) {
            data.add("Item $i")
        }
    }

    override fun hasStableIds(): Boolean = true

    override fun getViewAt(position: Int): RemoteViews {
        val rView = RemoteViews(context.packageName, R.layout.item)
        rView.setTextViewText(R.id.tvItemText, data[position])
        val clickIntent = Intent()
        clickIntent.putExtra(MyProvider.ITEM_POSITION,position)
        rView.setOnClickFillInIntent(R.id.tvItemText,clickIntent)
        return rView
    }

    override fun getCount(): Int = data.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
    }
}