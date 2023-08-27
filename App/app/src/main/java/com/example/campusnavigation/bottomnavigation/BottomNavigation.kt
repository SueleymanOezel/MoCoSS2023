package com.example.campusnavigation.bottomnavigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.campusnavigation.mvvm.MainViewModel
import com.example.campusnavigation.screens.BuildingScreen
import com.example.campusnavigation.screens.ProfileScreen
import com.example.campusnavigation.screens.home.HomeScreen
import com.example.campusnavigation.screens.nav.NavigationScreen
import com.example.campusnavigation.screens.nav.room.RoomDetailScreen
import com.example.campusnavigation.screens.nav.room.RoomScreen
import com.example.campusnavigation.screens.nav.room.ScannerScreen


/**
 *  Der folgende Code dient dazu, die Navigation zwischen den Bildschrimen unserer Appzu steuern
 *  und stellt die erforderlichen Argumente und Parameter bereit.
 *
 *  Der RoomDetailScreen wird mithilfe von Argumenten definiert, die in der Route spezifiziert werden.
 *  Innerhalb der composable-Funktion werden diese Argumente aus dem backStackEntry extrahiert.
 *  Anschließend wird mithilfe der Raumnummer der zugehörige Raumtext aus dem viewModel abgerufen.
 *
 *  Hinweis: Das composable für den SettingsScreen wurde entfernt, da es nach aktuellem Stand
 *  nicht benötigt wird.
 */

@Composable
fun Navigation(

    navController: NavHostController,
    viewModel: MainViewModel
) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                onItemClick = { route ->
                    navController.navigate(route)
                }
            )
        }
    ) {
            innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
        ) {

            NavHost(
                navController = navController,
                startDestination = "home") {

                composable("home") {
                    HomeScreen(viewModel)
                }

                composable("profile") {
                    ProfileScreen()
                }

                composable("navigation") {
                    NavigationScreen(navController = navController)
                }

                composable("building") {
                    BuildingScreen()
                }

                composable("room") {
                    RoomScreen(navController = navController)
                }

                // composable mit Argumenten für Raumnummer und Hintergrund
                composable(
                    route = "room/{roomNumber}/{background}",
                    arguments = listOf(
                        navArgument("roomNumber") {
                            type = NavType.StringType
                                                  },
                        navArgument("background")
                        { type = NavType.IntType
                        }
                    )
                ) {
                        backStackEntry ->

                    // Raumnummer und Hintergrund aus den Argunmenten extrahieren
                    val roomNumber = backStackEntry.arguments?.getString("roomNumber")
                    val roomBackgroundResId = backStackEntry.arguments?.getInt("background") ?: 0

                    // Text für die entsprechende Raumnummer abrufen
                    val roomText = roomNumber?.let { viewModel.getRoomText(it) } ?: ""

                    // Anzeige des Raumtexts und Hintergrund des RoomScreen im RoomDetailScreen
                    RoomDetailScreen(roomText = roomText, roomBackgroundResId = roomBackgroundResId)
                }

                composable("scanner") {
                    ScannerScreen()
                }
            }
        }
    }
}



