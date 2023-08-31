package com.example.campnavfinal.database.home

import androidx.room.Database
import androidx.room.RoomDatabase



/**
 * Eine abstrakte Klasse, die die Todo-Datenbank repräsentiert.
 * Erbt von der RoomDatabase-Klasse.
 */
@Database(entities = [TodoItem::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    /**
     * Eine abstrakte Funktion, um das Data Access Object (DAO) für die TodoItem-Entität zurückzugeben.
     * @return Das TodoDao-Objekt für den Zugriff auf die TodoItem-Tabelle.
     */
    abstract fun todoDao(): TodoDao
}




/* Datenbank, die die Räume und die ToDo´s enthält

@Database(entities = [TodoItem::class, RoomEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun roomDao(): RoomDao
}

 */
