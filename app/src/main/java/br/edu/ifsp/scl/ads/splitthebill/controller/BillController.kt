package br.edu.ifsp.scl.ads.splitthebill.controller

import androidx.room.Room
import br.edu.ifsp.scl.ads.splitthebill.model.PersonDao
import br.edu.ifsp.scl.ads.splitthebill.model.PersonDaoRoom
import br.edu.ifsp.scl.ads.splitthebill.view.BillActivity

class BillController(private val billActivity: BillActivity) {
    private val personDao: PersonDao = Room.databaseBuilder(billActivity, PersonDaoRoom::class.java, PersonDaoRoom.PERSON_DB_FILE)
        .build()
        .getPersonDao()

    fun getPersons() {
        Thread {
            val allPersons = personDao.retrievePersons()

            billActivity.runOnUiThread {
                billActivity.updateBillList(allPersons)
            }
        }.start()
    }
}