package com.example.myapplication.mvp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainViewDraft
import com.example.myapplication.R

class mvpActivity: AppCompatActivity(), MainView {
    private lateinit var presenter: MainPresenter;
    private lateinit var listView: ListView
    private lateinit var adduser: Button
    private lateinit var getusers: Button
    private lateinit var adapter: ArrayAdapter<User>
    private val taskList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvp_activity)

        adduser=findViewById(R.id.adduser)
        adduser.setOnClickListener(View.OnClickListener {presenter.addUser(User(name="blah", age=0))})
        getusers=findViewById(R.id.getusers)
        getusers.setOnClickListener(View.OnClickListener {presenter.getUsers()})

        listView=findViewById(R.id.list)
        adapter=
            ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, taskList)
        listView.adapter=adapter
        presenter=MainPresenter(this)
    }

    override fun showUsers(userList:List<User>){
        for (user in userList){
            adapter.add(user)
        }
        Toast.makeText(this@mvpActivity, "got users", Toast.LENGTH_LONG).show()
        adapter.notifyDataSetChanged()
    }

    override fun showError(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}