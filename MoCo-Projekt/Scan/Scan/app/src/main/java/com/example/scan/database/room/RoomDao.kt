package com.example.scan.database.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// Data Access Object für Raumdaten
@Dao
interface RoomDao {
    @Query("SELECT * FROM rooms WHERE roomNumber = :roomNumber")
    fun getRoomByNumber(roomNumber: String): RoomEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoom(room: RoomEntity)

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