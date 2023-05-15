package br.edu.ifsp.scl.ads.splitthebill.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.ads.splitthebill.adapter.BillAdapter
import br.edu.ifsp.scl.ads.splitthebill.adapter.PersonAdapter
import br.edu.ifsp.scl.ads.splitthebill.controller.BillController
import br.edu.ifsp.scl.ads.splitthebill.controller.PersonController
import br.edu.ifsp.scl.ads.splitthebill.databinding.ActivityBillBinding
import br.edu.ifsp.scl.ads.splitthebill.model.Person

class BillActivity : AppCompatActivity() {
    private val binding: ActivityBillBinding by lazy {
        ActivityBillBinding.inflate(layoutInflater)
    }
    private val personsList : MutableList<Person> = mutableListOf()

    private val billAdapter: BillAdapter by lazy {
        BillAdapter(this, personsList)
    }

    private val billController: BillController by lazy {
        BillController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        billController.getPersons()
        supportActionBar?.subtitle = ("Divisão da conta")

        binding.billLv.adapter = billAdapter
    }

    fun updateBillList(_personList: MutableList<Person>) {
        personsList.clear()
        personsList.addAll(_personList)

        billAdapter.notifyDataSetChanged()
    }
}