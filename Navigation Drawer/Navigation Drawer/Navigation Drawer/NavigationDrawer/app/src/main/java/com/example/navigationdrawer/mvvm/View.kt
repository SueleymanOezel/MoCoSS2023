package com.example.navigationdrawer.mvvm

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigationdrawer.HelpScreen
import com.example.navigationdrawer.MainActivity
import com.example.navigationdrawer.MainActivity.FirestoreUtil.firestore
import com.example.navigationdrawer.MenueItem
import com.example.navigationdrawer.R
import com.example.navigationdrawer.ScannerScreen
import com.example.navigationdrawer.SettingsScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {

    // Erstelle eine Instanz von FirestoreDao mit der FirestoreUtil Klasse
    val dao = FirestoreDao(firestore)

    // Erstelle eine Instanz von MainViewModel mit dem dao als Parameter
    val viewModel = MainViewModel(dao)

    // Erstelle eine Instanz von ScaffoldState mit einem geschlossenen DrawerState
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    // Erstelle eine Instanz von CoroutineScope mit dem rememberCoroutineScope Funktion
    val scope = rememberCoroutineScope()

    // Erstelle eine Instanz von NavController mit dem rememberNavController Funktion
    val navController = rememberNavController()

    // Verwende die Scaffold Komponente, um die TopBar und den DrawerContent zu definieren
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
        drawerContent = {
            Drawer(
                scope = scope,
                scaffoldState = scaffoldState,
                navController = navController,
                viewModel = viewModel // Gib das ViewModel an den Drawer weiter
            )
        }
    ) {
        // Verwende die Navigation Komponente, um zwischen verschiedenen Screens zu navigieren
        Navigation(navController = navController, viewModel = viewModel) // Gib das ViewModel an die Navigation weiter
    }
}

@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    // Verwende die TopAppBar Komponente, um eine obere Leiste mit einem Titel und einem Navigationsicon anzuzeigen
    TopAppBar(
        title = { Text(text = "Campus Navigation", fontSize = 20.sp) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        backgroundColor = Color.LightGray,
        contentColor = Color.Black
    )
}

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController,
    viewModel: MainViewModel // Nimm das ViewModel als Parameter entgegen
) {

    // Erstelle eine Liste von MenueItems für die verschiedenen Screens der App
    val items =
        listOf(
            MenueItem.Home,
            MenueItem.Navigation,
            MenueItem.Profile,
            MenueItem.Settings,
            MenueItem.Help)

    // Erstelle eine Liste von MenueItems für die Unteroptionen der Navigation Screen (Raum und Scanner)
    val navigationSubItems =
        listOf(MenueItem.Navigation.Room, MenueItem.Navigation.Scanner)

    // Verwende eine Column Komponente, um die Elemente des Drawers vertikal anzuordnen
    Column(
        modifier =
        Modifier.background(color =
        Color.White)) {

        // Verwende eine Row Komponente, um das Logo des Drawers horizontal anzuzeigen
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.LightGray),
            verticalAlignment =
            Alignment.CenterVertically,
            horizontalArrangement =
            Arrangement.Center) {

            // Verwende eine Image Komponente, um das Logo als Bild anzuzeigen
            Image(
                painter =
                painterResource(id =
                R.drawable.campusnav_icon),
                contentDescription =
                "Logo",
                modifier =
                Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .padding(0.dp))


        }

        Spacer(modifier =
        Modifier
            .fillMaxWidth()
            .height(5.dp))

        // Beobachte den aktuellen Screen mit dem currentBackStackEntryAsState Funktion
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute =
            navBackStackEntry?.destination?.route

        // Schleife durch die MenueItems und erstelle ein DrawerItem für jedes Element
        items.forEach { item ->
            DrawerItem(
                item =
                item,
                selected =
                currentRoute == item.route,
                onItemClick =
                {
                    // Navigiere zum entsprechenden Screen mit dem NavController
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                    // Schließe den Drawer mit dem CoroutineScope
                    scope.launch { scaffoldState.drawerState.close() }
                })
        }

        Spacer(modifier =
        Modifier.weight(1f))

        // Wenn der aktuelle Screen der Navigation Screen ist, zeige auch die Unteroptionen an
        if (currentRoute == MenueItem.Navigation.route) {
            navigationSubItems.forEach { subItem ->
                DrawerItem(
                    item =
                    subItem,
                    selected =
                    currentRoute == subItem.route,
                    onItemClick =
                    {
                        // Navigiere zum entsprechenden Screen mit dem NavController
                        navController.navigate(subItem.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) { saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }

                        // Schließe den Drawer mit dem CoroutineScope
                        scope.launch { scaffoldState.drawerState.close() } })
            }
        }
        Text(
            text = "",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun DrawerItem(item: MenueItem, selected: Boolean, onItemClick: (MenueItem) -> Unit){

    val background = if(selected) R.color.grey else android.R.color.transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .height(60.dp)
            .background(colorResource(id = background))
            .padding(start = 12.dp)
    ){
        Image(
            imageVector = item.icon,
            contentDescription = item.title,
            colorFilter = ColorFilter.tint(Color.Black),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = item.title, fontSize = 18.sp, color = Color.Black)

    }
}

