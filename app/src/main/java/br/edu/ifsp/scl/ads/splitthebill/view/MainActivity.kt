package br.edu.ifsp.scl.ads.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.edu.ifsp.scl.ads.splitthebill.R
import br.edu.ifsp.scl.ads.splitthebill.adapter.PersonAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        personsList.add(Person("Jean", 20.0, "Café"))
        personsList.add(Person("Fulano", 50.0, "Pão, Requeijão, Mussarela"))
        
        binding.personLv.adapter = personAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addPerson -> {
                val personActivity = Intent(this, PersonActivity::class.java)
                startActivity(personActivity)

                true
            }
            else -> false
        }
    }
}