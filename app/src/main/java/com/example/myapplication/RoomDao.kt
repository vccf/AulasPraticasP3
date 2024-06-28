package com.example.myapplication

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface RoomDao {
    @Query("SELECT * FROM users")
    suspend fun getAll(): List<RoomEntity>

    //@Query("SELECT * FROM users WHERE uid IN (:userIds)")

    @Query("SELECT * FROM users WHERE first_name LIKE: first LIMIT 1")
    suspend fun findByFirstName (first: String): RoomEntity

    @Insert
    suspend fun insertAll (users: RoomEntity)

    @Update
    suspend fun update(User: RoomEntity)

    @Query("UPDATE users SET first_name=:first, last_name=:last WHERE uid= :id")
    suspend fun update(id:Int, first:String, last:String)
}