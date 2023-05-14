package br.edu.ifsp.scl.ads.splitthebill.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 1)
abstract class PersonDaoRoom: RoomDatabase() {
    companion object Constants {
        const val PERSON_DB_FILE="person_room"
    }

    abstract fun getPersonDao(): PersonDao
}