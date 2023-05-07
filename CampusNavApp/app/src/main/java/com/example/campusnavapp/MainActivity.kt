package com.example.campusnavapp

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.campusnavapp.ui.theme.CampusNavAppTheme
import com.google.android.material.navigation.NavigationView

class MainActivity : ComponentActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CampusNavAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                }
            }

            // Verknüpfe die NavigationView mit dem OnNavigationItemSelectedListener
            val navigationView = findViewById<NavigationView>(R.id.bottom_navigation_view)
            navigationView.setNavigationItemSelectedListener { menuItem ->
                // Aufruf der Methode onNavigationItemSelected() bei Benutzereingabe
                onNavigationItemSelected(menuItem)
            }

            // Setze das Home-Element als ausgewählt
            navigationView.setCheckedItem(R.id.navigation_home)

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Hier kannst du auf die Benutzereingaben reagieren
        when (item.itemId) {
            R.id.navigation_home -> {
                // Der Benutzer hat auf "Home" geklickt
            }
            R.id.navigation_profile -> {
                // Der Benutzer hat auf "Einstellungen" geklickt
            }
            R.id.navigation_settings -> {
                // Der Benutzer hat auf "Einstellungen" geklickt
            }
        }

        // Rückgabe true, um zu signalisieren, dass das Element als ausgewählt markiert werden soll
        return true
    }
}

/*@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CampusNavAppTheme {
        Greeting("Android")
    }
}*/