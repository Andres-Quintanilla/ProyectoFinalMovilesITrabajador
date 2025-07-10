package com.example.ProyectoFinalMovilesTrabajador.data.repository

import android.content.Context
import android.net.Uri
import com.example.ProyectoFinalMovilesTrabajador.data.model.Categoria
import com.example.ProyectoFinalMovilesTrabajador.data.network.InstanciaRetrofit
import com.example.ProyectoFinalMovilesTrabajador.util.FileUtil
import com.example.ProyectoFinalMovilesTrabajador.util.GestorToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response

class Repository(private val context: Context) {
    private val api = InstanciaRetrofit.getInstance(context)
    private val gestorToken = GestorToken(context)

    suspend fun login(email: String, password: String) =
        api.loginTrabajador(mapOf("email" to email, "password" to password))

    suspend fun obtenerCategorias(): List<Categoria> =
        api.getCategorias().body() ?: emptyList()

    suspend fun registrarTrabajadorCompleto(
        nombre: String,
        apellido: String,
        email: String,
        password: String,
        imagenUri: Uri,
        ocupaciones: List<Int>
    ): Response<Unit> {
        val file = FileUtil.from(context, imagenUri)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val imagenPart = MultipartBody.Part.createFormData("picture", file.name, requestFile)

        val campos = listOf(
            "name" to nombre,
            "lastName" to apellido,
            "email" to email,
            "password" to password
        ).map { (key, value) ->
            MultipartBody.Part.createFormData(key, value)
        }

        val ocupacionesPart = ocupaciones.map {
            MultipartBody.Part.createFormData("categories[]", it.toString())
        }

        val partes = campos + ocupacionesPart + imagenPart
        return api.registrarTrabajadorCompleto(partes)
    }

    fun guardarToken(token: String) = gestorToken.guardarToken(token)
    fun obtenerToken() = gestorToken.obtenerToken()
    fun borrarToken() = gestorToken.borrarToken()

}