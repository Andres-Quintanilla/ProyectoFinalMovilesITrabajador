package com.example.ProyectoFinalMovilesTrabajador.data.network


import com.example.ProyectoFinalMovilesTrabajador.data.model.Categoria
import com.example.ProyectoFinalMovilesTrabajador.data.model.Trabajador
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("worker/register")
    suspend fun registrarTrabajador(@Body usuario: Map<String, String>): Response<Unit>

    @POST("worker/login")
    suspend fun loginTrabajador(@Body usuario: Map<String, String>): Response<Map<String, String>>

    @GET("categories")
    suspend fun getCategorias(): Response<List<Categoria>>

    @GET("categories/{id}/workers")
    fun obtenerTrabajadoresPorCategoria(@Path("id") id: Int): Call<List<Trabajador>>

    @GET("workers/{id}")
    fun getTrabajadorDetalle(@Path("id") id: Int): Call<Trabajador>

    @POST("appointments")
    fun crearCita(@Body body: Map<String, Any?>): Call<Unit>

}