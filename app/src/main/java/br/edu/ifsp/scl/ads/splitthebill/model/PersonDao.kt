package br.edu.ifsp.scl.ads.splitthebill.model

import androidx.room.*

@Dao
interface PersonDao {
    @Insert
    fun createPerson(person: Person)

    @Query("SELECT * FROM Person WHERE id = :id")
    fun retrievePerson(id: Int): Person?

    @Query("SELECT * FROM Person")
    fun retrievePersons(): MutableList<Person>

    @Update
    fun updatePerson(person: Person) :Int

    @Delete
    fun deletePerson(person: Person):Int

    @Query("DELETE FROM Person")
    fun deleteAllPersons()
}