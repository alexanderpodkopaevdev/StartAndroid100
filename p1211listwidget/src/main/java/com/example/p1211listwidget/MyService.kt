package com.example.p1211listwidget

import android.content.Intent
import android.widget.RemoteViewsService

class MyService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return MyFactory(applicationContext,intent)
    }
}