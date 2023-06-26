package com.example.scan.mvvm

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scan.database.home.TodoDao
import com.example.scan.database.home.TodoItem
import com.example.scan.database.room.RoomDao
import com.example.scan.database.room.RoomEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(

    private val todoDao: TodoDao,
    // private val roomDao: RoomDao

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



    // Methode, um Raumtext basierend auf der Raumnummer abzurufen (für RoomScreen)
   fun getRoomText(roomNumber: String): String {
        return when  {
            roomNumber.length != 4
            -> "Dies ist eine ungültige Eingabe. Es gibt keinen Raum mit der Nummer $roomNumber am Campus Gummersbach. "

            roomNumber == "1101"
            -> "Raum $roomNumber \nBAFÖG-Amt  " +
                    "\n\n\nDas Bafög-Amt befindet sich im Ost-Trakt des Hauptgebäudes auf der 1. Etage."

            roomNumber == "2101"
            -> "Raum $roomNumber \nÜbungsraum" +
                    "\n\n\n Der Übungsraum befindet sich im Ost-Trakt des Hauptgebäudes auf der 2. Etage."

            roomNumber == "2102"
            -> "Raum $roomNumber \nÜbungsraum" +
                    "\n\n\nDer Übungsraum befindet sich im Ost-Trakt des Hauptgebäudes auf der 2. Etage."

            roomNumber == "2103"
            -> "Raum $roomNumber \nÜbungsraum" +
                    "\n\n\nDer Übungsraum befindet sich im Ost-Trakt des Hauptgebäudes auf der 2. Etage."

            roomNumber == "3221"
            -> "Raum $roomNumber \nKTDS-Labor I" +
                    "\n\n\nDas KTDS-Labor befindet sich im West-Trakt des Hauptgebäudes auf der 3. Etage."

            roomNumber == "KTDS-Labor II"
            -> "Raum $roomNumber \nÜbungsraum" +
                    "\n\n\nDas KTDS-Labor befindet sich im West-Trakt des Hauptgebäudes auf der 3. Etage."


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



