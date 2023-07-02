package com.example.scan

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
//import com.example.scan.database.home.AppDatabase
import com.example.scan.database.home.TodoDatabase
import com.example.scan.database.room.RaumDatabase
import com.example.scan.database.room.RaumDao

class MyApplication : Application() {
    val todoDb by lazy {
        Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java, "todo-database"
        ).build()
    }

    val raumDb by lazy {
        Room.databaseBuilder(
            applicationContext,
            RaumDatabase::class.java, "room-database"
        ).build()
    }
    val raumDao by lazy { raumDb.raumDao()}


    val todoDao by lazy { todoDb.todoDao() }

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



