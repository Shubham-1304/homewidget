package com.example.imaginego_homewidget


import android.appwidget.AppWidgetManager
import android.content.Intent
import android.widget.RemoteViewsService
//import io.flutter.Log
import android.util.Log

class WidgetService : RemoteViewsService() {
    /*
     * So pretty simple just defining the Adapter of the listview
     * here Adapter is ListProvider
     * */

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        // val appWidgetId = intent.getIntExtra(
        //     AppWidgetManager.EXTRA_APPWIDGET_ID,
        //     AppWidgetManager.INVALID_APPWIDGET_ID
        // )
        Log.i("ChipService_onGet", "items added")

        return ListProvider(this.applicationContext,intent)
    }


}