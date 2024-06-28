package com.example.myapplication

import androidx.room.Database
import androidx.room.RoomDatabase

//@Database (entities=)
abstract class RoomDB: RoomDatabase() {
    abstract fun userDao(): RoomDao
}