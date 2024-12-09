package com.example.myapplication11.network


import com.example.myapplication11.models.Product
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("products/all") // Assurez-vous que le chemin correspond bien à ton API
    suspend fun getAllProducts(): List<Product>
}
private const val BASE_URL = "http://192.168.1.111:3000/" // Remplace par l’URL de ton serveur

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object ProductApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}


