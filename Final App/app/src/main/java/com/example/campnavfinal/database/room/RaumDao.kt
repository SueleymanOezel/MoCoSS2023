package com.example.campnavfinal.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Data Access Object für Raumdaten
@Dao
interface RaumDao {
    @Query("SELECT * FROM raum WHERE roomNumber = :roomNumber")
    fun getRoomByNumber(roomNumber: Int): LiveData<RaumEntity>

    //Methode, die einen Raum in die Tabelle "room" einfügt oder ersetzt, wenn er bereits existiert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(raum: RaumEntity)

    // Methode, die mehrere Räume in die Tabelle "raum" einfügt oder ersetzt, wenn sie bereits existieren
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRooms(raum: List<RaumEntity>)

    //Methode, die einen Raum aus der Tabelle "room" löscht
    @Delete
    suspend fun deleteRoom(raum: RaumEntity)

/*
    @Update
    suspend fun updateRoom(rooms: Room)

    @Delete
    suspend fun deleteRoom(rooms: Room)

 */
}



/*

@Dao
interface RoomDao {
   /*
   @Query("SELECT * FROM RoomEntity WHERE number = :number")
    fun getRoomByNumber(number: String): Room?

    @Insert
    suspend fun insertRoom(room: Room)

    @Update
    suspend fun updateRoom(room: Room)

    @Delete
    suspend fun deleteRoom(room: Room)

    */
}

 */