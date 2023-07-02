package com.example.scan.screens.nav.room

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//OptIn: verwendete Material3-Bibliothek experimentell, Änderungen an der API können auftreten
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomNavScreen(context: Context) {
    val startPoint = "Cologne"
    val destination = "Berlin"

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = startPoint,
            onValueChange = { /* Keine Änderungen erlaubt */ },
            label = { Text("Startpunkt eingeben") },
            enabled = false // Textfeld deaktivieren
        )

        TextField(
            value = destination,
            onValueChange = { /* Keine Änderungen erlaubt */ },
            label = { Text("Ziel eingeben") },
            enabled = false // Textfeld deaktivieren
        )

        Button(
            onClick = {
                startNavigation(startPoint, destination, context)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Navigation starten")
        }
    }
}

fun startNavigation(startPoint: String, destination: String, context: Context) {
    val gmmIntentUri = Uri.parse("google.navigation:q=$startPoint&destination=$destination&mode=w")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")

    if (mapIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(mapIntent)
    } else {
        Toast.makeText(
            context,
            "Google Maps ist auf diesem Gerät nicht installiert.",
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Preview
@Composable
fun RoomNavScreenPreview() {
    val context = androidx.compose.ui.platform.LocalContext.current
    RoomNavScreen(context = context)
}
