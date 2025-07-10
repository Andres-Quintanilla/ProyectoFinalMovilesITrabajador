package com.example.ProyectoFinalMovilesTrabajador.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.core.content.res.ResourcesCompat
import com.example.ProyectoFinalMovilesTrabajador.data.model.Categoria
import com.example.ProyectoFinalMovilesTrabajador.data.network.InstanciaRetrofit
import com.example.ProyectoFinalMovilesTrabajador.util.FileUtil
import com.example.ProyectoFinalMovilesTrabajador.util.GestorToken
import com.example.proyectofinalmovilestrabajador.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

class Repository(private val context: Context) {
    private val api = InstanciaRetrofit.getInstance(context)
    private val gestorToken = GestorToken(context)

    suspend fun login(email: String, password: String) =
        api.loginTrabajador(mapOf("email" to email, "password" to password))

    suspend fun crearCategoriasDesdeTexto(texto: String): List<Categoria> {
        val nombres = texto.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        val nuevasCategorias = mutableListOf<Categoria>()

        for (nombre in nombres) {
            try {
                val response = api.crearCategoria(mapOf("name" to nombre))
                if (response.isSuccessful && response.body() != null) {
                    nuevasCategorias.add(response.body()!!)
                }
            } catch (e: Exception) {
                // Puedes loguear el error si quieres
            }
        }
        return nuevasCategorias
    }

    suspend fun registrarTrabajadorSimple(
        nombre: String,
        apellido: String,
        email: String,
        password: String
    ): Response<Unit> {
        val body = mapOf(
            "name" to nombre,
            "lastName" to apellido,
            "email" to email,
            "password" to password
        )
        return api.registrarTrabajadorSimple(body)
    }




    fun guardarToken(token: String) = gestorToken.guardarToken(token)
    fun obtenerToken() = gestorToken.obtenerToken()
    fun borrarToken() = gestorToken.borrarToken()

}