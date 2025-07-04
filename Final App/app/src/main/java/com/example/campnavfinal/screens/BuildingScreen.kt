package com.example.campnavfinal.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.campnavfinal.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.campnavfinal.mvvm.ImageData




// Funktion für den Bildschirm, der den Gebäudeplan der TH Köln Campus GM enthält
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BuildingScreen() {
    // Liste der Bilder mit den zugehörigen Informationen
    val imageList = listOf(
        ImageData(
            R.drawable.hg_eg_ost,
            "Die Etage beherbergt das Archiv, Lager, den technischen Betriebsdienst sowie die Zentralwerkstätten für Lehre und Forschung.",
            "Hauptgebäude Erdgeschoss (Ost)t"
        ),

        ImageData(
            R.drawable.hg_1e_ost,
            "In diesem Gebäude befindet sich die Information, das Dekanat, der Studienservice und der Kienbaumsaal. Zudem sind hier auch die Labore für Werkstoffprüfung, Robotertechnologie und Technomechanik zu finden.",
            "Hauptgebäude 1. Etage (Ost)"
        ),

        ImageData(
            R.drawable.hg_2e_ost,
            "Auf dieser Etage befinden sich die Lehrräume der Studiengänge der Informatik. Es gibt drei Übungsräume, zwei Seminarräume, zwei ADV-Terminalräume und PC-Tools",
            "Hauptgebäude 2. Etage (Ost)"
        ),

        ImageData(
            R.drawable.hg_3e_ost,
            "Auf dieser Etage befinden sich der Informatikraum, verschiedene Lehrräume sowie die Säle: Euro Engineering, FISIA Babcock und Unitechnik.",
            "Hauptgebäude 3. Etage (Ost)"
        ),

        ImageData(
            R.drawable.hg_eg_west,
            "Die Räumlichkeiten für Produktentwicklung, Produktion und Qualität sind auf dieser Etage zu finden.",
            "Hauptgebäude Erdgeschoss (West)"
        ),

        ImageData(
            R.drawable.hg_1e_west,
            "Die Etage beherbergt die Bereiche Automation & Industrial IT, Werkstoffkunde und angewandte Mathematik sowie verschiedene Lehrräume.",
            "Hauptgebäude 1. Etage (West)"
        ),

        ImageData(
            R.drawable.hg_2e_west,
            "Die folgenden Räume befinden sich auf dieser Etage: Physik, Informatik, Elektronik & Information Engineering, das Betriebswirtschaftliche Institut Gummersbach sowie verschiedene Lehrräume.",
            "Hauptgebäude 2. Etage (West)"
        ),

        ImageData(
            R.drawable.hg_3e_west,
            "Die Etage beherbergt die Räume Informatik, Distance Learning & Further Education sowie diverse Lehrräume.",
            "Hauptgebäude 3. Etage (West)"
        ),

        ImageData(
            R.drawable.gb_b_eg,
            "Die genannten Räume befinden sich auf dieser Etage: Mensa, Großhörsäle, FERCHAU Saal, BPW Saal und Physik.",
            "Gebäude B Erdgeschoss"
        ),

        ImageData(
            R.drawable.gb_b_1e,
            "Die Räumlichkeiten auf dieser Etage umfassen die Hochschulbibliothek, Großhörsäle und den OPITZ CONSULTING Saal.",
            "Gebäude B 1.Etage)"
        ),


        // usw. bei Bedarf kann man hier weitere Bilder hinzufügen ...
    )


    val searchQuery = remember { mutableStateOf("") }
    val filteredImageList = imageList.filter { imageData ->
        imageData.caption.contains(searchQuery.value, ignoreCase = true) ||
                imageData.title.contains(searchQuery.value, ignoreCase = true)
    }

    val scope = rememberCoroutineScope()
    val selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.building),
                            contentDescription = "Gebäude",
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            "Gebäudeplan",
                            modifier = Modifier.padding(start = 10.dp),
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                },
                backgroundColor = Color(0xFF89CFF0),
                contentColor = Color.Black
            )
        },

        // Suchleiste hat nicht funktioniert, wie erwartet. Deswegen auskommentiert.
    /*
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        TextField(
                            value = searchQuery.value,
                            onValueChange = { searchQuery.value = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Search") }
                        )
                    }
                },
                actions = {
                    if (searchQuery.value.isNotEmpty()) {
                        Text(
                            text = "${selectedIndex + 1} of ${filteredImageList.size}",
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        IconButton(
                            onClick = {
                                selectedIndex = (selectedIndex + 1) % filteredImageList.size
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Next"
                            )
                        }
                    }
                }
            )
        } */


    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredImageList) { imageData ->
                ImageListItem(imageData, isSelected = imageData == filteredImageList[selectedIndex])
            }
        }
        }


}


// Status des "Gefällt mir" Buttons
@Composable
fun ImageListItem(imageData: ImageData, isSelected: Boolean) {
    //val backgroundColor = if (isSelected) Color.Yellow else Color.Transparent
    val shape = MaterialTheme.shapes.medium

    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .clip(shape)
        // .background(backgroundColor)
    ) {
        // Anzeige des Bildes
        Image(
            painter = painterResource(id = imageData.imageRes),
            contentDescription = "Bild",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Anzeige der Bildunterschrift
        Text(
            text = imageData.caption,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )

        // "Gefällt mir"-Buton
        var isLiked by remember { mutableStateOf(false) }
        IconButton(
            onClick = { isLiked = !isLiked },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Icon(
                imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Like",
                tint = if (isLiked) Color.Red else LocalContentColor.current
            )
        }
    }
}

