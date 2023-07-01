package com.example.scan.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


// Entität für Räume
@Entity(tableName = "Raum")
data class RaumEntity(
    @PrimaryKey val roomNumber: Int,
    @ColumnInfo val roomName: String,
    @ColumnInfo val floor: String,
    @ColumnInfo val buildingNumber: Int,
    @ColumnInfo val buildingName: String,
    @ColumnInfo val roomText: String
)




