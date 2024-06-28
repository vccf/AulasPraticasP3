package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity: AppCompatActivity(), OnClickListener{

    lateinit var textview: TextView
    lateinit var button3: Button

    override fun onCreate (saveInstanceState: Bundle?){
        super.onCreate(saveInstanceState)
        setContentView(R.layout.second_activity)
        textview= findViewById(R.id.textview1)
        textview.setText(intent.extras?.getString("data"))
        button3=findViewById<Button>(R.id.button3) as Button
        button3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        //TODO("Not yet implemented")
        when (v?.id) {
            R.id.button3 -> {
                var intent = intent.putExtra("returning", 123)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }
}