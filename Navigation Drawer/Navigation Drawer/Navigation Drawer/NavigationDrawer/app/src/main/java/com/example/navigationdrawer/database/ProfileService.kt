package com.example.navigationdrawer.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// This is the service class that handles the communication with Firebase
class ProfileService {
    // Get a reference to the database
    private val database = Firebase.database
    // Get a reference to the "Profile" table
    private val profileRef = database.getReference("Profile")

    // This function writes a new profile to the database
    fun createProfile(profile: Profile) {
        profileRef.child(profile.id.toString()).setValue(profile)
    }

    // This function reads a profile from the database given an id
    fun getProfile(id: Int): LiveData<Profile> {
        // Create a LiveData object to observe the data changes
        val profileLiveData = MutableLiveData<Profile>()
        // Attach a listener to the profile reference
        profileRef.child(id.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Get the profile data from the snapshot
                val profile = snapshot.getValue(Profile::class.java)
                // Update the LiveData value with the profile data
                profileLiveData.value = profile
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
        // Return the LiveData object
        return profileLiveData
    }
}
