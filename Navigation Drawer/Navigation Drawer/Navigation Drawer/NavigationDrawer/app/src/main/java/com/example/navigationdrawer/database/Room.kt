package com.example.navigationdrawer.database

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    @DocumentId val id: String = "",
    @PropertyName("roomNr") val roomNr: Int = 0,
    @PropertyName("buildingNr") val buildingNr: Int = 0,
    @PropertyName("floor") val floor: Int = 0,
    @PropertyName("description") val description: String = ""
) : Parcelable