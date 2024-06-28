package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class customBroadcastReceiver: BroadcastReceiver() {
    val LOG_MSG= "main_activity"
    override fun onReceive(p0: Context?, p1: Intent?) {
        val isAirplaneModeEnabled = p1?.getBooleanExtra("state", false) ?: return
        Toast.makeText(p0, " ne je sais pas que je peux faire " + isAirplaneModeEnabled, Toast.LENGTH_SHORT).show()
        Log.d("testando", "testando 2")
    }
}