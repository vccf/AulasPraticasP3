package com.example.myapplication.mvp

interface MainView {

    fun showUsers (userList: List<com.example.myapplication.mvp.User>)
    fun showError (message: String)
}