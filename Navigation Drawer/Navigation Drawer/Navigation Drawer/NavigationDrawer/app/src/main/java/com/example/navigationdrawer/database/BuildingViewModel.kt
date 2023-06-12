package com.example.navigationdrawer.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// This is the ViewModel class that handles the business logic and exposes the UI state
class BuildingViewModel : ViewModel() {
    // Create an instance of the BuildingService class
    private val buildingService = BuildingService()
    // Create a UiState class that represents the UI state of the building screen
    data class UiState(
        val building: Building? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
    // Create a LiveData object to hold the UiState value
    private val _uiState = MutableLiveData<UiState>()
    // Expose the UiState value as an immutable LiveData object
    val uiState: LiveData<UiState> = _uiState

    // This function creates a new building with some random values and writes it to the database
    fun createBuilding(){
        // Generate some random values for the profile fields
        val buildingNr = 1
        val name = "Hauptgebaude"
        // Create a new building object with the random values
        val building = Building("1", buildingNr, name)
        // Write the building to the database using the BuildingService class
        buildingService.createBuilding(building)
    }

    // This function gets a building from the database given an id and updates the UiState accordingly
    fun getBuilding(id: Int) {
        // Set the isLoading flag to true in the UiState
        _uiState.value = UiState(isLoading = true)
        // Get the profile from the database using the ProfileService class and observe the changes
        buildingService.getBuilding(id).observeForever { building ->
            // Set the isLoading flag to false and update the profile value in the UiState
            _uiState.value = UiState(building = building, isLoading = false)
        }
    }
}
