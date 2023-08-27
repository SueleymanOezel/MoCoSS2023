package com.example.campusnavigation.bottomnavigation

import androidx.compose.ui.graphics.vector.ImageVector



/**
 *  Hier wurde das Datenmodell für die Elemente der Navigationsleiste (Bottombar) definiert.
 *
 *  name → Name zur Anzeige
 *
 *  route → Zielroute, zu der das Element navigieren soll
 *
 *  icon → Visualisierung
 *
 */


data class BottomItem (

    val name: String,
    val route: String,
    val icon: ImageVector,

)
