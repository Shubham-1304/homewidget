package com.example.imaginego_homewidget

//import android.widget.GridView
//import android.content.SharedPreferences
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.loader.FlutterLoader
import io.flutter.plugin.common.MethodChannel
import io.flutter.view.FlutterCallbackInformation

const val EXTRA_ITEM = "com.example.imaginego_homewidget.EXTRA_ITEM"
const val TOAST_ACTION = "com.example.imaginego_homewidget.TOAST_ACTION"


class StackWidgetProvider : AppWidgetProvider(),MethodChannel.Result {

    private val TAG = this::class.java.simpleName

    companion object {
        private var channel: MethodChannel? = null;
    }

    private lateinit var context: Context

    private var flutterLoader= FlutterLoader()


    // Called when the BroadcastReceiver receives an Intent broadcast.
    // Checks whether the intent's action is TOAST_ACTION. If it is, the
    // widget displays a Toast message for the current item.
    override fun onReceive(context: Context, intent: Intent) {
        val mgr: AppWidgetManager = AppWidgetManager.getInstance(context)
        if (intent.action == TOAST_ACTION) {
            val appWidgetId: Int = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )

            // Invoke the Dart method using the MethodChannel
            // EXTRA_ITEM represents a custom value provided by the Intent
            // passed to the setOnClickFillInIntent() method to indicate the
            // position of the clicked item. See StackRemoteViewsFactory in
            // Set the fill-in Intent for details.
            val viewIndex: Int = intent.getIntExtra(EXTRA_ITEM, 0)
            Log.i("INDEX TAPPED",viewIndex.toString())
            Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
            Log.i("channelisnull",(channel==null).toString())
            val result=channel?.invokeMethod("received", appWidgetId, this)
            Log.i("RESULTRECEIVED",result.toString())
            val dataToPass = "$viewIndex"


//            val intent = Intent(context, MyFlutterActivity::class.java)
//            intent.putExtra("dataToPass", dataToPass)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(intent)

        }
        super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        this.context = context
        initializeFlutter()
        // Update each of the widgets with the remote adapter.
        appWidgetIds.forEach { appWidgetId ->

            // Sets up the intent that points to the StackViewService that
            // provides the views for this collection.
            val intent = Intent(context, WidgetService::class.java).apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                // When intents are compared, the extras are ignored, so embed
                // the extra sinto the data so that the extras are not ignored.
                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
            }
            val rv = RemoteViews(context.packageName, R.layout.widget_layout).apply {
                setRemoteAdapter(R.id.listview, intent)

                // The empty view is displayed when the collection has no items.
                // It must be a sibling of the collection view.
            }

            // This section makes it possible for items to have individualized
            // behavior. It does this by setting up a pending intent template.
            // Individuals items of a collection can't set up their own pending
            // intents. Instead, the collection as a whole sets up a pending
            // intent template, and the individual items set a fillInIntent
            // to create unique behavior on an item-by-item basis.
            val toastPendingIntent: PendingIntent = Intent(
                context,
                StackWidgetProvider::class.java
            ).run {
                // Set the action for the intent.
                // When the user touches a particular view, it has the effect of
                // broadcasting TOAST_ACTION.
                action = TOAST_ACTION
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))

                PendingIntent.getBroadcast(context, 0, this, PendingIntent.FLAG_UPDATE_CURRENT)
            }
            rv.setPendingIntentTemplate(R.id.listview, toastPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, rv)
            Log.i("channelisnullupdate",(channel==null).toString())
            Log.i("channelisnullupdate",channel.toString())
            val result=channel?.invokeMethod("update", appWidgetId, this)
            Log.i("channelisnullupdate",result.toString())

        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
    private fun initializeFlutter() {
        if (channel == null) {
            flutterLoader.startInitialization(context)
            flutterLoader.ensureInitializationComplete(context, arrayOf())

            val handle = WidgetHelper.getRawHandle(context)
            Log.d("handleARGYMENTreceived",handle.toString())
            if (handle == WidgetHelper.NO_HANDLE) {
                Log.w(TAG, "Couldn't update widget because there is no handle stored!")
                return
            }

            val callbackInfo = FlutterCallbackInformation.lookupCallbackInformation(handle)
            Log.i("callbackhfeh",callbackInfo.toString())
            // Instantiate a FlutterEngine.
            val engine = FlutterEngine(context.applicationContext)
            val callback = DartExecutor.DartCallback(context.assets, flutterLoader.findAppBundlePath(), callbackInfo)
            Log.i("callbackhfeh",callback.toString())
            engine.dartExecutor.executeDartCallback(callback)

            channel = MethodChannel(engine.dartExecutor.binaryMessenger, WidgetHelper.CHANNEL)
            Log.i("CHANNELINITIALIZEDS","$channel okkkk")

        }
    }

    override fun success(result: Any?) {
        Log.d("TAGSucess", "success $result")

        val args = result as HashMap<*, *>
        val id = args["id"] as Int
        val value = args["value"] as Int
        Log.d("return values","$id $value")

//        updateWidget("onDart $value", id, context)
    }

    override fun notImplemented() {
        Log.d("TAGnotimplemented", "notImplemented")
    }

    override fun error(errorCode: String, errorMessage: String?, errorDetails: Any?) {
        Log.d("TAGonerror", "onError $errorCode")
        TODO("Not yet implemented")
    }

    override fun onDisabled(context: Context?) {
        Log.d("TAGdisabled", "onDisabled")
        super.onDisabled(context)
//        channel = null
    }

    class MyFlutterActivity : FlutterActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // Receive data sent from AppWidgetProvider and call Dart function
            val dataToPass = intent.getStringExtra("dataToPass")
            val methodChannel = MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger, CHANNEL)
            methodChannel.invokeMethod(METHOD_NAME, dataToPass)
        }

        companion object {
            private const val CHANNEL = "com.example.myChannel"
            private const val METHOD_NAME = "myMethodFromAndroid"
        }
    }
}


