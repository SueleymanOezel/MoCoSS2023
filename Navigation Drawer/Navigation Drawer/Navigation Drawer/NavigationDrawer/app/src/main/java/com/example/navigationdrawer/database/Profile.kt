package com.example.navigationdrawer.database

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

// A data class that represents a user profile
@Parcelize
data class Profile(
    @DocumentId val id: String = "",
    @PropertyName("name") val name: String = "",
    @PropertyName("email") val email: String = ""
) : Parcelable
