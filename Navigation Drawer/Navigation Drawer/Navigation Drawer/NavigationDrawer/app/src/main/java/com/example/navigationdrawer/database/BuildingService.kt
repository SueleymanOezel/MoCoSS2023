package com.example.navigationdrawer.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// This is the service class that handles the communication with Firebase
class BuildingService {
    // Get a reference to the database
    private val database = Firebase.database
    // Get a reference to the "Building" table
    private val buildingRef = database.getReference("Building")

    // This function writes a new building to the database
    fun createBuilding(building: Building) {
        buildingRef.child(building.id.toString()).setValue(building)
    }

    // This function reads a building from the database given an id
    fun getBuilding(id: Int): LiveData<Building> {
        // Create a LiveData object to observe the data changes
        val buildingLiveData = MutableLiveData<Building>()
        // Attach a listener to the building reference
        buildingRef.child(id.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Get the building data from the snapshot
                val building = snapshot.getValue(Building::class.java)
                // Update the LiveData value with the building data
                buildingLiveData.value = building
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
        // Return the LiveData object
        return buildingLiveData
    }
}
