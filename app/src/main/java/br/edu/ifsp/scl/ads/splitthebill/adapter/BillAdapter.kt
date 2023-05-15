package br.edu.ifsp.scl.ads.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import br.edu.ifsp.scl.ads.splitthebill.R
import br.edu.ifsp.scl.ads.splitthebill.databinding.BillItemBinding
import br.edu.ifsp.scl.ads.splitthebill.model.Person

class BillAdapter(context: Context, private val personsList:MutableList<Person>):
    ArrayAdapter<Person>(context, R.layout.person_item, personsList) {

    private lateinit var binding: BillItemBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val person: Person = personsList[position]
        var billItemView = convertView

        if (billItemView == null) {
            binding = BillItemBinding.inflate(
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent, false
            )

            billItemView = binding.root

            val holder = BillItemViewHolder(
                billItemView.findViewById(R.id.nameTxt),
                billItemView.findViewById(R.id.valueTxt),
                billItemView.findViewById(R.id.calcTxt)
            )
            billItemView.tag = holder
        }

        with(billItemView.tag as BillAdapter.BillItemViewHolder) {
            nameTxt.text = person.nome
            valueTxt.text = "Gastou R$ ${person.valor.toString()}"

            val valuePerPerson = calcBillPerPerson()

            if (person.valor > valuePerPerson) {
                val valueReceive = String.format("%.2f", person.valor - valuePerPerson)

                calcTxt.text = "Recebe R$ $valueReceive"
            } else {
                val valuePay = String.format("%.2f", valuePerPerson - person.valor)

                calcTxt.text = "Paga R$ $valuePay"
            }
        }

        return billItemView
    }

    private data class BillItemViewHolder (
        val nameTxt: android.widget.TextView,
        val valueTxt: android.widget.TextView,
        val calcTxt: android.widget.TextView)

    private fun calcBillPerPerson(): Double {
        val total = personsList.map { it.valor }.sum()
        val personsCount = personsList.size

        return total / personsCount
    }
}