package br.edu.ifsp.scl.ads.splitthebill.controller

import androidx.room.Room
import br.edu.ifsp.scl.ads.splitthebill.model.Person
import br.edu.ifsp.scl.ads.splitthebill.model.PersonDao
import br.edu.ifsp.scl.ads.splitthebill.model.PersonDaoRoom
import br.edu.ifsp.scl.ads.splitthebill.view.MainActivity

interface PersonInsertedListener {
    fun PersonInserted()
}

class PersonController(private val mainActivity: MainActivity) {
    private val personDao: PersonDao = Room.databaseBuilder(mainActivity, PersonDaoRoom::class.java, PersonDaoRoom.PERSON_DB_FILE)
        .build()
        .getPersonDao()

    fun insertPerson(person: Person, listener: PersonInsertedListener) {
        Thread{
            personDao.createPerson(person)

            mainActivity.runOnUiThread {
                listener.PersonInserted()
            }
        }.start()
    }

    fun getPerson(id: Int) {
        Thread {
            personDao.retrievePerson(id)
        }.start()
    }

    fun getPersons() {
        Thread {
            val allPersons = personDao.retrievePersons()

            mainActivity.runOnUiThread {
                mainActivity.updatePersonList(allPersons)
            }
        }.start()
    }

    fun editPerson(person: Person) {
        Thread{
            personDao.updatePerson(person)
        }.start()
    }

    fun removeAllPersons(){
        Thread{
            personDao.deleteAllPersons()
        }.start()
    }

    fun removePerson(person: Person){
        Thread{
            personDao.deletePerson(person)
        }.start()
    }
}