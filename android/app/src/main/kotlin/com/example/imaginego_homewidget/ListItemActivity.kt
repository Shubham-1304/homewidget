package com.example.imaginego_homewidget

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import es.antonborri.home_widget.HomeWidgetBackgroundIntent
import io.flutter.embedding.android.FlutterActivity

class ListItemActivity : FlutterActivity(){
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            Log.i("tap list working","okkkkkkkkkkkkkk")
        }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        Log.i("tap list working","well done 2")
        val selectIntent= HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://selecttile"))
        return super.onCreateView(name, context, attrs)
    }
}