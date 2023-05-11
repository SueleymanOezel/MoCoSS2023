package com.example.navigationdrawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
/*import androidx.compose.material3.Icon
import androidx.compose.material3.Text*/
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Überschrift im Header
@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 60.dp),
        contentAlignment = Alignment.Center
    ){
        Text(text = "CampusNav", fontSize = 50.sp, color = Color.DarkGray)
    }
}

// Body -> Menue-Items

@Composable
fun NavElements(
    items: List<MenueItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 20.sp),
    onItemClick: (MenueItem) -> Unit
) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(18.dp)
                    ) {
                Icon(imageVector = item.icon, contentDescription = item.contentDescription)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = item.title, style = itemTextStyle, modifier = Modifier.weight(1f))
            }
        }
    }
}