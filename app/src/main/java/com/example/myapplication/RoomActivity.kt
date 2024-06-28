package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RoomActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var button_save: Button
    lateinit var button_update: Button
    lateinit var button_delete: Button
    lateinit var id_user: TextView
    val LOG_MSG:String="2"
    //lateinit var
    val dataStore: DataStore<Preferences> by preferencesDataStore(name="settings")
    val EXAMPLE_COUNTER= intPreferencesKey("example_counter")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        button_save=findViewById<Button>(R.id.button1) as Button
        button_update=findViewById<Button>(R.id.button2) as Button
        //var create_alarm = findViewbyId(R.id.create_alarm) as Button
        button_delete
        //button2.setOnClickListener(this)
        //plus=findViewById<Button>(R.id.plus) as Button
        //plus.setOnClickListener(this)
        //preference_counter=findViewById<TextView>(R.id.pref_count)

        //R.id criar botao, setar id, linkar layout no oncreate, linkar botao a var,
        // setar escutador, implemebnntar click
        lifecycleScope.launch {
            //IncrementPreferenceCounting()
            //ReadPreferenceCounter()
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}