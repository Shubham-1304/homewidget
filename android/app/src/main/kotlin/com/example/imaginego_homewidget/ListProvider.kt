package com.example.imaginego_homewidget

import android.app.Activity
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory
import kotlinx.coroutines.NonDisposableHandle.parent
import es.antonborri.home_widget.HomeWidgetBackgroundIntent
import android.net.Uri
import android.os.Bundle
import java.nio.IntBuffer

class ListProvider(private val context: Context,intent: Intent) : RemoteViewsFactory {

    private val itemList = ArrayList<ListItem>()
    val appWidgetId: Int;

    init {
        appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID)
        Log.i("init_ChipProvider", "items added $appWidgetId")
        populateListItem()
    }

    private fun populateListItem() {
        itemList.removeAll(itemList)
//        val drawableResources = ImageUtils.getAllDrawableResources(this)
        for (i in 1..10) {
            Log.i("listview", "items added")
            val item = ListItem("Cute kawaii pikachu, {texture} texture, visible stitch line, soft smooth lighting, shimmer in the air, symmetrical, in re:Zero style, concept art, digital painting, looking into camera, square image","Danny Morris",R.drawable.img1)
            itemList.add(item)
        }
    }

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        Log.i("getViewAt", "items setup")
        val remoteViews = RemoteViews(context.packageName, R.layout.list_item)
        remoteViews.setTextViewText(R.id.title,itemList[position].title)
        remoteViews.setTextViewText(R.id.subtitle,itemList[position].subtitle)
        remoteViews.setImageViewResource(R.id.image,itemList[position].itemImage)

//        val fillInIntent = Intent().apply {
//            Bundle().also { extras ->
//                extras.putInt(EXTRA_ITEM, position)
//                putExtras(extras)
//                action = TOAST_ACTION
//            }
//        }

        val fillInIntent=Intent(context, StackWidgetProvider::class.java).apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            putExtra(EXTRA_ITEM, position)
            action = TOAST_ACTION
        }

        remoteViews.setOnClickFillInIntent(R.id.title, fillInIntent)


        return remoteViews

    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun onCreate() {
        Log.i("onCreate_ListProvider", "items added")
//        populateListItem()

        // Initialization tasks, if any.
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun onDataSetChanged() {
//        Log.i("datachng_ListProvider", "items added")
//        populateListItem()
        // If data set changes, update the widget
    }

    override fun onDestroy() {
        // Cleanup resources, if any.
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Implement other methods of RemoteViewsFactory if required

    // ...
}
