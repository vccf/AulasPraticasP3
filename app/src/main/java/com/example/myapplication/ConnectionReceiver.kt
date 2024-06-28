package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ConnectionReceiver: BroadcastReceiver() {
    val LOG_MSG= "main_activity"
    override fun onReceive(p0: Context?, p1: Intent?) {
        val isAirplaneModeEnabled = p1?.getBooleanExtra("state", false) ?: return
        Toast.makeText(p0, "is airplane mode enabled: " + isAirplaneModeEnabled, Toast.LENGTH_SHORT).show()
    }
}