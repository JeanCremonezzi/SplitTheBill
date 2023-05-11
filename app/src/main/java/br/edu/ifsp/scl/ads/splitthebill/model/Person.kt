package br.edu.ifsp.scl.ads.splitthebill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person (
    var nome: String,
    var valor: Double,
    var itens: String
): Parcelable
