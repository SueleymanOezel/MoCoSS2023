package com.example.navigationdrawer.ui.theme.Screens

import android.graphics.BitmapShader
import android.graphics.Shader
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.navigationdrawer.R
import com.example.navigationdrawer.mvvm.MainScreen
import com.example.navigationdrawer.mvvm.SignUpState
import com.example.navigationdrawer.ui.theme.NavigationDrawerTheme
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import coil.compose.ImagePainter.State.Empty.painter
import com.example.navigationdrawer.ui.theme.BackgroundLogo
import com.example.navigationdrawer.ui.theme.Blue1
import com.example.navigationdrawer.ui.theme.Blue2

/*
@Composable
fun LoginHomeScreen() {
    LandingPageScreen()
}*/




/*
@Composable
fun App(){

    val (currentScreen, setCurrentScreen) = remember { mutableStateOf(Screen.Landing) }

    when (currentScreen) {
        Screen.Landing -> LandingPageScreen(
            onLoginClick = { setCurrentScreen(Screen.Login) },
            onSignUpClick = { setCurrentScreen(Screen.SignUp) }
        )

        Screen.Login -> LoginPageScreen()
        Screen.SignUp -> SignUpPageScreen()

    }
}

 */



enum class Screen {
    Start, SignIn, SignUp, Main
}



@Composable
fun App(){
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = Screen.Start.name) {
        composable(Screen.Start.name) {

            // Aufruf der StartPageScreen-Funktion mit den entsprechenden Navigationsschritten
            StartScreen(
                onLoginClick = { navController.navigate(Screen.SignIn.name) },
                onSignUpClick = {navController.navigate(Screen.SignUp.name)}
            )
        }

        composable(Screen.SignIn.name){
            // Aufruf der LoginPageScreen-Funktion und Übergabe des NavHostControllers
            // -> Navigationspunkt für die Login-Seite
            LoginScreen(navController = navController)
        }

        composable(Screen.SignUp.name){
            // Aufruf der SignUpPageScreen-Funktion und Übergabe des NavHostControllers
            // -> Navigationspunkt für die Registrierungsseite
            SignUpScreen(navController = navController)
        }

        composable(Screen.Main.name){
            // Aufruf der MainScreen-Funktion
            // -> Navigationspunkt für den Hauptbildschirm
            MainScreen()
        }
    }
}



@Composable
fun StartScreen(onLoginClick: () -> Unit, onSignUpClick:() -> Unit) {

    // Zustandsvariablen für die aktuelle Seite
    val (currentPage, setCurrentPage) = remember { mutableStateOf(Screen.SignIn) }

    // Hintergrundfläche mit primärer Farbe
    Surface(color = Color.Gray) {
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
                    text = "Hello",
                    style = MaterialTheme.typography.h1,
                    fontSize = 32.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Welcome to CampusNav",
                    style = MaterialTheme.typography.body1,
                    fontSize = 24.sp,
                    color = Color.Black
                )

                Spacer(
                    modifier = Modifier
                        .size(20.dp))

                // Logo der App
                Image(
                    painter = painterResource(id = R.drawable.campusnav_icon),
                    contentDescription = null,
                    modifier = Modifier.size(230.dp)
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
                    onClick = onLoginClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Blue2

                    )
                ) {
                    Text(text = "Sign In")
                }
                Spacer(modifier = Modifier.size(18.dp)
                )

                // Button zur Registrierung
                // -> Weiterleitung zur SignUpPageScreen
                Button(
                    onClick = onSignUpClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Blue1
                    )
                ) {
                    Text(text = "Sign up")
                }
            }

        }
    }
}


// Preview für den Start Bildschirm
@Preview
@Composable
fun StartScreenPreview() {
    StartScreen(
        onLoginClick = {},
        onSignUpClick = {}
    )
}


