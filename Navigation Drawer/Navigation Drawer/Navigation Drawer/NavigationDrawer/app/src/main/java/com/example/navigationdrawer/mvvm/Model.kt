package com.example.navigationdrawer.mvvm

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


// Die Datenklasse für das Profil des Users
data class Profile(
    val id: String,
    val name: String,
    val email: String
)

// Die Datenklasse für einen Raum
data class Room(
    val id: String,
    val roomNr: Int,
    val buildingNr: Int,
    val floor: Int,
    val description: String
)

// Die Datenklasse für ein Gebäude
data class Building(
    val id: String,
    val buildingNr: Int,
    val name: String
)

// Die Datenklasse für den aktuellen Raum
data class CurrentRoom(
    val room: Room,
    val scannedCode: String
)


// Eine Klasse, die den Zustand zur Aktualisierung für die Anmeldung (Sign Up-Formular) eines Benutzers repräsentiert
class SignUpState {

    /**
     * mutableStateOf-Funktion ermöglicht es, den Zustand einer Variable zu verfolgen
     * und automatisch die betroffenen UI-Elemente neu zu rendern, wenn sich der Zustand ändert.
     * Die private set-Anweisung stellt sicher, dass die Variablen nur innerhalb der Klasse geändert werden können.
     */

    var firstName: String by mutableStateOf("")
        private set
    var lastName: String by mutableStateOf("")

    var emailAddress: String by mutableStateOf("")
        private set
    var password: String by mutableStateOf("")
        private set
    var confirmPassword: String by mutableStateOf("")
        private set
    var checked: Boolean by mutableStateOf(false)
        private set

    /*
     Eine berechnete Eigenschaft, die den Zustand des Buttons "Sign Up" aktiviert
     -> enableButton gibt true zurück, wenn alle erforderlichen Felder (firstName, lastName, confirmPassword und password)
     ausgefüllt sind.
    */
    val enableButton = firstName.isNotBlank() && lastName.isNotBlank() &&
            confirmPassword.isNotBlank() && password.isNotBlank()


    // Funktionen zum Aktualisieren der Werte der einzelnen Eigenschaften

    fun firstNameChenged(newValue: String){
        firstName = newValue

    }

    fun lastNameChange(newValue: String) {
        lastName = newValue
    }

    fun emailAddressChange(newValue: String) {
        emailAddress = newValue
    }

    fun password(newValue: String) {
        password = newValue
    }

    fun confirmPasswordChange(newValue: String) {
        confirmPassword = newValue
    }
    fun checkedChange(newValue: Boolean) {
        checked = newValue
    }


}


// Die abstrakte Klasse für die Datenzugriffsschicht
abstract class DatabaseDao {

    // Eine Methode, um das Profil des aktuellen Users zu bekommen
    abstract suspend fun getProfile(): Profile?

    // Eine Methode, um das Profil des aktuellen Users zu aktualisieren
    abstract suspend fun updateProfile(profile: Profile)

    // Eine Methode, um eine Liste von Räumen nach Gebäudenummer zu bekommen
    abstract suspend fun getRoomsByBuilding(buildingNr: Int): List<Room>

    // Eine Methode, um eine Liste von Gebäuden zu bekommen
    abstract suspend fun getBuildings(): List<Building>

}

// Eine konkrete Klasse, die die Methoden für die Datenzugriffsschicht mit Firestore implementiert
class FirestoreDao(private val db: FirebaseFirestore) : DatabaseDao() {

    // Eine Eigenschaft, um auf die Firebase Authentication Instanz zuzugreifen
    private val auth = FirebaseAuth.getInstance()

    // Eine Eigenschaft, um die ID des aktuellen Users zu bekommen oder null, wenn nicht eingeloggt
    private val userId: String?
        get() = auth.currentUser?.uid


    override suspend fun getProfile(): Profile? {
        return userId?.let { id ->
            // Hole die Dokumentenreferenz für das Profil des Users
            val docRef = db.collection("profiles").document(id)
            try {
                // Hole den Dokumentensnapshot und konvertiere ihn zu einem Profile Objekt
                val snapshot = docRef.get().await()
                snapshot.toObject(Profile::class.java)
            } catch (e: Exception) {
                // Behandle mögliche Fehler und gib null zurück, wenn etwas schief gelaufen ist
                e.printStackTrace()
                null
            }
        }
    }

    override suspend fun updateProfile(profile: Profile) {
        userId?.let { id ->
            // Hole die Dokumentenreferenz für das Profil des Users
            val docRef = db.collection("profiles").document(id)
            try {
                // Setze die Profildaten auf das Dokument mit der Merge-Option
                docRef.set(profile, SetOptions.merge()).await()
            } catch (e: Exception) {
                // Behandle mögliche Fehler und werfe sie weiter, wenn etwas schief gelaufen ist
                e.printStackTrace()
                throw e
            }
        }
    }

    override suspend fun getRoomsByBuilding(buildingNr: Int): List<Room> {
        return try {
            // Hole die Abfragenreferenz für die Räume-Kollektion mit einem Filter nach Gebäudenummer
            val queryRef = db.collection("rooms").whereEqualTo("buildingNr", buildingNr)
            // Hole den Abfragensnapshot und konvertiere ihn zu einer Liste von Room Objekten
            val snapshot = queryRef.get().await()
            snapshot.toObjects(Room::class.java)
        } catch (e: Exception) {
            // Behandle mögliche Fehler und gib eine leere Liste zurück, wenn etwas schief gelaufen ist
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getBuildings(): List<Building> {
        return try {
            // Hole die Abfragenreferenz für die Gebäude-Kollektion
            val queryRef = db.collection("buildings")
            // Hole den Abfragensnapshot und konvertiere ihn zu einer Liste von Building Objekten
            val snapshot = queryRef.get().await()
            snapshot.toObjects(Building::class.java)
        } catch (e: Exception) {
            // Behandle mögliche Fehler und gib eine leere Liste zurück, wenn etwas schief gelaufen ist
            e.printStackTrace()
            emptyList()
        }
    }
}
