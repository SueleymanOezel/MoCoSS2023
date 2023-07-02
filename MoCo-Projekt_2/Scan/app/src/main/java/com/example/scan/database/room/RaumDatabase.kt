package com.example.scan.database.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RaumEntity::class], version = 1)
abstract class RaumDatabase : RoomDatabase() {
    abstract fun raumDao(): RaumDao
    /*

    // Ein Begleitobjekt, um eine Singleton-Instanz der Datenbank zu verwalten:
    companion object {
        //flüchtige Variable, um die Datenbankinstanz zu speichern:
        @Volatile
        private var INSTANCE: RoomDatabase? = null
        //eine Methode, die die Datenbankinstanz zurückgibt oder erstellt,
        // wenn sie noch nicht existiert.
        fun getDatabase(context: Context): RoomDatabase {
            return INSTANCE ?: synchronized(this) {
                //Wenn die Instanz nicht null ist, wird zurueck gegeben
                // Wenn die Instanz null ist, wird eine neue Datenbank mit dem Namen "room_database"
                // und dem Kontext der Anwendung erstellt:
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabase::class.java,
                    "room_database"
                ).build()
                //Instanz in Variable speichern +zurueckgeben:
                INSTANCE = instance
                instance
            }
        }
    }

     */
}






