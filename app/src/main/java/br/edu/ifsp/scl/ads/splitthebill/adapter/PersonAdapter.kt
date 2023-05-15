package br.edu.ifsp.scl.ads.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import br.edu.ifsp.scl.ads.splitthebill.R
import br.edu.ifsp.scl.ads.splitthebill.databinding.PersonItemBinding
import br.edu.ifsp.scl.ads.splitthebill.model.Person

class PersonAdapter(context: Context, private val personList:MutableList<Person>):
    ArrayAdapter<Person>(context, R.layout.person_item, personList) {
        private lateinit var binding: PersonItemBinding

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val person: Person = personList[position]
            var personItemView = convertView

            if (personItemView == null) {
                binding = PersonItemBinding.inflate(
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                    parent, false
                )

                personItemView = binding.root

                val holder = PersonItemViewHolder(
                    personItemView.findViewById(R.id.nameTxt),
                    personItemView.findViewById(R.id.valueTxt)
                )
                personItemView.tag = holder
            }

            with(personItemView.tag as PersonItemViewHolder) {
                nameTxt.text = person.nome
                valueTxt.text = "Gastou R$ ${person.valor.toString()}"
            }

            return personItemView
        }

        private data class PersonItemViewHolder (
            val nameTxt: android.widget.TextView,
            val valueTxt: android.widget.TextView)
    }