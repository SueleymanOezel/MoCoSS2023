package com.example.navigationdrawer.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MainViewModel(private val dao: FirestoreDao): ViewModel() {
    // Die LiveData Eigenschaft für das Profil des aktuellen Users
    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile> = _profile

    // Die LiveData Eigenschaft für die Liste der Gebäude
    private val _buildings = MutableLiveData<List<Building>>()
    val buildings: LiveData<List<Building>> = _buildings

    // Die LiveData Eigenschaft für die Liste der Räume im ausgewählten Gebäude
    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> = _rooms

    // Die LiveData Eigenschaft für den aktuellen Raum
    private val _currentRoom = MutableLiveData<CurrentRoom>()
    val currentRoom: LiveData<CurrentRoom> = _currentRoom

    // Eine Methode, um das Profil des aktuellen Users zu laden
    fun loadProfile() {
        viewModelScope.launch {
            val profile = dao.getProfile()
            if (profile != null) {
                _profile.value = profile
            }
        }
    }

    // Eine Methode, um das Profil des aktuellen Users zu aktualisieren
    fun updateProfile(profile: Profile) {
        viewModelScope.launch {
            try {
                dao.updateProfile(profile)
                _profile.value = profile
            } catch (e: Exception) {
                // Handle any errors
            }
        }
    }

    // Eine Methode, um die Liste der Gebäude zu laden
    fun loadBuildings() {
        viewModelScope.launch {
            val buildings = dao.getBuildings()
            _buildings.value = buildings
        }
    }

    // Eine Methode, um die Liste der Räume im ausgewählten Gebäude zu laden
    fun loadRoomsByBuilding(buildingNr: Int) {
        viewModelScope.launch {
            val rooms = dao.getRoomsByBuilding(buildingNr)
            _rooms.value = rooms
        }
    }

    // Eine Methode, um den aktuellen Raum zu setzen
    fun setCurrentRoom(room: Room, scannedCode: String) {
        _currentRoom.value = CurrentRoom(room, scannedCode)
    }
}