// Funktion für den Login-Bildschirm
@Composable
fun LoginScreen(navController: NavHostController) {

    Surface(
        color = Color.LightGray,
        contentColor = MaterialTheme.colors.onSurface,
    ) {

        // Formular für den Login-Bildschirm
        val (username, onUserNameChange) = remember {
            mutableStateOf("")
        }

        val (password, onPasswordChange) = remember {
            mutableStateOf("")
        }

        val (checked, onCheckedChange) = remember {
            // Zustand für die Checkbox "Remember me"
            mutableStateOf(false)
        }

        Column {
            // Überschrift "Login"
            Text(
                text = "Login",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(20.dp),
            )

            Spacer(
                modifier = Modifier
                    .size(16.dp)
            )

            // Eingabefeld für den Benutzernamen
            OutlinedTextField(
                value = username,
                onValueChange = onUserNameChange,
                label = { Text(text = "UserName") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(
                modifier = Modifier
                    .size(16.dp)
            )

            // Eingabefeld für das Passwort
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text(text = "Password") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(
                modifier = Modifier
                    .size(16.dp)
            )

            // Checkbox "Remember me"
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Checkbox(checked = checked, onCheckedChange = onCheckedChange)
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(text = "Remember me")
                }

                // Button "Forgot Password"
                TextButton(
                    onClick = {  },
                ) {
                    Text(text = "Forgot Password")
                }

            }

            Spacer(
                modifier = Modifier
                    .size(16.dp)
            )

            // Button "Login"
            Button(
                // beim Anklicken des Buttons wird man auf den Mainscreen weitergeleitet
                onClick = { navController.navigate(Screen.Main.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Login")
            }
        }


        Row(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomCenter)
        ) {
            Text(text = "Don't have an Account?")

            Spacer(
                modifier = Modifier
                    .size(8.dp)
            )

            // Button "Sign Up"
            TextButton(
                onClick = {  },
            ) {
                Text(text = "Sign Up")

            }


        }


    }


}


//Preview für Login Bildschirm
@Preview
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}



@Composable
fun SignUpScreen(navController: NavHostController) {

    // Erstellen des Zustands für die SignUp-Seite
    val signUpState = SignUpState()

    Surface(
        color = Color.LightGray,
        contentColor = MaterialTheme.colors.onSurface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(16.dp),
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {

                // Eingabefeld für den Vornamen
                OutlinedTextField(
                    value = signUpState.firstName,
                    onValueChange = {
                        signUpState.firstNameChenged(newValue = it)
                        Log.i("SignUp", "SignUpPageScreen: $it")
                    },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    label = { Text(text = "First Name") }
                )
                Log.i("SignUp", "SignUpPageScreen: ${signUpState.firstName}")
                Spacer(
                    modifier = Modifier
                        .size(8.dp)
                )

                // Eingabefeld für den Nachnamen
                OutlinedTextField(
                    value = signUpState.lastName,
                    onValueChange = { signUpState.lastNameChange(it) },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    label = { Text(text = "Last Name") }
                )

            }

            // Eingabefeld für die E-Mail-Adresse
            OutlinedTextField(
                value = signUpState.emailAddress,
                onValueChange = { signUpState.emailAddressChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium,
                label = { Text(text = "Email Address") }
            )
            Spacer(
                modifier = Modifier
                    .size(8.dp)
            )

            // Eingabefeld für das Passwort
            OutlinedTextField(
                value = signUpState.password,
                onValueChange = { signUpState.password(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium,
                label = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            // Eingabefeld für die Bestätigung des Passworts
            OutlinedTextField(
                value = signUpState.confirmPassword,
                onValueChange = { signUpState.confirmPasswordChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium,
                label = { Text(text = "Confirm Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(
                modifier = Modifier
                    .size(8.dp)
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Checkbox(
                    checked = signUpState.checked,
                    onCheckedChange = { signUpState.checkedChange(it) }
                )
                Spacer(
                    modifier = Modifier
                        .size(4.dp)
                )
                Text(text = "Agree with privacy policy")
            }

            Spacer(
                modifier = Modifier
                    .size(8.dp)
            )

            // Button "Sign Up"
            Button(
                // beim Anklicken des Buttons wird man auf die Mainscreen weitergeleitet
                onClick = { navController.navigate(Screen.Main.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Sign Up")

            }


            Row(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentSize(align = Alignment.BottomCenter)) {

                Text(text = "Already have an account?")
                Spacer(
                    modifier = Modifier
                        .size(8.dp)
                )
                Text(
                    text = "Sign In",
                    modifier = Modifier
                        .clickable { },
                    color = MaterialTheme.colors.primary
                )


            }


        }

    }


}

// Preview für Registrierungsbildschirm
@Preview
@Composable
fun SignUpScreenPreview() {
    val navController = rememberNavController()
    SignUpScreen(navController = navController)
}



/*

// Preview für Login Screen
@Preview(showBackground = true)
@Composable
fun LoginScreenPrev() {
    NavigationDrawerTheme() {
        LoginScreen(navController = )
    }
}


// Preview für SignUp Screen
@Preview(showBackground = true)
@Composable
fun StartScreenPrev() {
    NavigationDrawerTheme() {
        SignUpScreen(navController = ) {

        }
    }
}


 */


/*
@Preview(showBackground = true)
@Composable
fun StartPagePrev() {
    NavigationDrawerTheme {
        StartPageScreen()
    }
}
*/