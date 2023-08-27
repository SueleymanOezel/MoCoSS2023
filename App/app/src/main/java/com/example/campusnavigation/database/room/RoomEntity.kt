package com.example.scan.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


// Entität für Räume
@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey val roomNumber: String,
    @ColumnInfo val roomText: String
)




