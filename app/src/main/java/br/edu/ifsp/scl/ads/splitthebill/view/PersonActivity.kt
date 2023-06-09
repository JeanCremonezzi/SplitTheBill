package br.edu.ifsp.scl.ads.splitthebill.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.edu.ifsp.scl.ads.splitthebill.R
import br.edu.ifsp.scl.ads.splitthebill.databinding.ActivityPersonBinding
import br.edu.ifsp.scl.ads.splitthebill.model.Person

class PersonActivity : AppCompatActivity() {
    private val binding: ActivityPersonBinding by lazy {
        ActivityPersonBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.subtitle = ("Adicionar pessoa")

        val receivedPerson = getReceivedPerson()
        receivedPerson?.let { _receivedPerson ->
            supportActionBar?.subtitle = ("Visualizar ou editar pessoa")

            with (binding) {
                nameEt.isEnabled = false
                saveBtn.text = "Atualizar pessoa"

                with (_receivedPerson) {
                    nameEt.setText(nome)
                    valueEt.setText(valor.toString())
                    itemsEt.setText(itens)
                }
            }
        }

        val viewPerson = intent.getBooleanExtra("ViewPerson", false)
        with (binding) {
            valueEt.isEnabled = !viewPerson
            itemsEt.isEnabled = !viewPerson
            saveBtn.visibility = if (viewPerson) View.GONE else View.VISIBLE
        }

        with(binding) {
            saveBtn.setOnClickListener {
                val person = Person(
                    receivedPerson?.id,
                    nameEt.text.toString(),
                    valueEt.text.toString().toDoubleOrNull() ?: 0.0,
                    itemsEt.text.toString()
                )

                val resultIntent = Intent()
                resultIntent.putExtra("Person", person)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.person_menu, menu)

        if (getReceivedPerson() != null) {
            menu.getItem(0).isVisible = false
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clearPerson -> {
                with (binding) {
                    nameEt.setText("")
                    valueEt.setText("")
                    itemsEt.setText("")
                }

                true
            }
            else -> false
        }
    }

    private fun getReceivedPerson(): Person? {
        val receivedPerson = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Person", Person::class.java)
        } else {
            intent.getParcelableExtra("Person")
        }

        return receivedPerson
    }
}