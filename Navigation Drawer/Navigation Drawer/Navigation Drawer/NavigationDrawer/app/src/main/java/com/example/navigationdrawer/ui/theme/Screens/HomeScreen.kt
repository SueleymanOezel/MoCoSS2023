package com.example.navigationdrawer.ui.theme.Screens

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract.Profile
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.core.content.ContextCompat.startActivity
import com.example.navigationdrawer.R
import com.example.navigationdrawer.mvvm.MainViewModel


// Eine Funktion, die die nächste Activity oder den nächsten Screen startet und die Eingabe als Extra übergibt
fun startNextActivity(input: String, context: Context) {
    val intent = Intent(context, Profile::class.java)
    intent.putExtra("input", input)
    startActivity(context, intent, null)
}


//@Preview
@Composable
fun HomeScreen(viewModel: MainViewModel) {

    // Beobachte das Profil des aktuellen Users aus dem ViewModel
    val profile by viewModel.profile.observeAsState()

    // Ein Zustand für den Eingaberaum, der die eingegebene Zahl speichert
    val eingabeRaum = remember { mutableStateOf("") }
    val context = LocalContext.current

    // Eine Funktion, die überprüft, ob die Eingabe gültig ist (eine vierstellige Zahl)
    fun isValidInput(input: String): Boolean {
        return input.length == 4 && input.all { it.isDigit() }
    }
    // Ein Composable Layout für den HomeScreen
    Box( // Ein Box-Layout für den Hintergrund
        modifier = Modifier.fillMaxSize()
    ) {
        // Ein Hintergrundbild für den HomeScreen
        Image(
            painter = painterResource(id = R.drawable.campusnav_icon),
            contentDescription = "Hintergrundbild",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Ein Column-Layout für die restlichen Elemente
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0x80000000)), // Halbtransparentes Schwarz
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ein Kreisfeld für das Iconbild mit einem Schatten
            Image(
                painter = painterResource(id = R.drawable.campusnav_icon),
                contentDescription = "Iconbild",
                modifier = Modifier.size(250.dp).padding(30.dp).clip(CircleShape).shadow(10.dp)
            )

            // Ein Textfeld für den Appnamen
            Text(
                text = "CampusNav",
                modifier = Modifier.padding(10.dp),
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
                modifier = Modifier.padding(10.dp).width(150.dp).border(2.dp, Color.White, RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.White
                )
            )

            // Ein Button für die Bestätigung des Raums mit einer Validierung der Eingabe
            Button(
                onClick = {
                    if (isValidInput(eingabeRaum.value)) {
                        startNextActivity(eingabeRaum.value, context)
                    }
                },
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



