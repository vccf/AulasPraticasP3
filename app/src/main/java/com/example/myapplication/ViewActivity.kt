package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
//import mvc.Model
import mvc.TaskController

class ViewActivity: AppCompatActivity(){

//    lateinit var controller: TaskController
//
//    override fun onCreate(savedInstanceState: Bundle?){
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.mvc)
//
//        controller=TaskController()
//
//        val completeButton=findViewById<Button>(R.id.button_complete)
//        completeButton.setOnClickListener{
//            controller!!.completeTask()
//            updateUI()
//        }
//
//    }
//
//    private fun updateUI(){
//        val taskTitle = findViewById<TextView>(R.id.text_task_title)
//        val taskCompleted=findViewById<CheckBox>(R.id.checkbox_task_completed)
//        val task: Model = controller.getTask()
//        taskTitle.setText(task.getModelTitle())
//        taskCompleted.isChecked=task.isModelCompleted()
//
//    }

}