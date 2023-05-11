package com.example.navigationdrawer

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
/*import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor*/
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Start(
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name)) // hier wird der App-Name oben in der App angezeigt
        }, backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White, //MaterialTheme.colors.onPrimary,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "...")

            }
        }
        /*
            Modifier.background(color = MaterialTheme.colorScheme.primary,
            contentColorFor(backgroundColor = MaterialTheme.colorScheme.onPrimary

        */

        )


}