package com.example.campnavfinal.bottomnavigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.campnavfinal.R


/**
 *  Im folgenden Code wird eine Navigationsleiste am unteren Bildschrimrand erstellt, welche die
 *  Navigationspunkte mit den Icons unserer Screens anzeigt. Dabei wird der aktive Navigationspunkt
 *  farblich hervorgehoben.
 *
 */




@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit
) {

    // Liste der Navigationspunkte
    val items = listOf(

        // Navigationspunkt für den HomeScreen
        BottomItem(
            name = "Home",
            route = "home",
            icon = R.drawable.home
        ),

        // Navigationspunkt für den ProfileScreen
        BottomItem(
            name = "Profile",
            route = "profile",
            icon = R.drawable.ic_person

            ),

        // Navigationspunkt für den NavigationScreen
        BottomItem(
            name = "Navigation",
            route = "navigation",
            icon = R.drawable.room

            ),

        // Navigationspunkt für den BuildingScreen
        BottomItem(
            name = "Building",
            route = "building",
            icon = R.drawable.building

            ),

        // Navigationspunkt für den MapsScreen
        BottomItem(
            name = "Maps",
            route = "maps",
            icon = R.drawable.location

        ),

        /** Der Navigationspunkt Settings wird wahrscheinlich doch nicht mehr benötigt,
         *  da es für die Funktion unserer App (Stand 19.06.2023) nicht relevant ist.
         *  Wenn sich die Anforderungen nicht ändern, kann der folgende Teil später gelöscht werden.

        BottomItem(
            name = "Settings",
            route = "settings",
            icon = Icons.Default.Settings,

            )

         */
    )

    // Abruf des aktuellen Navigationspunktes aus dem NavController
    val backStackEntry = navController.currentBackStackEntryAsState()

    // Anzeige der BottomNavigationBar
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        elevation = 5.dp
    ) {

        // erstellt für jeden Navigationspunkt ein BottomNavigationItem
        items.forEach { item ->

            // Überprüfung, ob der aktuelle Navigationspunkt ausgewählt ist
            val selected = item.route == backStackEntry.value?.destination?.route

            // Anzeige der BottomNavigationBar
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item.route) },
                selectedContentColor = Color.Green,  // bei Auswahl des Menüpunkts grün anzeigen
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        // Icons der jeweiligen Menüpunkte anzeigen
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.name
                        )

                        // Überprüfung, ob der Navigationspunkt ausgewählt ist und Text anzeigen
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}




// Vorschau für die BottomBar
@Preview
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(navController = navController, onItemClick = {})
}
