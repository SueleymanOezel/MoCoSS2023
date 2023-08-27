package com.example.campnavfinal.database.home

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow



/**
 *  Definition des Data Access Object für Todo-Daten.
 *
 *   > @Query → SQL-Abfrage, um Todo-Elemente aus der Datenbank abzurufen, die den angegebenen Forderungen entsprechen
 *
 *  > @Insert → Operation zum Einfügen für Todo-Elemente in die Room-Datenbank
 *
 *  > @Delete → Operation zum Löschen eines Todo-Elements aus der Room-Datenbank
 *
 *  > @Update → Operation zum Aktualisieren eines Todo-Elements in der Room-Datenbank
 *
 */



@Dao
interface TodoDao {


    /**
     * Abruf aller Todo-Elemente aus der Room-Datenbank.
     * @return Ein Flow-Objekt, das eine Liste von TodoItem-Objekten enthält.
     */
    @Query("SELECT * FROM TodoItem")
    fun getAll(): Flow<List<TodoItem>>


    /**
     * Einfügen von neues 'TodoItem'-Objekten in die Room-Datenbank
     * @param todos Die TodoItem-Objekte, die eingefügt werden sollen.
     */
    @Insert
    fun insertAll(vararg todos: TodoItem)


    /**
     * Löschen eines bestimmten 'TodoItem'-Objekts aus der Room-Datenbank
     * @param todo Das zu löschende TodoItem-Objekt.
     */
    @Delete
    fun delete(todo: TodoItem)


    /**
     * Aktualisiert ein bestimmtes Todo-Element in der Room-Datenbank.
     * @param note Das zu aktualisierende TodoItem-Objekt.
     */
    @Update
    fun update(note: TodoItem)


    /**
     * Löscht alle Einträge in der Tabelle 'TodoItem' aus der Room-Datenbank
     */
    @Query("DELETE FROM TodoItem")
    fun nukeTable()


    /**
     * Asynchrone Suche nach 'TodoItem'-Objekten durch eines Suchbegriffs
     * @param query Der Suchbegriff, nach dem gesucht werden soll.
     * @return Eine Liste von TodoItem-Objekten, die den Suchkriterien entsprechen.
     */
    @Query("SELECT * FROM TodoItem WHERE title LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<TodoItem>
}