package com.example.bottombar

<<<<<<< HEAD
=======
/*import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar*/
import android.annotation.SuppressLint
>>>>>>> origin/main
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
<<<<<<< HEAD
/*import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar*/
=======
>>>>>>> origin/main
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
<<<<<<< HEAD
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
=======
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
>>>>>>> origin/main
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bottombar.ui.theme.BottombarTheme


<<<<<<< HEAD

=======
>>>>>>> origin/main
/*  erbt von ComponentActivity
    Aufruf von ´setContent()´ in ´onCreate()-Methode´
    -> Anzeige der ´MainScreen()-Funktion´
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
<<<<<<< HEAD
           MainScreen()
=======
            BottombarTheme {
                MainScreen()
            }
>>>>>>> origin/main
        }
    }
}

/*  Hauptbildschirm -> Navigationsleiste unten i.d. App (Z. 60),
    Top-Leiste (Z.59) und Navigation (Z. 62)

 */

<<<<<<< HEAD
=======

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
>>>>>>> origin/main
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        Navigation(navController)
    }

}

/*  Top-Leiste (oben i.d. App)
    -> enthält den Namen, hier "Bottom Navigation"

 */


@Composable
fun TopBar() {

    TopAppBar(
        title = { Text(text = "Bottom Navigation", fontSize = 18.sp) },
        backgroundColor = Color.White,
        contentColor = Color.Black
    )

}



/*  Bottom Navigation Bar -> untere Navigationsleiste
    enthält die drei Navigationspunkte "Home", "Profil", "Einstellungen"
    zeigt aktuelles Element an, das ausgewählt wurde
    Navigation zwischen den Bildschirmen, die mit den Navigationspunkten verknüpft sind

 */


@Composable
fun BottomNavigationBar(navController: NavController) {

    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Profil,
        NavigationItem.Einstellungen
    )
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach {items ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = items.icon),
                        contentDescription = items.title
                    )
                },
                label = { Text(text = items.title) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(1.0f),
                alwaysShowLabel = true,
                selected = currentRoute == items.route,
                onClick = {
                    navController.navigate(items.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route = route) {
                                saveState = true
                            }
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            }
        }

    }

<<<<<<< HEAD

@Composable
fun HomeScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home Screen",
            fontSize = 30.sp,               // Schriftgröße
            fontWeight = FontWeight.Bold,   // Schriftstärke
            color = Color.Black
        )

    }
}

=======
>>>>>>> origin/main
@Composable
fun ProfileScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile Screen",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }

}

@Composable
fun SettingsScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Settings Screen",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }

}


/*
    Navigationsleiste (NavHost)
    Navigation zwischen den Bildschrimen
 */

@Composable
fun Navigation(navController: NavHostController){

    NavHost(navController, startDestination = NavigationItem.Home.route){

        composable(NavigationItem.Home.route){
            HomeScreen()
        }

        composable(NavigationItem.Profil.route){
            ProfileScreen()
        }

        composable(NavigationItem.Einstellungen.route){
            SettingsScreen()
        }

    }

}
