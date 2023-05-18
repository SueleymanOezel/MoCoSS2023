package com.example.navigationdrawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MenueItem(
    var route: String,
    var icon: ImageVector,
    val title: String
){
    object Home: MenueItem("home", Icons.Default.Home, "Home")
    object Navigation: MenueItem("navigation", Icons.Default.LocationOn, "Navigation"){
        object Room : MenueItem("room", Icons.Default.Favorite, "Room")
        object Scanner : MenueItem("scanner", Icons.Default.Info, "Scanner")
    }
    object Profile: MenueItem("profile", Icons.Default.Person, "Profile")
    object Settings: MenueItem("settings", Icons.Default.Settings, "Settings")
    object Help: MenueItem("help", Icons.Default.Info, "Help" )

}


/*
data class MenueItem(

    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector
)*/
