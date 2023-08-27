package com.example.campusnavigation

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
//import com.example.campusnavigation.database.home.AppDatabase
import com.example.campusnavigation.database.home.TodoDatabase
import com.example.scan.database.room.RoomDao

class MyApplication : Application() {
    val todoDb by lazy {
        Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java, "todo-database"
        ).build()
    }

    val roomDb by lazy {
        Room.databaseBuilder(
            applicationContext,
            RoomDatabase::class.java, "room-database"
        ).build()
    }

    val todoDao by lazy { todoDb.todoDao() }
    // val roomDao by lazy { roomDb.roomDao() }
}


/* Application, die die Raum-DB und die ToDo-DB enthält
class MyApplication: Application() {
    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()
    }

    val todoDao by lazy { db.todoDao() }
    val roomDao by lazy { db.roomDao() }
}

 */


/* AKTUELL
class MyApplication: Application() {
    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java, "todo-database"
        ).build()
    }
    val todoDao by lazy { db.todoDao() }

    // val roomDao by lazy { db.roomDao() }

    // val userDao by lazy { db. }


    }

 */



