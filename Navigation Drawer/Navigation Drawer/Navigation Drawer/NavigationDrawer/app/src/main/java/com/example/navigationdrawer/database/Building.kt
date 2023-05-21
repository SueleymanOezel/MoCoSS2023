package com.example.navigationdrawer.database

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Building(
    @DocumentId val id: String = "",
    @PropertyName("buildingNr") val buildingNr: Int = 0,
    @PropertyName("name") val name: String = ""
) : Parcelable