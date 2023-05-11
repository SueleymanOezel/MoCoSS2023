package com.example.bottombar

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract.Profile
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity


// Eine Funktion, die die nächste Activity oder den nächsten Screen startet und die Eingabe als Extra übergibt
fun startNextActivity(input: String, context: Context) {
    val intent = Intent(context, Profile::class.java)
    intent.putExtra("input", input)
    startActivity(context, intent, null)
}


@Preview
@Composable
fun HomeScreen() {
    // Ein Zustand für den Eingaberaum, der die eingegebene Zahl speichert
    val eingabeRaum = remember { mutableStateOf("") }
    val context = LocalContext.current

    // Eine Funktion, die überprüft, ob die Eingabe gültig ist (eine vierstellige Zahl)
    fun isValidInput(input: String): Boolean {
        return input.length == 4 && input.all { it.isDigit() }
    }
        // Ein Composable Layout für den HomeScreen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF8aa3b6)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ein Kreisfeld für das Iconbild
            Image(
                painter = painterResource(id = R.drawable.campus_icon),
                contentDescription = "Iconbild",
                modifier = Modifier.size(250.dp).padding(30.dp)
            )

            // Ein Textfeld für den Appnamen
            Text(
                text = "CampusNav",
                modifier = Modifier.padding(10.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black// Babyblau: #89CFF0
                )
            )


            // Ein Textfeld für die Wohinabfrage
            Text(
                text = "Wo willst du hin?",
                modifier = Modifier.padding(10.dp)
            )

            // Ein Eingabefeld für den Eingaberaum mit einer Tastatur für Zahlen
            TextField(
                value = eingabeRaum.value,
                onValueChange = { eingabeRaum.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(10.dp).width(150.dp)
            )

            // Ein Button für die Bestätigung des Raums mit einer Validierung der Eingabe
            Button(
                onClick = {
                    if (isValidInput(eingabeRaum.value)) {
                        startNextActivity(eingabeRaum.value, context)
                    }
                }
            ) {
                Text(text = "Bestätigung des Raums")
            }

        }
    }

