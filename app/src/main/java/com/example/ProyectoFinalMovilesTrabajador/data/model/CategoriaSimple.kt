package com.example.ProyectoFinalMovilesTrabajador.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoriaSimple(
    val id: Int,
    val name: String
) : Parcelable
