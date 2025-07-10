package com.example.ProyectoFinalMovilesTrabajador.viewModel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ProyectoFinalMovilesTrabajador.data.model.Categoria
import com.example.ProyectoFinalMovilesTrabajador.data.repository.Repository
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)

    fun registrarTrabajador(
        nombre: String,
        apellido: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.registrarTrabajadorSimple(nombre, apellido, email, password)
                if (response.isSuccessful) onSuccess()
                else onError("Error: ${response.code()}")
            } catch (e: Exception) {
                onError("Error: ${e.localizedMessage}")
            }
        }
    }

}