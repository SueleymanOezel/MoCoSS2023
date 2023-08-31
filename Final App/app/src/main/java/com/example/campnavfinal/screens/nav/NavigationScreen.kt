package com.example.campnavfinal.screens.nav

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.campnavfinal.R
import com.example.campnavfinal.ui.theme.Blue1
import com.example.campnavfinal.ui.theme.Blue2

@Composable
fun NavigationScreen(navController: NavHostController) {


    // Hintergrundfläche mit primärer Farbe
    Surface(
        color = Color.Gray
    ) {

        // Box-Container für die gesamte Startseite
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {

                    // Erstellen eines Pfads für die Hintergrundbezeichnung
                    val path = Path()
                    val x = size.width
                    val y = size.height
                    val center = size.center
                    path.apply {

                        // Linie von (0,0) nach (x, 0)
                        moveTo(0f, 0f)
                        lineTo(x, 0f)

                        // Kubische Bezier-Kurve zur Erzeugung der Wellenform
                        cubicTo(
                            x1 = x,
                            y1 = center.y / 2,
                            x2 = x,
                            y2 = center.y,
                            x3 = 0f,
                            y3 = center.y

                        )

                    }

                    // Zeichnen des Pfads mit der Farbe LightGray
                    drawPath(path = path, color = Color(0xFDE1E1ED))

                }
        ) {

            // UI-Elemente der Startseite
            Column(modifier = Modifier.padding(20.dp)) {


                // Titel und Willkommensnachricht bei Start der App ("Deckblatt")
                Text(
                    text = "Navigation zu den Räumen",
                    style = MaterialTheme.typography.h1,
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "",
                    style = MaterialTheme.typography.body1,
                    fontSize = 24.sp,
                    color = Color.Black
                )

                Spacer(
                    modifier = Modifier
                        .size(20.dp))

                // Logo der App
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(190.dp)
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
                    .wrapContentSize(align = Alignment.BottomCenter)
            ) {

                // Button zum Einlogen
                // -> Weiterleitung zur LoginPageScreen
                Button(
                    onClick = {navController.navigate("room")},
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Blue2

                    )
                ) {
                    Text(text = "Go To Room")
                }

                Spacer(modifier = Modifier.size(18.dp)
                )

                // Button zur Registrierung
                // -> Weiterleitung zur SignUpPageScreen
                Button(
                    onClick = {navController.navigate("scanner")},
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Blue1
                    )
                ) {
                    Text(text = "Scan QR Code")
                }
            }

        }
    }
}

/*
@Composable
fun NavigationScreen(navController: NavHostController){

    // val navController = rememberNavController()
    Column(
        modifier= Modifier.fillMaxSize(),
        verticalArrangement= Arrangement.Center,
        horizontalAlignment= Alignment.CenterHorizontally){

        Text(text="Navigation Screen", fontWeight= FontWeight.Bold,color= Color.Black,fontSize=30.sp,textAlign= TextAlign.Center)

        Spacer(modifier= Modifier.height(16.dp))

        Button(onClick= {
            navController.navigate("room")
            //navController.navigate(MenueItem.Navigation.Room.route)
        }

        ){

            Text(text="Go to Room")

        }

        Spacer(modifier= Modifier.height(16.dp))

        Button(onClick={
            navController.navigate("scanner")
            //navController.navigate(MenueItem.Navigation.Scanner.route)
        }){

            Text(text="Scan QR Code")

        }

    }

}

 */

@Preview
@Composable
fun NavigationScreenPreview() {
    val navController = rememberNavController()
    NavigationScreen(navController = navController)
}