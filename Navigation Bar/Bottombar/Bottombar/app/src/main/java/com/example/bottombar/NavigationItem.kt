package com.example.bottombar


/*
    jedes Unterobjekt (hier Home, Profil und Einstellungen) hat unterschiedliche Route, Icon und Titel
    werden in der Main in Bottom Navigation Bar verwendet zur Navigation innerhalb der App
 */
sealed class NavigationItem(var route: String, var icon: Int, var title: String)
{
    object Home : NavigationItem("home", R.drawable.ic_home, "Home")
    object Profil : NavigationItem("profil", R.drawable.ic_person, "Profile")
    object Einstellungen : NavigationItem("einstellungen", R.drawable.ic_settings, "Settings")
}

