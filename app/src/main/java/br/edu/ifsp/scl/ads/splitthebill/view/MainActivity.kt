package br.edu.ifsp.scl.ads.splitthebill.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.scl.ads.splitthebill.R
import br.edu.ifsp.scl.ads.splitthebill.adapter.PersonAdapter
import br.edu.ifsp.scl.ads.splitthebill.controller.PersonController
import br.edu.ifsp.scl.ads.splitthebill.controller.PersonInsertedListener
import br.edu.ifsp.scl.ads.splitthebill.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.splitthebill.model.Person

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val personsList : MutableList<Person> = mutableListOf()
    private val personAdapter: PersonAdapter by lazy {
        PersonAdapter(this, personsList)
    }

    private lateinit var acrl: ActivityResultLauncher<Intent>

    private val personController: PersonController by lazy {
        PersonController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        personController.getPersons()

        //personsList.add(Person(0, "Jean", 20.0, "Café"))
        //personsList.add(Person(1, "Fulano", 50.0, "Pão, Requeijão, Mussarela"))
        
        binding.personLv.adapter = personAdapter

        acrl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val person = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("Person", Person::class.java)
                } else {
                    result.data?.getParcelableExtra("Person")
                }

                person?.let {_person ->
                    var position = personsList.indexOfFirst { it.id == _person.id }

                    if (position != -1) {
                        personsList[position] = _person
                        personController.editPerson(_person)

                    } else {
                        personController.insertPerson(_person, object : PersonInsertedListener {
                            override fun PersonInserted() {
                                personController.getPersons()
                                personAdapter.notifyDataSetChanged()
                            }
                        })
                    }

                    personAdapter.notifyDataSetChanged()
                }
            }
        }
        registerForContextMenu(binding.personLv)

        binding.personLv.setOnItemClickListener(object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val person = personsList[position]
                val personIntent = Intent(this@MainActivity, PersonActivity::class.java)
                personIntent.putExtra("Person", person)
                personIntent.putExtra("ViewPerson",true)

                acrl.launch(personIntent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addPerson -> {
                val personIntent = Intent(this, PersonActivity::class.java)
                acrl.launch(personIntent)

                true
            }
            R.id.clearList -> {
                personsList.clear()
                personController.removeAllPersons()
                personAdapter.notifyDataSetChanged()

                true
            }
            R.id.calcBill -> {
                val billIntent = Intent(this, BillActivity::class.java)
                acrl.launch(billIntent)
                true
            }
            else -> false
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View, menuInfo: ContextMenu.ContextMenuInfo?){
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        val person = personsList[position]

        return when (item.itemId) {
            R.id.removePersonMi -> {
                personsList.removeAt(position)
                personController.removePerson(person)
                personAdapter.notifyDataSetChanged()

                Toast.makeText(this,"Pessoa removida!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.editPersonMi -> {
                val personIntent = Intent(this, PersonActivity::class.java)
                personIntent.putExtra("Person", person)

                acrl.launch(personIntent)
                true
            }
            else -> false
        }
    }

    fun updatePersonList(_personList: MutableList<Person>){
        personsList.clear()
        personsList.addAll(_personList)

        personAdapter.notifyDataSetChanged()
    }
}