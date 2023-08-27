package com.example.campusnavigation.database.profile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)
}



