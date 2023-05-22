package com.example.navigationdrawer.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// This is the ViewModel class that handles the business logic and exposes the UI state
class RoomViewModel : ViewModel() {
    // Create an instance of the RoomService class
    private val roomService = RoomService()
    // Create a UiState class that represents the UI state of the room screen
    data class UiState(
        val room: Room? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
    // Create a LiveData object to hold the UiState value
    private val _uiState = MutableLiveData<UiState>()
    // Expose the UiState value as an immutable LiveData object
    val uiState: LiveData<UiState> = _uiState

    // This function creates a new room with some random values and writes it to the database
    fun createRoom() {
        // Generate some random values for the room fields
        val roomNr = 3110
        val buildingNr = 1
        val floor = 3
        val description = "3. etage rechts"
        // Create a new room object with the random values
        val room = Room("1", roomNr, buildingNr, floor, description)
        // Write the profile to the database using the RoomService class
        roomService.createRoom(room)
    }

    // This function gets a room from the database given an id and updates the UiState accordingly
    fun getRoom(id: Int) {
        // Set the isLoading flag to true in the UiState
        _uiState.value = UiState(isLoading = true)
        // Get the room from the database using the RoomService class and observe the changes
        roomService.getRoom(id).observeForever { room ->
            // Set the isLoading flag to false and update the profile value in the UiState
            _uiState.value = UiState(room = room, isLoading = false)
        }
    }
}
