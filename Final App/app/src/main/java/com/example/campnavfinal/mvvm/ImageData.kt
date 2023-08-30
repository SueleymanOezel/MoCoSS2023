package com.example.campnavfinal.mvvm

// Datenklasse mit den Variablen für die Bilder
data class ImageData(
    val imageRes: Int,
    val caption: String,
    val title: String,
    var isFavorite: Boolean = false
)