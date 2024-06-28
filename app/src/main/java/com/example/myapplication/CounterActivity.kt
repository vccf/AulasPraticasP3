package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class CounterActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var button_count: Button
    lateinit var view_count: TextView
    var incr: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        button_count=findViewById<Button>(R.id.botao_contador) as Button
        view_count=findViewById<Button>(R.id.text_contador) as TextView
            //var create_alarm = findViewbyId(R.id.create_alarm) as Button
        button_count.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        //TODO("Not yet implemented")
        when (v?.id) {
            R.id.botao_contador->{
                incr++
                view_count.setText(incr.toString())
            }

        }
    }
}