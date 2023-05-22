package com.example.navigationdrawer.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// This is the service class that handles the communication with Firebase
class RoomService {
    // Get a reference to the database
    private val database = Firebase.database
    // Get a reference to the "Room" table
    private val roomRef = database.getReference("Room")

    // This function writes a new room to the database
    fun createRoom(room: Room) {
        roomRef.child(room.id.toString()).setValue(room)
    }

    // This function reads a room from the database given an id
    fun getRoom(id: Int): LiveData<Room> {
        // Create a LiveData object to observe the data changes
        val roomLiveData = MutableLiveData<Room>()
        // Attach a listener to the room reference
        roomRef.child(id.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Get the room data from the snapshot
                val room = snapshot.getValue(Room::class.java)
                // Update the LiveData value with the room data
                roomLiveData.value = room
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
        // Return the LiveData object
        return roomLiveData
    }
}
