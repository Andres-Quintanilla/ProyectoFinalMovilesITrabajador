package com.example.ProyectoFinalMovilesTrabajador.data.network


import com.example.ProyectoFinalMovilesTrabajador.data.model.Categoria
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("worker/register")
    suspend fun registrarTrabajadorCompleto(
        @Part partes: List<MultipartBody.Part>
    ): Response<Unit>

    @POST("worker/login")
    suspend fun loginTrabajador(@Body usuario: Map<String, String>): Response<Map<String, String>>

    @GET("categories")
    suspend fun getCategorias(): Response<List<Categoria>>


}