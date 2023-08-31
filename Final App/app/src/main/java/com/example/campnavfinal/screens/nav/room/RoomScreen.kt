package com.example.campnavfinal.screens.nav.room

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.campnavfinal.R




fun startNextActivity(roomNumber: String, navController: NavHostController, roomBackgroundResId: Int) {
    val route = "room/$roomNumber/$roomBackgroundResId"
    navController.navigate(route)
}



// Funktion für den Bildschirm, zur Eingabe der Raum-Nummern
@Composable
fun RoomScreen(navController: NavHostController) {

    val context = LocalContext.current // Aktuellen Kontext abrufen
    val eingabeRaum = remember { mutableStateOf("") }

    val roomBackground = R.drawable.logo2

    fun isValidInput(input: String): Boolean {
        return input.length == 4 && input.all { it.isDigit() }
    }


    // Button-Klick-Handler
    val onButtonClick: () -> Unit = {
        if (isValidInput(eingabeRaum.value)) {
            startNextActivity(eingabeRaum.value, navController, roomBackground)
        } else {
            Toast.makeText(context, "Ungültige Eingabe", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Ein Hintergrundbild für den HomeScreen
        Image(
            painter = painterResource(id = R.drawable.logo2),
            contentDescription = "Hintergrundbild",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Ein Column-Layout für die restlichen Elemente
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0x81000000)), // Halbtransparentes Schwarz
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Ein Kreisfeld für das Iconbild mit einem Schatten
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Iconbild",
                modifier = Modifier
                    .size(230.dp)
                    .padding(30.dp)
                    .clip(CircleShape)
                    .shadow(10.dp)
                    .scale(1f, 1f),
                contentScale = ContentScale.Crop  // Anpassung des Logos an den Kreis
            )

            // Ein Textfeld für den Appnamen
            Text(
                text = "CampusNav",
                modifier = Modifier.padding(20.dp),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif
                )
            )


            // Ein Textfeld für die Wohinabfrage
            Text(
                text = "Wo willst du hin?",
                modifier = Modifier.padding(10.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif
                )
            )

            // Ein Eingabefeld für den Eingaberaum mit einer Tastatur für Zahlen und einem Rahmen
            TextField(
                value = eingabeRaum.value,
                onValueChange = { eingabeRaum.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(10.dp)
                    .width(150.dp)
                    .border(2.dp,
                        Color.White,
                        RoundedCornerShape(10.dp)
                    ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.White
                )
            )

            // Ein Button für die Bestätigung des Raums mit einer Validierung der Eingabe
            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Bestätigung des Raums")
            }
        }
    }
}


    // Vorschau für den Bildschirm der Raum-Nummer-Eingabe
@Preview
@Composable
fun RoomScreenPreview() {
    RoomScreen(navController = rememberNavController())
}