@Composable
fun Navigation(navController: NavHostController, viewModel: MainViewModel) {

    NavHost(navController, startDestination = MenueItem.Home.route) {
        composable(MenueItem.Home.route) {
            com.example.navigationdrawer.ui.theme.Screens.HomeScreen(viewModel) // Gib das ViewModel an den HomeScreen weiter
        }
        composable(MenueItem.Navigation.route) {
            NavigationScreen(viewModel) // Gib das ViewModel an den NavigationScreen weiter
        }
        composable(MenueItem.Profile.route) {
            com.example.navigationdrawer.ui.theme.Screens.ProfileScreen(viewModel) // Gib das ViewModel an den ProfileScreen weiter
        }
        composable(MenueItem.Navigation.Room.route) {
            RoomScreen(viewModel) // Gib das ViewModel an den RoomScreen weiter
        }
        composable(MenueItem.Navigation.Scanner.route) {
            ScannerScreen(viewModel) // Gib das ViewModel an den ScannerScreen weiter
        }
        composable(MenueItem.Settings.route) {
            SettingsScreen()
        }
        composable(MenueItem.Help.route) {
            HelpScreen()
        }
    }
}

@Composable
fun NavigationScreen(viewModel: MainViewModel){

    Column(
        modifier=Modifier.fillMaxSize(),
        verticalArrangement= Arrangement.Center,
        horizontalAlignment= Alignment.CenterHorizontally){

        Text(text="Navigation Screen", fontWeight= FontWeight.Bold,color= Color.Black,fontSize=30.sp,textAlign= TextAlign.Center)

        Spacer(modifier=Modifier.height(16.dp))

        Button(onClick={}){

            Text(text="Go to Room")

        }

        Spacer(modifier=Modifier.height(16.dp))

        Button(onClick={}){

            Text(text="Scan QR Code")

        }

    }

}

@Composable
fun ProfileScreen(viewModel: MainViewModel){

    Column(
        modifier=Modifier.fillMaxSize(),
        verticalArrangement= Arrangement.Center,
        horizontalAlignment= Alignment.CenterHorizontally){

        Text(text="Profile Screen", fontWeight= FontWeight.Bold,color= Color.Black,fontSize=30.sp,textAlign= TextAlign.Center)

    }

}

@Composable
fun RoomScreen(viewModel: MainViewModel){

    Column(
        modifier=Modifier.fillMaxSize(),
        verticalArrangement= Arrangement.Center,
        horizontalAlignment= Alignment.CenterHorizontally){

        Text(text="Room Screen", fontWeight= FontWeight.Bold,color= Color.Black,fontSize=30.sp,textAlign= TextAlign.Center)

        Spacer(modifier=Modifier.height(16.dp))

        // Beobachte den aktuellen Raum aus dem ViewModel
        val currentRoom by viewModel.currentRoom.observeAsState()

        // Wenn der aktuelle Raum nicht null ist, zeige seine Details an
        if (currentRoom != null) {
            Text(text="You are in room ${currentRoom!!.room.roomNr} in building ${currentRoom!!.room.buildingNr}", fontWeight= FontWeight.Bold,color= Color.Black,fontSize=20.sp,textAlign= TextAlign.Center)
            Text(text="The room is on the ${currentRoom!!.room.floor} floor and has the following description: ${currentRoom!!.room.description}", fontWeight= FontWeight.Bold,color= Color.Black,fontSize=20.sp,textAlign= TextAlign.Center)
            Text(text="The scanned code is ${currentRoom!!.scannedCode}", fontWeight= FontWeight.Bold,color= Color.Black,fontSize=20.sp,textAlign= TextAlign.Center)
        } else {
            // Wenn der aktuelle Raum null ist, zeige eine Nachricht an
            Text(text="You have not selected or scanned a room yet", fontWeight= FontWeight.Bold,color= Color.Black,fontSize=20.sp,textAlign= TextAlign.Center)
        }

    }

}