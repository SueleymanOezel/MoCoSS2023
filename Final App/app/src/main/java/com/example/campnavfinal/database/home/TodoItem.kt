package com.example.campnavfinal.database.home

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entität für ToDo´s
@Entity
data class TodoItem(
    @PrimaryKey val id: Int,
    @ColumnInfo val title: String,
    @ColumnInfo var urgent: Boolean = false
)