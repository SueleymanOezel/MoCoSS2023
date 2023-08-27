package com.example.campusnavigation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scan.R


data class User(
    var firstName: String,
    var lastName: String,
    var email: String,
    var profilePicture: Int,
    // var favoriteImages: List<ImageData> // hiermit sollen später die als Favorit markierten Bilder gespeichert werden
)

fun getUsers(): List<User> {
    return listOf(
        User(
            firstName = "",
            lastName = "",
            email = "",
            profilePicture = R.drawable.ic_person
            // favoriteImages = emptyList()
        )
    )
}


@Composable
fun ProfileScreen() {

    val currentUser = remember { mutableStateOf(getUsers().first()) }
    val editMode = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    val firstNameState = remember { mutableStateOf(currentUser.value.firstName) }
    val lastNameState = remember { mutableStateOf(currentUser.value.lastName) }
    val emailState = remember { mutableStateOf(currentUser.value.email) }

    val favoriteImages = remember { mutableStateListOf<ImageData>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profil",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                },
                backgroundColor = Color(0xFF89CFF0),
                actions = {
                    IconButton(onClick = { editMode.value = !editMode.value }) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Edit",
                            tint = Color.White
                        )
                    }
                    /*
                    IconButton(onClick = { favoriteImages.clear() }) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Favorites",
                            tint = Color.White
                        )
                    }*/
                }
            )
        },
        content = { padding ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Hintergrundbild",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(color = Color(0x80000000)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = currentUser.value.profilePicture),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(200.dp).clickable {
                            // Hier kann die Logik zum Ändern des Profilbilds implementiert werden
                        }.clip(CircleShape).shadow(10.dp)
                    )
                    if (editMode.value) {
                        TextField(
                            value = firstNameState.value,
                            onValueChange = { firstNameState.value = it },
                            label = { Text(text = "Vorname") },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                textColor = Color.White,
                                cursorColor = Color.White
                            )
                        )
                        TextField(
                            value = lastNameState.value,
                            onValueChange = { lastNameState.value = it },
                            label = { Text(text = "Nachname") },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                textColor = Color.White,
                                cursorColor = Color.White
                            )
                        )
                        TextField(
                            value = emailState.value,
                            onValueChange = { emailState.value = it },
                            label = { Text(text = "E-Mail") },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                textColor = Color.White,
                                cursorColor = Color.White
                            )
                        )
                        Button(
                            onClick = {
                                currentUser.value.firstName = firstNameState.value
                                currentUser.value.lastName = lastNameState.value
                                currentUser.value.email = emailState.value
                                editMode.value = false
                                errorMessage.value = ""
                            },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = "Speichern")
                        }
                        if (errorMessage.value.isNotEmpty()) {
                            Text(
                                text = errorMessage.value,
                                color = MaterialTheme.colors.error,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth().height(50.dp)
                            )
                        }
                    } else {
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

                        Button(
                            onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .border(1.dp, Color.White)
                            .background(Color.Transparent),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent,
                                contentColor = Color.White
                        ),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favorites",
                                tint = Color.White
                            )
                            Text(
                                text = "Favorites",
                                modifier = Modifier.padding(start = 8.dp),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif
                                )
                            )
                        }

                    }
                /*
                    if (favoriteImages.isNotEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                        ) {
                            Text(
                                text = "Favoriten",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            favoriteImages.forEach { imageData ->
                                FavoriteImageItem(imageData)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }*/

                    }
                }
            }
    )
}


@Preview
@Composable
fun ProfileScreenPreview(){
    ProfileScreen()
}




/** -----------------------------------------------------------------------------------------------*
 *   es sollen später die im BuildingScreen favorisierten Bilder im Profil gespeichert werden.     *
 *   noch keine Funktion implementiert, folgt später im MVVM                                       *
 * ------------------------------------------------------------------------------------------------*
 */
/*
@Composable
fun FavoriteImageItem(imageData: ImageData) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageData.imageRes),
            contentDescription = "Bild",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = imageData.title,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

 */

