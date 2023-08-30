package com.example.campnavfinal.mvvm

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campnavfinal.database.home.TodoDao
import com.example.campnavfinal.database.home.TodoItem
import com.example.campnavfinal.database.room.RaumDao
import com.example.campnavfinal.database.room.RaumEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(

    private val todoDao: TodoDao,
    private val raumDao: RaumDao

    ) : ViewModel() {

    private var todoList = mutableStateListOf<TodoItem>()
    private val _todoListFlow = MutableStateFlow(todoList)

    val todoListFlow: StateFlow<List<TodoItem>> get() = _todoListFlow
    private var postExecute: (() -> Unit)? = null

    init {
        loadTodoList()
    }

    /*
    private var roomList = mutableStateListOf<Room>()
    private val _roomListFlow = MutableStateFlow(roomList)
    val roomListFlow: StateFlow<List<Room>> get() = _roomListFlow

    init {
        loadRoomList()
    }

    private fun loadRoomList() {
        viewModelScope.launch {
            roomDao.getAllRooms().collect { rooms ->
                roomList = rooms.toMutableStateList()
                _roomListFlow.value = roomList
            }
        }
    }

     */

    private fun loadTodoList() {
        viewModelScope.launch {
            todoDao.getAll().collect {
                todoList = it.toMutableStateList()
                _todoListFlow.value = todoList
                postExecute?.invoke()
            }
        }
    }

    fun setUrgent(index: Int, value: Boolean) {
        val editedTodo = todoList[index].copy(urgent = value)
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.update(editedTodo)
            postExecute = null
        }
    }


    fun deleteAllRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.nukeTable()
            postExecute = null
        }
    }

    fun searchRecords(query: String) {
        viewModelScope.launch {
            val searchResult = todoDao.search(query)
            todoList = searchResult.toMutableStateList()
            _todoListFlow.value = todoList
            postExecute?.invoke()
        }
    }



    fun addRecord(titleText: String, urgency: Boolean, postInsert: (() -> Unit)? = null) {
        viewModelScope.launch(Dispatchers.IO) {  }
        val id = todoList.lastOrNull()?.id ?: -1
        val todoItem = TodoItem(id + 1, titleText, urgency)
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.insertAll(todoItem)
            postExecute = postInsert
        }
    }

    fun removeRecord(todoItem: TodoItem, postRemove: (() -> Unit)? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.delete(todoItem)
            postExecute = postRemove
        }
    }


    suspend fun initializeRoomData() {
        try {

            val rooms: List<RaumEntity> = listOf(

                RaumEntity(
                    roomNumber = 1101,
                    roomName = "BAFÖG-Amt",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "1.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur ersten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 1120,
                    roomName = "Fakultätssektreteriat",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "1.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur ersten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 1122,
                    roomName = "Kienbaumsaal",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "2.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur ersten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 1123,
                    roomName = "Fachschaft",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "1.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur ersten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 1126,
                    roomName = "Prüfungsamt",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "1.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur ersten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 1127,
                    roomName = "Studierendensekreteriat",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "1.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur ersten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 2101,
                    roomName = "Übungsraum",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "2.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur zweiten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 2102,
                    roomName = "Übungsraum",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "2.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur zweiten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 2103,
                    roomName = "Übungsraum",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "2.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur zweiten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 2106,
                    roomName = "WI-PC-Pool I",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "2.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur zweiten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 2107,
                    roomName = "WI-PC-Pool II",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "2.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur zweiten Etage hoch. Dort befindet sich der Raum."
                ),


                RaumEntity(
                    roomNumber = 2109,
                    roomName = "ADV-Terminalraum II",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "2.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur zweiten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 2110,
                    roomName = "ADV-Terminalraum I",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "2.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur zweiten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 2113,
                    roomName = "Seminarraum",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "2.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur zweiten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 2114,
                    roomName = "Seminarraum",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "2.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur zweiten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 3110,
                    roomName = "Seminarraum",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "3.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur dritten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 3111,
                    roomName = "Seminarraum",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "3.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur dritten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 3112,
                    roomName = "Seminarraum",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "3.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur dritten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 3113,
                    roomName = "Mathe-PC-Pool",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Links",
                    floor = "3.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen die Treppen zur dritten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 3221,
                    roomName = "KTDS-Labor I",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Rechts",
                    floor = "3.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach rechts und laufen die Treppen zur dritten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 3222,
                    roomName = "KTDS-Labor II",
                    buildingNumber = 1,
                    buildingName = "Hauptgebäude Rechts",
                    floor = "3.Etage",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach rechts und laufen die Treppen zur dritten Etage hoch. Dort befindet sich der Raum."
                ),

                RaumEntity(
                    roomNumber = 8300,  // 0 nicht möglich deswegen 8, muss noch korrigiert werden
                    roomName = "Mensa Aufenthaltszone",
                    buildingNumber = 2,
                    buildingName = "Hörsaalgebäude Links",
                    floor = "Erdgeschoss",
                    roomText = "Vom Haupteingang des Hörsaalgebäudes gehen Sie nach links. Dort befindet sich die Mensa."
                ),

                RaumEntity(
                    roomNumber = 8301,  // 0 nicht möglich deswegen 8, muss noch korrigiert werden
                    roomName = "Mensa Ausgabezone",
                    buildingNumber = 2,
                    buildingName = "Hörsaalgebäude Links",
                    floor = "Erdgeschoss",
                    roomText = "Vom Haupteingang des Hauptgebäudes gehen Sie nach links. Dort befindet sich die Mensa."
                ),

                )

            raumDao.insertRooms(rooms)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // Methode, um Raumtext basierend auf der Raumnummer abzurufen (für RoomScreen)
   fun getRoomText(roomNumber: String): String {
        return when  {

            roomNumber.length != 4
            -> "Dies ist eine ungültige Eingabe. Es gibt keinen Raum mit der Nummer $roomNumber am Campus Gummersbach. "

            roomNumber == "1101"
            -> "Raum $roomNumber \nBAFÖG-Amt" +
                    "\n\n\nDas Bafög-Amt befindet sich im Ost-Trakt des Hauptgebäudes auf der 1. Etage." +
                    "\nVom Haupteingang des Hauptgebäudes gehen Sie nach links bis zur Information und dann nach rechts. " +
                    "\nIm vorletzten Raum auf der rechten Seite befindet sich das BAFÖG-Amt." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen erstmal die Treppen hoch und dann ebenfalls nach rechts bis zum vorletzten Raum."

            roomNumber == "1120"
            -> "Raum $roomNumber \nFakultätssekreteriat" +
                    "\n\n\nDas Faktultätssektreteriat für Informatiker befindet sich im Ost-Trakt des Hauptgebäudes auf der 1. Etage." +
                    "\nVom Haupteingang des Hauptgebäudes gehen Sie nach links bis zur Information und dann wieder nach links. " +
                    "\nIm letzten Raum auf der rechten Seite befindet sich das Fakutltätssekreteriat." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen erstmal die Treppen rechts hoch " +
                    "\nund dann ebenfalls geradeaus den Flur bis zum letzten Raum auf der rechten Seite."

            roomNumber == "1122"
            -> "Raum $roomNumber \nKienbaum Saal" +
                    "\n\n\nDer Kienbaum Saal befindet sich im Ost-Trakt des Hauptgebäudes auf der 1. Etage." +
                    "\nVom Haupteingang des Hauptgebäudes gehen Sie nach links bis zur Information und dann wieder nach links. " +
                    "\nIm letzten Raum auf der linken Seite befindet sich der Kienbaum Saal." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen erstmal die Treppen rechts hoch " +
                    "\nund dann ebenfalls geradeaus den Flur bis zum letzten Raum auf der linken Seite."

            roomNumber == "1123"
            -> "Raum $roomNumber \nFachschaft" +
                    "\n\n\nDas Prüfungsamt befindet sich im Ost-Trakt des Hauptgebäudes auf der 1. Etage." +
                    "\nVom Haupteingang des Hauptgebäudes gehen Sie nach links bis zur Information und dann wieder nach links. " +
                    "\nDer vorletzte Raum auf der linken Seite ist der Raum der Fachschaft." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen erstmal die Treppen rechts hoch " +
                    "\nund dann ebenfalls geradeaus den Flur  bis zum vorletzten Raum auf der linken Seite."

            roomNumber == "1126"
            -> "Raum $roomNumber \nPrüfungsamt" +
                    "\n\n\nDas Prüfungsamt befindet sich im Ost-Trakt des Hauptgebäudes auf der 1. Etage." +
                    "\nVom Haupteingang des Hauptgebäudes gehen Sie nach links bis zur Information und dann wieder nach links. " +
                    "\nDer vierte Raum auf der linken Seite ist der Raum des Prüfungsamts." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen erstmal die Treppen rechts hoch " +
                    "\nund dann ebenfalls geradeaus den Flur bis zum vierten Raum auf der linken Seite."

            roomNumber == "1127"
            -> "Raum $roomNumber \nStudierendensekreteriat" +
                    "\n\n\nDas Studierendensekreteriat befindet sich im Ost-Trakt des Hauptgebäudes auf der 1. Etage." +
                    "\nVom Haupteingang des Hauptgebäudes gehen Sie nach links bis zur Information und dann wieder nach links. " +
                    "\nDer dritte Raum auf der linken Seite ist der Raum des Studierendensekreteriats." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen erstmal die Treppen rechts hoch " +
                    "\nund dann ebenfalls den Flur geradeaus  bis zum dritten Raum auf der linken Seite."

            roomNumber == "2101"
            -> "Raum $roomNumber \nÜbungsraum" +
                    "\n\n\n Der Übungsraum befindet sich im Ost-Trakt des Hauptgebäudes auf der 2. Etage." +
                    "\nVom Haupteingang dea Hauptgebäudes gehen Sie nach links und laufen die Treppe eine Etage hoch. " +
                    "\nVon dort laufen Sie zwei mal nach rechts und dann den Flur bis zum Ende durch. Zu Ihrer linken befindet sich der Übungsraum." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen zweimal die Treppen auf der rechten Seite hoch " +
                    "\nund dann ebenfalls rechts in den Flur durch bis zum letzten Raum auf der linken Seite."

            roomNumber == "2102"
            -> "Raum $roomNumber \nÜbungsraum" +
                    "\n\n\nDer Übungsraum befindet sich im Ost-Trakt des Hauptgebäudes auf der 2. Etage." +
                    "\nVom Haupteingang dea Hauptgebäudes gehen Sie nach links und laufen die Treppe eine Etage hoch. " +
                    "\nVon dort laufen Sie zwei mal nach rechts und dann den Flur bis zum vorletzten Raum durch. Zu Ihrer linken befindet sich der Übungsraum." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen zweimal die Treppen auf der rechten Seite hoch " +
                    "\nund dann ebenfalls rechts in den Flur durch bis zum vorletzten Raum auf der linken Seite."

            roomNumber == "2103"
            -> "Raum $roomNumber \nÜbungsraum" +
                    "\n\n\nDer Übungsraum befindet sich im Ost-Trakt des Hauptgebäudes auf der 2. Etage." +
                    "\nVom Haupteingang dea Hauptgebäudes gehen Sie nach links und laufen die Treppe eine Etage hoch. " +
                    "\nVon dort laufen Sie zwei mal nach rechts und dann den Flur bis zum dritten Raum. Zu Ihrer linken befindet sich der Übungsraum." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen zweimal die Treppen auf der rechten Seite hoch " +
                    "\nund dann ebenfalls rechts in den Flur durch bis zum dritten Raum auf der linken Seite."

            roomNumber == "2106"
            -> "Raum $roomNumber \nWI-PC-Pool I" +
                    "\n\n\nDer WI-PC-Pool I befindet sich im Ost-Trakt des Hauptgebäudes auf der 2. Etage." +
                    "\nVom Haupteingang dea Hauptgebäudes gehen Sie nach links und laufen die Treppe eine Etage hoch. " +
                    "\nVon dort laufen Sie nach rechts und dann links zum Raum vor dem Flur. Zu Ihrer rechteb befindet sich der WI-PC-Pool I." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen zweimal die Treppen auf der rechten Seite hoch " +
                    "\nund dann ebenfalls rechts und dann links bis zum Raum vor dem Flur auf der linken Seite."

            roomNumber == "2106"
            -> "Raum $roomNumber \nWI-PC-Pool II" +
                    "\n\n\nDer WI-PC-Pool II befindet sich im Ost-Trakt des Hauptgebäudes auf der 2. Etage." +
                    "\nVom Haupteingang dea Hauptgebäudes gehen Sie nach links und laufen die Treppe eine Etage hoch. " +
                    "\nVon dort laufen Sie nach rechts und dann links zum Raum hinter der Flurtür. Zu Ihrer rechteb befindet sich der WI-PC-Pool II." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen zweimal die Treppen auf der rechten Seite hoch " +
                    "\nund dann ebenfalls rechts und dann links bis zum Raum hinter der Flurtür auf der linken Seite."

            roomNumber == "2109"
            -> "Raum $roomNumber \nADV-Terminalraum II" +
                    "\n\n\nDer ADV-Terminalraum II befindet sich im Ost-Trakt des Hauptgebäudes auf der 2. Etage." +
                    "\nVom Haupteingang dea Hauptgebäudes gehen Sie nach links und laufen die Treppe eine Etage hoch. " +
                    "\nVon dort laufen Sie nach rechts und dann links den Flur durch bis zum letzten Raum. Zu Ihrer rechten befindet sich der ADV-Raum." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen zweimal die Treppen auf der rechten Seite hoch " +
                    "\nund dann ebenfalls rechts und dann links in den Flur durch bis zum letzten Raum auf der rechten Seite."

            roomNumber == "2110"
            -> "Raum $roomNumber \nADV-Terminalraum I" +
                    "\n\n\nDer ADV-Terminalraum I befindet sich im Ost-Trakt des Hauptgebäudes auf der 2. Etage." +
                    "\nVom Haupteingang dea Hauptgebäudes gehen Sie nach links und laufen die Treppe eine Etage hoch. " +
                    "\nVon dort laufen Sie nach rechts und dann links den Flur durch bis zum letzten Raum. Zu Ihrer linken befindet sich der ADV-Raum." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen zweimal die Treppen auf der rechten Seite hoch " +
                    "\nund dann ebenfalls rechts und dann links in den Flur durch bis zum letzten Raum auf der linken Seite."

            roomNumber == "2113"
            -> "Raum $roomNumber \nSeminarraum" +
                    "\n\n\nDer Seminarraum befindet sich im Ost-Trakt des Hauptgebäudes auf der 2. Etage." +
                    "\nVom Haupteingang dea Hauptgebäudes gehen Sie nach links und laufen die Treppe eine Etage hoch. " +
                    "\nVon dort laufen Sie nach rechts und dann links in den Flur bis zum zweiten Raum nach den Toiletten. Zu Ihrer linken befindet sich der Seminarraum." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen zweimal die Treppen auf der rechten Seite hoch " +
                    "\nund dann ebenfalls rechts und dann links in den Flur bis zum zweiten Raum nach den Toiletten auf der linken Seite."

            roomNumber == "2114"
            -> "Raum $roomNumber \nSeminarraum" +
                    "\n\n\nDer Seminarraum befindet sich im Ost-Trakt des Hauptgebäudes auf der 2. Etage." +
                    "\nVom Haupteingang dea Hauptgebäudes gehen Sie nach links und laufen die Treppe eine Etage hoch. " +
                    "\nVon dort laufen Sie nach rechts und dann links in den Flur bis zum ersten Raum nach den Toiletten. Zu Ihrer linken befindet sich der Seminarraum." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen zweimal die Treppen auf der rechten Seite hoch " +
                    "\nund dann ebenfalls rechts und dann links in den Flur bis zum ersten Raum nach den Toiletten auf der linken Seite."

            roomNumber == "3110"
            -> "Raum $roomNumber \nSeminarraum" +
                    "\n\n\nDer Seminarraum befindet sich im Ost-Trakt des Hauptgebäudes auf der 3. Etage." +
                    "\nVom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen zweimal die Treppen bis zur 3. Etage hoch." +
                    "\nVon dort laufen Sie nach rechts und dann links den Flur durch. Im letzen Raum am Ende des Flurs auf der linken Seite befindet sich der Seminarraum." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen dreimal die Treppen auf der rechten Seite hoch " +
                    "\nund von dort ebenfalls nach rechts und dann links den Flur durch bis zum letzten Raum auf der linken Seite."

            roomNumber == "3111"
            -> "Raum $roomNumber \nSeminarraum" +
                    "\n\n\nDer Seminarraum befindet sich im Ost-Trakt des Hauptgebäudes auf der 3. Etage." +
                    "\nVom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen zweimal die Treppen bis zur 3. Etage hoch." +
                    "\nVon dort laufen Sie nach rechts und dann links den Flur durch. Im vorletzen Raum am Ende des Flurs auf der linken Seite befindet sich der Seminarraum." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen dreimal die Treppen auf der rechten Seite hoch " +
                    "\nund von dort ebenfalls nach rechts und dann links den Flur durch bis zum vorletzten Raum auf der linken Seite."

            roomNumber == "3112"
            -> "Raum $roomNumber \nSeminarraum" +
                    "\n\n\nDer Seminarraum befindet sich im Ost-Trakt des Hauptgebäudes auf der 3. Etage." +
                    "\nVom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen zweimal die Treppen bis zur 3. Etage hoch." +
                    "\nVon dort laufen Sie nach rechts und dann links den Flur entlang. Im dritten Raum nach den Toiletten auf der linken Seite befindet sich der Seminarraum." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen dreimal die Treppen auf der rechten Seite hoch " +
                    "\nund von dort ebenfalls nach rechts und dann links den Flur entlang bis zum dritten Raum nach den Toiletten auf der linken Seite."

            roomNumber == "3113"
            -> "Raum $roomNumber \nMathe-PC-Pool" +
                    "\n\n\nDer Mathe-PC-Pool befindet sich im Ost-Trakt des Hauptgebäudes auf der 3. Etage." +
                    "\nVom Haupteingang des Hauptgebäudes gehen Sie nach links und laufen zweimal die Treppen bis zur 3. Etage hoch." +
                    "\nVon dort laufen Sie nach rechts und dann links den Flur entlang. Im ersten Raum nach den Toiletten auf der linken Seite befindet sich der Seminarraum." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen dreimal die Treppen auf der rechten Seite hoch " +
                    "\nund von dort ebenfalls nach rechts und dann links den Flur entlang bis zum ersten Raum nach den Toiletten auf der linken Seite."

            roomNumber == "3221"
            -> "Raum $roomNumber \nKTDS-Labor I" +
                    "\n\n\nDas KTDS-Labor I befindet sich im West-Trakt des Hauptgebäudes auf der 3. Etage." +
                    "\nVom Haupteingang des Hauptgebäudes gehen Sie nach rechts und laufen zweimal die Treppen bis zur 3. Etage hoch." +
                    "\nVon dort laufen Sie nach links den Flur durch. Im vorletzten Raum am Ende des Flurs auf der rechten Seite befindet sich das KTDS-Labor I." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen dreimal die Treppen auf der linken Seite hoch " +
                    "\nund von dort ebenfalls nach links den Flur durch bis zum vorletzten Raum auf der rechten Seite."

            roomNumber == "3222"
            -> "Raum $roomNumber \nKTDS-Labor II" +
                    "\n\n\nDas KTDS-Labor II befindet sich im West-Trakt des Hauptgebäudes auf der 3. Etage." +
                    "\nVom Haupteingang des Hauptgebäudes gehen Sie nach rechts und laufen zweimal die Treppen bis zur 3. Etage hoch." +
                    "\nVon dort laufen Sie nach links den Flur durch. Im vorletzten Raum am Ende des Flurs auf der rechten Seite befindet sich das KTDS-Labor II." +
                    "\n\nVom Hintereingang des Hauptgebäudes laufen Sie hingegen dreimal die Treppen auf der linken Seite hoch " +
                    "\nund von dort ebenfalls nach links den Flur durch bis zum vorletzten Raum auf der rechten Seite."

            roomNumber == "0301"
            -> "Raum $roomNumber \nMensa" +
                    "\nAusgabezone / Kassenbereich" +
                    "\n\n\nDie Ausgabezone der Mensa befindet sich im Erdgeschoss des Hörsaalgebäudes." +
                    "\nVom Haupteingang des Hörsaalgebäudes gehen Sie direkt nach links und dann nach rechts." +
                    "\nHier können Sie sich Ihr Mittagsessen aussuchen und anschließend im Kassenbereich zahlen. :)"

            roomNumber == "030o"
            -> "Raum $roomNumber \nMensa" +
                    "\nAufenthaltszone" +
                    "\n\n\nDie Aufenthaltszone der Mensa befindet sich im Erdgeschoss des Hörsaalgebäudes." +
                    "\nVom Haupteingang des Hörsaalgebäudes gehen Sie direkt nach links." +
                    "\nHier können Sie  Ihr Mittagsessenin Ruhe auf einem der Plätze genießen. :)"

            else -> "Es wurde kein Text für  Raum $roomNumber gefunden. Es gibt diesen Raum am Standort Gummersbach der TH Köln nicht." +
                    "\n\n\nWeiteres Vorgehen: " +
                    "\n\n- Gehen Sie zum vorherigen Bildschirm zurück" +
                    "und überprüfen Sie, ob Sie wirklich die richtige Raumnummer eingegeben haben. " +
                    "\n\n- Falls Sie die Raumnummer vergessen haben, schauen Sie in Ihre Notizen, welche Raumnummer Sie für das entsprechende Modul hinterlegt haben." +
                    "\n\n- Falls Sie noch keine Notizen hinterlegt haben, können Sie sich den Gebäudeplan anschauen."

        }
    }





/*
    fun getRoomText(roomNumber: String): String {
        val roomEntity = roomDao.getRoomByNumber(roomNumber)
        return roomEntity?.roomText ?: "Es wurde kein Text für diesen Raum gefunden"
    }

    fun addRoom(roomNumber: String, roomText: String) {
        val roomEntity = RoomEntity(roomNumber, roomText)
        roomDao.insertRoom(roomEntity)
    }
    */



    /*
    suspend fun getRoomText(roomNumber: String): String {
        val room = roomDao.getRoomByNumber(roomNumber)
        return room?.text ?: "Es wurde kein Text für diesen Raum gefunden"
    }

     */
}



