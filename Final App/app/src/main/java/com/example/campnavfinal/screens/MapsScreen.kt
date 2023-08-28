package com.example.campnavfinal.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.campnavfinal.R
import com.example.campnavfinal.ui.theme.Blue2

/*
// Vorschau für Einstellungen
@Preview
@Composable
fun MapsScreenPreview(){
    MapsScreen()
}

 */


//OptIn: verwendete Material3-Bibliothek experimentell, Änderungen an der API können auftreten
// ... (import-Anweisungen)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen(context: Context) {
    var startPoint by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Google Maps",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    )
                        },
                backgroundColor = Color(0xFF89CFF0),
                contentColor = Color.Black
            )
        },
        content = {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally // Zentrierung des Inhalts horizontal
            ) {


                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Iconbild",
                        modifier = Modifier
                            .size(200.dp)
                            .padding(30.dp)
                            .clip(CircleShape)
                            .shadow(10.dp)
                            .scale(1f, 1f),
                        contentScale = ContentScale.Crop  // Anpassung des Logos an den Kreis
                    )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Steinmüllerallee 1, 51643   Hauptgebäude, Mensa ",
                    style = TextStyle( fontSize = 16.sp ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                    )
                Text(
                    text = "Steinmüllerallee 4, 51643   Hochschulbibliothek ",
                    style = TextStyle( fontSize = 16.sp ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Steinmüllerallee 2, 51643   Gebäude B, FERCHAU ",
                    style = TextStyle( fontSize = 16.sp ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = startPoint,
                    onValueChange = { startPoint = it },
                    label = { Text("Startpunkt eingeben") }
                )

                TextField(
                    value = destination,
                    onValueChange = { destination = it },
                    label = { Text("Ziel eingeben") }
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
    )
}

fun startNavigationFromMain(startPoint: String, destination: String, context: Context) {
    val gmmIntentUri = Uri.parse("google.navigation:q=$startPoint&daddr=$destination&mode=w")
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
    MapsScreen(context = context)
}