package br.edu.ifsp.scl.ads.splitthebill.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Person (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @NonNull var nome: String,
    var valor: Double,
    var itens: String
): Parcelable {

    /*fun calcBill(persons: MutableList<Person>): Double {
        var total = persons.map { it.valor }.sum()
        var totalPersons = persons.size
    }*/
}
