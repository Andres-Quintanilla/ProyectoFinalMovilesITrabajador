package com.example.ProyectoFinalMovilesTrabajador.data.network


import com.example.ProyectoFinalMovilesTrabajador.data.model.Categoria
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST

interface ApiService {
    @POST("worker/register")
    suspend fun registrarTrabajadorSimple(
        @Body trabajador: Map<String, String>
    ): Response<Unit>


    @POST("worker/login")
    suspend fun loginTrabajador(@Body usuario: Map<String, String>): Response<Map<String, String>>

    @POST("categories")
    suspend fun crearCategoria(@Body categoria: Map<String, String>): Response<Categoria>



}