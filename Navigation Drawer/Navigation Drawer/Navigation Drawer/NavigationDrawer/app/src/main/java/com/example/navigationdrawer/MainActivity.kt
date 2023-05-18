package com.example.navigationdrawer


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
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
/*import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text*/
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.navigationdrawer.ui.theme.Screens.ProfileScreen
import com.example.navigationdrawer.ui.theme.Screens.HomeScreen
// import com.example.navigationdrawer.ui.theme.Screens.startNextActivity
import com.example.navigationdrawer.ui.theme.Screens.NavigationScreen
import java.lang.Exception

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           /* Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    val navigate = Intent(this@MainActivity, AnotherActivity::class.java)
                    startActivity(navigate)
                }) {

                    Text(text = "Navigate", fontSize = 18.sp)
                }

            */
                MainScreen()
            }
        }
    }
//}

@Composable
fun MainScreen(){

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState)},
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
        }
    ) {
        Navigation(navController = navController)

    }
}

@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState){
    TopAppBar(
        title = { Text(text = "Campus Navigation", fontSize = 20.sp)},
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        backgroundColor =  Color.LightGray,
        contentColor = Color.Black
    )
}


@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController
    ) {

    val items = listOf(
        MenueItem.Home,
        MenueItem.Navigation,
        MenueItem.Profile,
        MenueItem.Settings,
        MenueItem.Help
    )

    val navigationSubItems = listOf(
        MenueItem.Navigation.Room,
        MenueItem.Navigation.Scanner
    )

    Column(
        modifier = Modifier
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.LightGray),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.campusnav_icon),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .padding(0.dp)
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { items ->
            DrawerItem(item = items, selected = currentRoute == items.route, onItemClick = {
                navController.navigate(items.route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                scope.launch {
                    scaffoldState.drawerState.close()
                }

            })
        }

        Spacer(modifier = Modifier.weight(1f))

        // Überprüfen des ausgewählten Menüpunkts
     /*   val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route
*/
        if (currentRoute == MenueItem.Navigation.route) {
            navigationSubItems.forEach { subItem ->
                DrawerItem(
                    item = subItem,
                    selected = currentRoute == subItem.route,
                    onItemClick = {
                        navController.navigate(subItem.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                )
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


/*
@Composable
fun HomeScreen() {
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

 */


/*
@Composable
fun HomeScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text= "Home Screen",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
    }
}


 */


/*
@Composable
fun NavigationScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text= "Navigation Screen",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
    }
}

 */


@Composable
fun RoomScreen(onBackClick: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text= "Room Screen",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
    }

}


@Composable
fun ScannerScreen(onBackClick: () -> Unit) {
    var code by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )
    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (hasCameraPermission) {
            AndroidView(
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = androidx.camera.core.Preview.Builder().build()
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(
                            Size(
                                previewView.width,
                                previewView.height
                            )
                        )
                        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        QrCodeAnalyzer { result ->
                            code = result
                        }
                    )
                    try {
                        cameraProviderFuture.get().bindToLifecycle(
                            lifecycleOwner,
                            selector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    previewView

                },
                modifier = Modifier.weight(1f)
            )
            Text(
                //text= "Scanner Screen",
                // fontWeight = FontWeight.Bold,
                //            color = Color.Black,
                //            fontSize = 30.sp,
                //            textAlign = TextAlign.Center
                text = code,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)

            )
        }
    }
}

/*
@Composable
fun ProfileScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text= "Profile Screen",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
    }
}

 */


/*
@Composable
fun ProfileScreen() {
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
                title = { Text(text = "Profil", style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White, fontFamily = FontFamily.SansSerif)) },
                backgroundColor = Color(0xFF89CFF0), // Babyblau
                actions = {
                    // Ein Button zum Wechseln des Bearbeitungsmodus
                    IconButton(onClick = { editMode.value = !editMode.value }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit), // Das Icon muss in den drawable Ordner gelegt werden
                            contentDescription = "Edit",
                            tint = Color.White // Weißes Icon
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
                    painter = painterResource(id = R.drawable.campusnav_icon),
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
                                errorMessage.value = ""
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

 */


@Composable
fun SettingsScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text= "Settings Screen",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun HelpScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text= "Help Screen",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
    }
}


/*@Composable
fun Navigation(navController: NavHostController){

    NavHost(navController, startDestination = MenueItem.Home.route) {
        composable(MenueItem.Home.route){
            HomeScreen()
        }
        composable(MenueItem.Navigation.route){
            NavigationScreen(
                onBackClick = { navCon}
            )
        }
        composable(MenueItem.Navigation.Room.route){
            RoomScreen()
        }
        composable(MenueItem.Navigation.Scanner.route){
            ScannerScreen()
        }
        composable(MenueItem.Profile.route){
            ProfileScreen()
        }
        composable(MenueItem.Settings.route){
            SettingsScreen()
        }
        composable(MenueItem.Help.route){
            HelpScreen()
        }

    }
}

 */

@Composable
fun Navigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavHost(navController, startDestination = MenueItem.Home.route) {
        composable(MenueItem.Home.route) {
            HomeScreen()
        }
        composable(MenueItem.Navigation.route) {
            NavigationScreen(
                onRoomClick = { navController.navigate(MenueItem.Navigation.Room.route) },
                onScannerClick = { navController.navigate(MenueItem.Navigation.Scanner.route) }
            )
        }
        composable(MenueItem.Profile.route) {
            ProfileScreen()
        }
        composable(MenueItem.Navigation.Room.route) {
            RoomScreen(
                onBackClick = {
                    navController.popBackStack(
                        MenueItem.Navigation.route,
                        inclusive = false
                    )
                }
            )
        }
        composable(MenueItem.Navigation.Scanner.route) {
            ScannerScreen(
                onBackClick = {
                    navController.popBackStack(
                        MenueItem.Navigation.route,
                        inclusive = false
                    )
                }
            )
        }
        composable(MenueItem.Settings.route) {
            SettingsScreen()
        }
        composable(MenueItem.Help.route) {
            HelpScreen()
        }
    }
}









/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationDrawerTheme() {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        Start(
                            onNavigationIconClick = {
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }
                        )
                    },
                    drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                    drawerContent = {
                        TopBar()
                        NavElements(
                            items = listOf(
                                MenueItem(
                                    id = "home",
                                    title = "Home",
                                    contentDescription = "Start-Bildschirm",
                                    icon = Icons.Default.Home
                                ),

                                MenueItem(
                                    id = "navigation",
                                    title = "Navigation",
                                    contentDescription = "Navigations-Bildschirm",
                                    icon = Icons.Default.LocationOn
                                ),
                                MenueItem(
                                    id = "profile",
                                    title = "Profile",
                                    contentDescription = "Profil-Bildschirm",
                                    icon = Icons.Default.Person
                                ),
                                MenueItem(
                                    id = "settings",
                                    title = "Settings",
                                    contentDescription = "Go to settings screen",
                                    icon = Icons.Default.Settings
                                ),
                                MenueItem(
                                    id = "help",
                                    title = "Help",
                                    contentDescription = "Get help",
                                    icon = Icons.Default.Info
                                ),
                            ),
                            onItemClick = {
                                println("Clicked on ${it.title}")
                            }
                        )
                    }
                ) {

                }
            }
        }
    }
}

*/