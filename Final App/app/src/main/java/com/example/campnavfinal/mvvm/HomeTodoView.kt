package com.example.campnavfinal.mvvm

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.campnavfinal.screens.home.AddNote
import com.example.campnavfinal.R
import com.example.campnavfinal.ui.theme.Blue1
import com.example.campnavfinal.ui.theme.Blue2
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeTodoView(viewModel: MainViewModel) {
    val todoListState = viewModel.todoListFlow.collectAsState(listOf())
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.note),
                            contentDescription = "Logo",
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            "Notes",
                            modifier = Modifier.padding(start = 10.dp),
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                },
                backgroundColor = Color(0xFF89CFF0).copy(alpha = 0.8f),
                contentColor = Color.Black
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Hintergrundbild für den HomeScreen
                Image(
                    painter = painterResource(id = R.drawable.logo2),
                    contentDescription = "Hintergrundbild",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                // Overlay-Hintergrund mit reduziertem Alpha-Wert
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                var popupControl by remember { mutableStateOf(false) }
                var searchNotes by remember { mutableStateOf("") }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = searchNotes,
                        onValueChange = { searchNotes = it },
                        label = { Text("Suchen...") },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { viewModel.searchRecords(searchNotes) },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    state = lazyListState,
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(
                        items = todoListState.value,
                        key = { todoItem -> todoItem.id },
                        itemContent = { item ->
                            val currentItem by rememberUpdatedState(item)
                            val dismissState = rememberDismissState(
                                confirmStateChange = {
                                    if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                                        viewModel.removeRecord(currentItem)
                                        true
                                    } else false
                                }
                            )

                            if (dismissState.isDismissed(DismissDirection.EndToStart) ||
                                dismissState.isDismissed(DismissDirection.StartToEnd)
                            ) {
                                viewModel.removeRecord(item)
                            }

                            SwipeToDismiss(
                                state = dismissState,
                                modifier = Modifier
                                    .padding(vertical = 1.dp)
                                    .animateItemPlacement(),
                                directions = setOf(
                                    DismissDirection.StartToEnd,
                                    DismissDirection.EndToStart
                                ),
                                dismissThresholds = { direction ->
                                    FractionalThreshold(
                                        if (direction == DismissDirection.StartToEnd) 0.66f else 0.50f
                                    )
                                },
                                background = {
                                    SwipeBackground(dismissState)
                                },
                                dismissContent = {
                                    TodoItemRow(item, todoListState, viewModel)
                                }
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { viewModel.deleteAllRecords() },
                                colors = ButtonDefaults.buttonColors(
                                backgroundColor = Blue2
                    )
                    ){
                        Text("Delete All")
                    }
                    Button(
                        onClick = { popupControl = true },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Blue2
                    )
                    ){
                        Text("Add Note")
                    }
                }

                if (popupControl) {
                    Popup(
                        popupPositionProvider = CenteredPopupPositionProvider(),
                        onDismissRequest = { popupControl = false },
                        properties = PopupProperties(focusable = true)
                    ) {
                        AddNote({
                            popupControl = false
                            scope.launch {
                                lazyListState.animateScrollToItem(todoListState.value.size)
                            }
                        }, viewModel)
                    }
                }
            }


}
        }
    )
}



/** -----------------------------------------------------------------------------------------------*
 *  Der Folgende Abschnitt ist die erste Version der HomeTodoView.                                                   *
 *  Der Unterschied zur aktuellen Version besteht in der Struktur und dem Design.                  *
 *  Der Code wird nicht mehr benötigt, dient nur dazu, dass die anderen Gruppenmitglieder          *
 *  @soezel und @lhassan den Code verwenden können, um Änderungen an der UI vorzunehmen.           *
 *  -----------------------------------------------------------------------------------------------*
 */


/*
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeTodoView(viewModel: MainViewModel) {
    val todoListState = viewModel.todoListFlow.collectAsState(listOf())
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var popupControl by remember { mutableStateOf(false) }


        var searchQuery by remember { mutableStateOf("") }

        Row(verticalAlignment = Alignment.CenterVertically) {

            IconButton(
            onClick = { viewModel.searchRecords(searchQuery) }
        ) {
            Icon(Icons.Default.Search, contentDescription = "Search")
        }
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search query") }
            )

        }


        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { viewModel.deleteAllRecords() }) {
                    Text("Delete All")
                }
                Button(onClick = { popupControl = true }) {
                    Text("Add Note")
                }
            }

        }


        if (popupControl) {
            Popup(
                popupPositionProvider = CenteredPopupPositionProvider(),
                onDismissRequest = { popupControl = false },
                properties = PopupProperties(focusable = true)
            ) {
                AddNote({
                    popupControl = false
                    scope.launch {
                        lazyListState.scrollToItem(todoListState.value.size)
                    }
                }, viewModel)
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            state = lazyListState
        ) {
            items(
                items = todoListState.value,
                key = { todoItem -> todoItem.id },
                itemContent = { item ->
                    val currentItem by rememberUpdatedState(item)
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                                viewModel.removeRecord(currentItem)
                                true
                            } else false
                        }
                    )

                    if (dismissState.isDismissed(DismissDirection.EndToStart) ||
                        dismissState.isDismissed(DismissDirection.StartToEnd)){
                        viewModel.removeRecord(item)
                    }

                    SwipeToDismiss(
                        state = dismissState,
                        modifier = Modifier
                            .padding(vertical = 1.dp)
                            .animateItemPlacement(),
                        directions = setOf(
                            DismissDirection.StartToEnd,
                            DismissDirection.EndToStart
                        ),
                        dismissThresholds = { direction ->
                            FractionalThreshold(
                                if (direction == DismissDirection.StartToEnd) 0.66f else 0.50f
                            )
                        },
                        background = {
                            SwipeBackground(dismissState)
                        },
                        dismissContent = {
                            TodoItemRow(item, todoListState, viewModel)
                        }
                    )
                }
            )
        }
    }
}

 */