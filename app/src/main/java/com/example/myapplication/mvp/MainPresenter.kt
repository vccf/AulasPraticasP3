package com.example.myapplication.mvp

import com.example.myapplication.mvp.User
import com.example.myapplication.mvp.Model

class MainPresenter (private val view: MainView): PresenterInterface {

    private val model: Model = Model()

    fun getUsers() {
        view.showUsers(model.getUsers())
    }

    override fun addUser(user: User) {
        model.addUsers(user)
    }
}