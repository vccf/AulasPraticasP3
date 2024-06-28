package com.example.myapplication.mvp

class Model {

    val usersInDB: MutableList<User> = mutableListOf(
        User("Alice", 25),
        User("Bob", 30),
        User("Carol", 28)
    )

    fun getUsers():List<User>{
        return usersInDB
    }

    fun addUsers(user: User){
        usersInDB.add(user)
    }
}
