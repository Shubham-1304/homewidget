package com.example.imaginego_homewidget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import es.antonborri.home_widget.HomeWidgetBackgroundIntent
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import kotlinx.coroutines.handleCoroutineException

//class MainActivity: FlutterActivity() {
//    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
//        super.onCreate(savedInstanceState, persistentState)
//        Log.i("tap list working","good")
//    }
//
////    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
////        Log.i("tap list working","well done")
////        val selectIntent= HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://selecttile"))
////        return super.onCreateView(name, context, attrs)
////    }
//
//    override fun onNewIntent(intent: Intent) {
//        Log.i("onNewIntent","well done")
//        super.onNewIntent(intent)
//        handleIntent(intent)
//    }
//
//    private fun handleIntent(intent: Intent?) {
//        Log.i("handleIntent","well done")
//        val receivedValue = intent?.getIntExtra("index",0)
//        Toast.makeText(this, "$receivedValue", Toast.LENGTH_LONG).show()
//        if (intent?.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
//            val data = intent.data
//            if (data != null && data.scheme == "myAppWidget" && data.host == "selecttile") {
//                // The selectIntent was triggered from an item in the ListView
//                // Extract the data from the intent
//                val itemText = intent.getStringExtra("selectedItem")
//                if (itemText != null) {
//                    // Handle the tapped item here
//                    // For example, you can show a toast message with the selected item
//                    Toast.makeText(this, "Clicked: $itemText", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//}

//class MainActivity : FlutterActivity(), MethodChannel.MethodCallHandler {
//    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
//        GeneratedPluginRegistrant.registerWith(flutterEngine)
//
//        val channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, WidgetHelper.CHANNEL)
//        channel.setMethodCallHandler(this)
//        Log.i("setMethodCallHandler","called method channel")
//    }
//
//    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
//        when (call.method) {
//            "initialize" -> {
//                Log.i("onMethodCallLOG",(call.arguments == null).toString())
//                if (call.arguments == null) return
//                WidgetHelper.setHandle(this, call.arguments as Long)
//            }
//        }
//    }
//}

// MainActivity.kt
//import android.os.Bundle
//import io.flutter.embedding.android.FlutterActivity
//import io.flutter.embedding.engine.FlutterEngine

//class MainActivity : FlutterActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
//        super.configureFlutterEngine(flutterEngine)
//    }
//}

//class MainActivity : FlutterActivity() {
//
//    private val HOME_CHANNEL="com.example.imaginego_homewidget/home"
//    private val EVENT_CHANNEL="com.example.imaginego_homewidget/event"
//    lateinit var channel: MethodChannel
//    lateinit var eventChannel: EventChannel
//
//    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
//        GeneratedPluginRegistrant.registerWith(flutterEngine)
//
//        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, HOME_CHANNEL)
//        eventChannel= EventChannel(flutterEngine.dartExecutor.binaryMessenger,EVENT_CHANNEL)
//        eventChannel.setStreamHandler(MyStreamHandler(context))
//
//        channel.invokeMethod("getIndex",null)
//    }
//
//}
//
//class MyStreamHandler(private val  context: Context): EventChannel.StreamHandler{
//
//    private  var receiver: BroadcastReceiver?=null
//    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
//        if(events==null) return
//        receiver=initReceiver(events)
//        context.registerReceiver(receiver, IntentFilter(Intent.ACTION_DEFAULT))
//    }
//
//    override fun onCancel(arguments: Any?) {
//        TODO("Not yet implemented")
//        context.unregisterReceiver(receiver)
//        receiver=null
//    }
//
//    private fun initReceiver(events: EventSink): BroadcastReceiver{
//        return object : BroadcastReceiver(){
//            override fun onReceive(p0: Context?, p1: Intent?) {
//                events.success(100)
//            }
//        }
//    }
//}

class MainActivity : FlutterActivity(), MethodChannel.MethodCallHandler {
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)

        val channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, WidgetHelper.CHANNEL)
        channel.setMethodCallHandler(this)
    }



    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "initialize" -> {
                Log.i("CHANNEL INITIALIZED","sUCCESS")
                result.success("ok")
                Log.d("handle ARGYMENT",call.arguments.toString())
                if (call.arguments == null) {
                    Log.d("handle NOT PROVIFDED","GIVE THE HANDLE")
                    return
                }

                WidgetHelper.setHandle(this, call.arguments as Long)
            }
        }
    }
}