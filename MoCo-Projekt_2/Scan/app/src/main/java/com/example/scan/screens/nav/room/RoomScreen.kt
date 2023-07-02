package com.example.scan.screens.nav.room

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.scan.R
import com.example.scan.mvvm.MainViewModel

fun startNextActivity(roomNumber: String, navController: NavHostController, roomBackgroundResId: Int) {
    val route = "room/$roomNumber/$roomBackgroundResId"
    navController.navigate(route)
}



// Funktion für den Bildschirm, zur Eingabe der Raum-Nummern
@Composable
fun RoomScreen(navController: NavHostController) {
    val eingabeRaum = remember { mutableStateOf("") }

    val roomBackground = R.drawable.logo

    fun isValidInput(input: String): Boolean {
        return input.length == 4 && input.all { it.isDigit() }
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Ein Hintergrundbild für den HomeScreen
        Image(
            painter = painterResource(id = R.drawable.logo),
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
                onClick = {
                    if (isValidInput(eingabeRaum.value)) {
                        startNextActivity(eingabeRaum.value, navController, roomBackground)
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


    // Vorschau für den Bildschirm der Raum-Nummer-Eingabe
@Preview
@Composable
fun RoomScreenPreview() {
    RoomScreen(navController = rememberNavController())
}



