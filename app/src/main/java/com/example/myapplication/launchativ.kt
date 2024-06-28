package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.delay
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class launchativ:  AppCompatActivity(), View.OnClickListener {

    lateinit var buttonthr1: Button
    lateinit var buttonthr2: Button
    lateinit var tvthr1: TextView
    lateinit var tvthr2: TextView
    lateinit var deferred2: Deferred<Unit>
    lateinit var button_stop_async: Button

    suspend fun counter(){
        delay(1000)
        tvthr1.text="thread1"
        //fourthAct_counter.text="thread1" criar textview
    }
    suspend fun counter2(){
        delay(1000)
        tvthr2.text="thread2"
        //fourthAct_counter2.text="thread2" criar textview
    }

    suspend fun counter4(): Int {
        delay(1000)
        return 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.launchativ)

        buttonthr1 = findViewById<Button>(R.id.buttonthr1) as Button
        buttonthr2 = findViewById<Button>(R.id.buttonthr2) as Button
        button_stop_async=findViewById<Button>(R.id.button_stop_async) as Button
        //var create_alarm = findViewbyId(R.id.create_alarm) as Button
        buttonthr1.setOnClickListener(this)
        buttonthr2.setOnClickListener(this)
        button_stop_async.setOnClickListener(this)
        tvthr1=findViewById<TextView>(R.id.tvthr1)
        tvthr2=findViewById<TextView>(R.id.tvthr2)
        //plus = findViewById<Button>(R.id.plus) as Button
        //plus.setOnClickListener(this)
        //preference_counter = findViewById<TextView>(R.id.pref_count)

        //R.id criar botao, setar id, linkar layout no oncreate, linkar botao a var,
        // setar escutador, implemebnntar click
        //lifecycleScope.launch {
            //IncrementPreferenceCounting()
            //ReadPreferenceCounter()
        //}
    }

    //no click do botao
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonthr1 -> {
                tvthr1.text=""
                tvthr2.text=""
//                lifecycleScope.launch() {
//                    counter()
//                    counter2()
//                    Toast.makeText(applicationContext, "launch sequentially", Toast.LENGTH_SHORT).show()
//                }
                lifecycleScope.launch() {
                    launch { counter()}
                    launch { counter2()}
                    Toast.makeText(applicationContext, "launch sequentially", Toast.LENGTH_SHORT).show()
                }

//                lifecycleScope.launch{
//                   val deferred1 = async { counter() }
//                    deferred2 = async { counter2() }
//                    deferred1.await()
//                    deferred2.await()
//                    Toast.makeText(applicationContext, "je suis tres fatigue", Toast.LENGTH_LONG).show()
//                }
            }
            R.id.button_stop_async -> {
                deferred2.cancel()
//                lifecycleScope.launch {
//                    val deferred3 = async { counter4()}
//                    val result = deferred3.await()
//                    Toast.makeText(applicationContext, "je suis tres tres fatigue" + result, Toast.LENGTH_LONG).show()
//                }
            }
        }

        //def 1 local, def 2 global
        //linear layout dentro layout
        //linearlayout orientação horizontal
    }
}