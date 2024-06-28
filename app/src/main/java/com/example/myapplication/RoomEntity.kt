package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//import androidx.activity

@Entity(tableName="users")
data class RoomEntity (
    //@PrimaryKey
    @PrimaryKey(autoGenerate = true)
    //val uid: Int,
    val uid: Int = 0, // Primary constructor parameter
    @ColumnInfo(name = "first_name")
    val firstName: String = "",

    @ColumnInfo(name = "last_name")
    val lastName: String = ""
)

