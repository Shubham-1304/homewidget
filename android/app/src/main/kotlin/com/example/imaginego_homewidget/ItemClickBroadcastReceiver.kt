package com.example.imaginego_homewidget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class ItemClickBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.i("onReceive","hurray")
        val selectedItem = intent.getStringExtra("selectedItem")
        if (selectedItem != null) {
            // Handle the clicked item here
            // For example, you can show a toast message with the selected item
            Toast.makeText(context, "Clicked: $selectedItem", Toast.LENGTH_SHORT).show()
        }
    }
}
