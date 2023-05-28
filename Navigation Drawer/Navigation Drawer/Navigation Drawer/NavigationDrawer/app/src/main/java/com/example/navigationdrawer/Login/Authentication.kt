package com.example.navigationdrawer.Login

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Authentication {

    // to get the currentUser -> is a firebase user
    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    // Function to check if the user is going to be logged in or not
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    // to get the user id
    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    // function that is going to create the actual user
    suspend fun createUser(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }.await()

    }


    // function that is going to help us to log in a user
    suspend fun logIn(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }.await()

    }


}

