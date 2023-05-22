package com.example.navigationdrawer.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// This is the ViewModel class that handles the business logic and exposes the UI state
class ProfileViewModel : ViewModel() {
    // Create an instance of the ProfileService class
    private val profileService = ProfileService()
    // Create a UiState class that represents the UI state of the profile screen
    data class UiState(
        val profile: Profile? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
    // Create a LiveData object to hold the UiState value
    private val _uiState = MutableLiveData<UiState>()
    // Expose the UiState value as an immutable LiveData object
    val uiState: LiveData<UiState> = _uiState

    // This function creates a new profile with some random values and writes it to the database
    fun createProfile() {
        // Generate some random values for the profile fields
        val name = "Müller"
        val email = "$name@gmail.com".lowercase()
        // Create a new profile object with the random values
        val profile = Profile("1", name, email)
        // Write the profile to the database using the ProfileService class
        profileService.createProfile(profile)
    }

    // This function gets a profile from the database given an id and updates the UiState accordingly
    fun getProfile(id: Int) {
        // Set the isLoading flag to true in the UiState
        _uiState.value = UiState(isLoading = true)
        // Get the profile from the database using the ProfileService class and observe the changes
        profileService.getProfile(id).observeForever { profile ->
            // Set the isLoading flag to false and update the profile value in the UiState
            _uiState.value = UiState(profile = profile, isLoading = false)
        }
    }
}
