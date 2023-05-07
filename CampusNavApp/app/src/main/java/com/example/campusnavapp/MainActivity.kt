package com.example.campusnavapp

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.campusnavapp.Screens.HomeScreen
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
                    // Zeige den Inhalt der App an
                    HomeContent()

                    // Verknüpfe die NavigationView mit dem OnNavigationItemSelectedListener
                    val navigationView = findViewById<NavigationView>(R.id.nav_view)
                    navigationView.setNavigationItemSelectedListener { menuItem ->
                        // Aufruf der Methode onNavigationItemSelected() bei Benutzereingabe
                        onNavigationItemSelected(menuItem)
                    }
                    // Setze das Home-Element als ausgewählt
                    navigationView.setCheckedItem(R.id.navigation_home)
                }
            }
        }
    }

}

@Composable
fun HomeContent() {
    HomeScreen.Content()
}