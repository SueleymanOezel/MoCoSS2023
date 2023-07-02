package com.example.scan.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.scan.mvvm.MainViewModel


@Composable
fun AddNote(closeRecord: () -> Unit, viewModel: MainViewModel) {
    Surface(
        border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
        shape = RoundedCornerShape(8.dp),
        color = Color(0xCCEEEEEE),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var titleText by rememberSaveable { mutableStateOf("") }
            var checkBoxStatus by rememberSaveable { mutableStateOf(false) }
            TextField(
                value = titleText,
                onValueChange = { titleText = it },
                label = { Text("Title") }
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Urgency:")
                Checkbox(
                    checked = checkBoxStatus,
                    onCheckedChange = {
                        checkBoxStatus = !checkBoxStatus
                    }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            TextButton(onClick = {
                if (titleText != "") {
                    viewModel.addRecord(titleText, checkBoxStatus){
                        closeRecord.invoke()
                    }
                }
            }) {
                Text(text = "Submit Record")
            }
        }
    }
}

