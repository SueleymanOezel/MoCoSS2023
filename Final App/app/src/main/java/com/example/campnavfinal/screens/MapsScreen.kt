package com.example.campnavfinal.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
<<<<<<< HEAD:Final App/app/src/main/java/com/example/campnavfinal/screens/MapsScreen.kt
=======
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
>>>>>>> origin/main:Final App/app/src/main/java/com/example/campnavfinal/screens/SettingsScreen.kt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
<<<<<<< HEAD:Final App/app/src/main/java/com/example/campnavfinal/screens/MapsScreen.kt
=======
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
>>>>>>> origin/main:Final App/app/src/main/java/com/example/campnavfinal/screens/SettingsScreen.kt
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.campnavfinal.R
import com.example.campnavfinal.ui.theme.Blue2


<<<<<<< HEAD:Final App/app/src/main/java/com/example/campnavfinal/screens/MapsScreen.kt
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
=======
//OptIn: verwendete Material3-Bibliothek experimentell, Änderungen an der API können auftreten
// ... (import-Anweisungen)
>>>>>>> origin/main:Final App/app/src/main/java/com/example/campnavfinal/screens/SettingsScreen.kt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(context: Context) {
    val startPoint = "Cologne"
    val destination = "Berlin"

    Surface(color = Color.Gray) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally // Zentrierung des Inhalts horizontal
            ) {
                Text(
                    text = "Room Nav Screen",
                    style = MaterialTheme.typography.h1,
                    fontSize = 32.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(225.dp)
                )

                TextField(
                    value = startPoint,
                    onValueChange = { /* Keine Änderungen erlaubt */ },
                    label = { Text("Startpunkt eingeben") },
                    enabled = false
                )

                TextField(
                    value = destination,
                    onValueChange = { /* Keine Änderungen erlaubt */ },
                    label = { Text("Ziel eingeben") },
                    enabled = false
                )

                Button(
                    onClick = {
                        startNavigationFromMain(startPoint, destination, context)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .background(Blue2), // Hier setzen wir die Hintergrundfarbe
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White) // Textfarbe der Schaltfläche
                ) {
                    Text("Navigation starten")
                }
            }
        }
    }
}

// Restlicher Code bleibt unverändert
fun startNavigationFromMain(startPoint: String, destination: String, context: Context) {
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
fun MainScreenPreview() {
    val context = androidx.compose.ui.platform.LocalContext.current
    MainScreen(context = context)
}