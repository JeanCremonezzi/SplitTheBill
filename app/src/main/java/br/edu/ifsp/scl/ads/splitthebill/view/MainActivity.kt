package br.edu.ifsp.scl.ads.splitthebill.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

    private lateinit var acrl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        personsList.add(Person("Jean", 20.0, "Café"))
        personsList.add(Person("Fulano", 50.0, "Pão, Requeijão, Mussarela"))
        
        binding.personLv.adapter = personAdapter

        acrl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val person = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("Person", Person::class.java)
                } else {
                    result.data?.getParcelableExtra("Person")
                }

                person?.let {_person ->
                    personsList.add(_person)
                    personAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addPerson -> {
                val personActivity = Intent(this, PersonActivity::class.java)
                acrl.launch(personActivity)

                true
            }
            R.id.clearList -> {
                personsList.clear()
                personAdapter.notifyDataSetChanged()

                true
            }
            else -> false
        }
    }
}