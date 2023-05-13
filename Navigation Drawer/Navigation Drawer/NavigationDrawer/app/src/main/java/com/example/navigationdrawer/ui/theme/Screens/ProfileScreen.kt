package com.example.navigationdrawer.ui.theme.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.navigationdrawer.R

// Eine Annotation für die Composable-Preview-Funktion

@Preview(showBackground = true)
@Composable

fun ProfileScreenPreview() {
    // Ein NavController zum Navigieren zwischen den Screens

    val navController = rememberNavController()
    // Der Profile-Screen als Composable

    ProfileScreen(navController = navController)
}


// Eine Datenklasse für die User-Informationen

data class User(
    var firstName: String,
    var lastName: String,
    var email: String,
    var birthday: String,
    var password: String,
    var profilePicture: Int // Die ID des Bildes aus den Ressourcen

)

// Eine Funktion, die eine Liste von Usern zurückgibt (hier nur ein Beispiel-User)

fun getUsers(): List<User> {
    return listOf(
        User(
            firstName = "Max",
            lastName = "Mustermann",
            email = "max@mustermann.de",
            birthday = "01.01.2000",
            password = "123456",
            profilePicture = R.drawable.ic_profile // Das Bild muss in den drawable Ordner gelegt werden

        )
    )
}

// Eine Funktion, die den Profile-Screen als Composable definiert und das CampusNav Design verwendet

@Composable

fun ProfileScreen(navController: NavController) {
    // Ein Zustand für den aktuellen User (hier nur der erste aus der Liste)

    val currentUser = remember { mutableStateOf(getUsers().first()) }
    // Ein Zustand für den Bearbeitungsmodus (true oder false)

    val editMode = remember { mutableStateOf(false) }
    // Ein Zustand für die Fehlermeldung (falls das Passwort falsch ist)

    val errorMessage = remember { mutableStateOf("") }

    // Ein Scaffold mit einer TopBar und einem Content-Bereich

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Profil") },
                actions = {
                    // Ein Button zum Wechseln des Bearbeitungsmodus

                    IconButton(onClick = { editMode.value = !editMode.value }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit), // Das Icon muss in den drawable Ordner gelegt werden

                            contentDescription = "Edit"
                        )
                    }
                }
            )
        },
        content = { padding -> // Hier habe ich den padding Parameter hinzugefügt und verwendet

            // Ein Box-Layout für den Hintergrund und die restlichen Elemente

            Box(modifier = Modifier.fillMaxSize()) {
                // Ein Hintergrundbild für den Profile-Screen

                Image(
                    painter = painterResource(id = R.drawable.campus_icon),
                    contentDescription = "Hintergrundbild",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop

                )
                // Ein Column-Layout mit dem Profilbild und den Daten des Users
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(color = Color(0x80000000)), // Halbtransparentes Schwarz
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Das Profilbild als Image-Composable mit einem Klick-Listener und einem Schatten
                    Image(
                        painter = painterResource(id = currentUser.value.profilePicture),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(200.dp).clickable {
                            // Hier kann die Logik zum Ändern des Profilbilds implementiert werden (z.B. mit einer Camera- oder Gallery-Aktivität)
                        }.clip(CircleShape).shadow(10.dp)
                    )
                    // Die Daten des Users als Text-Composables mit einem Modifier zum Anpassen der Breite und Höhe und einem Stil zum Anpassen der Farbe und Schriftart
                    Text(
                        text = "Vorname: ${currentUser.value.firstName}",
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                    Text(
                        text = "Nachname: ${currentUser.value.lastName}",
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                    Text(
                        text = "E-Mail: ${currentUser.value.email}",
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                    Text(
                        text = "Geburtstag: ${currentUser.value.birthday}",
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                    Text(
                        text = "Passwort: ${currentUser.value.password}",
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White,
                            fontFamily = FontFamily.SansSerif
                        )
                    )

                    // Wenn der Bearbeitungsmodus aktiv ist, werden zusätzlich ein TextField für das neue Passwort und ein Button zum Speichern angezeigt
                    if (editMode.value) {
                        TextField(
                            value = currentUser.value.password,
                            onValueChange = {
                                currentUser.value.password =
                                    it
                            },
                            label =
                            {
                                Text(
                                    text =
                                    "Neues Passwort"
                                )
                            },
                            modifier =
                            Modifier.fillMaxWidth().height(50.dp).border(
                                2.dp,
                                Color.White,
                                RoundedCornerShape(10.dp)
                            ),
                            colors =
                            TextFieldDefaults.textFieldColors(
                                backgroundColor =
                                Color.Transparent,
                                textColor =
                                Color.White,
                                cursorColor =
                                Color.White
                            )
                        )
                        Button(
                            onClick =
                            { // Hier kann die Logik zum Speichern des neuen Passworts implementiert werden (z.B. mit einer Datenbank- oder SharedPreferences-Aktivität) // Wenn das Speichern erfolgreich ist, wird der Bearbeitungsmodus beendet und die Fehlermeldung geleert // Wenn das Speichern fehlschlägt, wird eine Fehlermeldung angezeigt editMode.value =
                                false
                                ""
                            },
                            modifier =
                            Modifier.fillMaxWidth().height(50.dp),
                            colors =
                            ButtonDefaults.buttonColors(
                                backgroundColor =
                                Color.Gray,
                                contentColor =
                                Color.White
                            )
                        ) {
                            Text(text = "Speichern")
                        }

                        // Wenn eine Fehlermeldung vorhanden ist, wird sie als Text-Composable mit roter Farbe und fetter Schrift angezeigt
                        if (errorMessage.value.isNotEmpty()) {
                            Text(
                                text = errorMessage.value,
                                color = MaterialTheme.colors.error,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth().height(50.dp)
                            )

                        }
                    }
                }
            }
        }
    )
}