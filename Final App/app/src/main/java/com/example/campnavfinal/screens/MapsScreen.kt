package com.example.campnavfinal.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/*
// UI für Einstellungen
@Composable
fun MapsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Settings screen")
    }
}

// Vorschau für Einstellungen
@Preview
@Composable
fun MapsScreenPreview(){
    MapsScreen()
}

 */


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(this)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(activity: ComponentActivity) {
    val startPoint = "Cologne"
    val destination = "Berlin"

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = startPoint,
            onValueChange = { /* Keine Änderungen erlaubt */ },
            label = { androidx.compose.material3.Text("Startpunkt eingeben") },
            enabled = false // Textfeld deaktivieren
        )

        TextField(
            value = destination,
            onValueChange = { /* Keine Änderungen erlaubt */ },
            label = { androidx.compose.material3.Text("Ziel eingeben") },
            enabled = false // Textfeld deaktivieren
        )

        Button(
            onClick = {
                startNavigation(startPoint, destination, activity)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            androidx.compose.material3.Text("Navigation starten")
        }
    }
}


fun startNavigation(startPoint: String, destination: String, activity: ComponentActivity) {
    val gmmIntentUri = Uri.parse("google.navigation:q=$startPoint&destination=$destination&mode=w")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")

    if (mapIntent.resolveActivity(activity.packageManager) != null) {
        activity.startActivity(mapIntent)
    } else {
        Toast.makeText(
            activity,
            "Google Maps ist auf diesem Gerät nicht installiert.",
            Toast.LENGTH_SHORT
        ).show()
    }
}