// Vorschau für den Gebäudeplan-Bildschirm
@Preview
@Composable
fun BuildingScreenPreview() {
    BuildingScreen()
}



/**  -----------------------------------------------------------------------------------------------
 *   Der folgende Code ist die erste Version des BuildingScreens.                                  *
 *   In der aktuellen Version im oberen Codeteil dieser Datei wurde die Suchfunktion überarbeitet  *
 *   und ein erster Ansatz für das Speichern der favorisierten Bilder im Profil gegeben.           *
 *   Da die Suchfunktion im aktuellen Composable noch nicht ganz so funktioniert wie geplant,      *
 *   bleibt die 1. Version auskommentiert, um Folgeprobleme zu vermeiden.                          *
 *  ---------------------------------------------------------------------------------------------- *
 */

/*
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BuildingScreen() {
    val imageList = listOf(
        ImageData(
            R.drawable.hg_eg_ost,
            "Bild 1",
            "Hauptgebäude Erdgeschoss (Ost)t"
        ),

        ImageData(
            R.drawable.hg_1e_ost,
            "In diesem Gebäude befindet sich die Information, das Dekanat, der Studienservice und der Kienbaumsaal. Zudem sind hier auch die Labore für Werkstoffprüfung, Robotertechnologie und Technomechanik zu finden.",
            "Hauptgebäude 1. Etage (Ost)"
        ),

        ImageData(
            R.drawable.hg_2e_ost,
            "Auf dieser Etage befinden sich die Lehrräume der Studiengänge der Informatik. Es gibt drei Übungsräume, zwei Seminarräume, zwei ADV-Terminalräume und PC-Tools",
            "Hauptgebäude 2. Etage (Ost)"
        ),

        ImageData(
            R.drawable.hg_3e_ost,
            "Bild 4",
            "Hauptgebäude 3. Etage (Ost)"
        ),

        ImageData(
            R.drawable.hg_eg_west,
            "Bild 5",
            "Hauptgebäude Erdgeschoss (West)"
        ),

        ImageData(
            R.drawable.hg_1e_west,
            "Bild 6",
            "Hauptgebäude 1. Etage (West)"
        ),

        ImageData(
            R.drawable.hg_2e_west,
            "Bild 7",
            "Hauptgebäude 2. Etage (West)"
        ),

        ImageData(
            R.drawable.hg_3e_west,
            "Bild 8",
            "Hauptgebäude 3. Etage (West)"
        ),

        ImageData(
            R.drawable.gb_b_eg,
            "Bild 9",
            "Gebäude B Erdgeschoss"
        ),

        ImageData(
            R.drawable.gb_b_1e,
            "Bild 10",
            "Gebäude B 1.Etage)"
        ),


        // Weitere Bilder hinzufügen...
    )

    val searchQuery = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        TextField(
                            value = searchQuery.value,
                            onValueChange = { searchQuery.value = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(text = "Search") }
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val filteredImageList = imageList.filter { imageData ->
                imageData.caption.contains(searchQuery.value, ignoreCase = true) ||
                        imageData.title.contains(searchQuery.value, ignoreCase = true)

            }
            filteredImageList.forEach { imageData ->
                ImageListItem(imageData)
                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ImageListItem(imageData: ImageData) {
    var isLiked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = imageData.imageRes),
            contentDescription = "Bild",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = imageData.caption,
            style = MaterialTheme.typography.subtitle1
        )
        IconButton(
            onClick = { isLiked = !isLiked },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Icon(
                imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Like",
                tint = if (isLiked) Color.Red else LocalContentColor.current
            )
        }
    }
}


 */


/*
// Zoombare Bilder-Galerie
@Composable
fun ImageListItem(imageData: ImageData) {

    // Zustand für den Herz-Status (rot oder nicht rot)
    var isLiked by remember { mutableStateOf(false) }

    val scale = remember { mutableStateOf(1f) }
    val rotation = remember { mutableStateOf(0f) }
    val offset = remember { mutableStateOf(Offset.Zero) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = imageData.title,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, gestureZoom, gestureRotation ->
                        scale.value *= gestureZoom
                        rotation.value += gestureRotation
                        offset.value += pan
                    }
                }
        ) {
            Image(
                painter = painterResource(id = imageData.imageRes),
                contentDescription = "Bild",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = scale.value,
                        scaleY = scale.value,
                        rotationZ = rotation.value,
                        translationX = offset.value.x,
                        translationY = offset.value.y
                    )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = imageData.caption,
            style = MaterialTheme.typography.subtitle1
        )
    }
}

 */


/*
// Zoomeffekt für Bilder 2. Versuch
@Composable
fun ZoomableImage(imageRes: Int, imageSize: IntSize) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(IntOffset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale *= zoom
                    val panOffset = pan.toOffset()
                    offset += panOffset.toIntOffset()
                }
            }
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x.toFloat(),
                    translationY = offset.y.toFloat()
                )
                .fillMaxSize()
        )
    }
}

private fun Offset.toIntOffset(): IntOffset {
    return IntOffset(x.toInt(), y.toInt())
}

private fun Offset.toOffset(): Offset {
    return Offset(x, y)
}


@Composable
fun ImageListItem(imageData: ImageData) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = imageData.title,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        ZoomableImage(
            imageRes = imageData.imageRes,
            imageSize = IntSize(200, 200) // Passen Sie die tatsächliche Größe des Bildes an
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = imageData.caption,
            style = MaterialTheme.typography.subtitle1
        )
    }
}
 */



