package com.example.ejercicio09

import retrofit2.http.GET
import retrofit2.http.Path

interface CartApiService {
    // Obtiene todos los carritos
    @GET("carts")
    suspend fun getUserCarts(): List<CartModel>

    // Obtiene un carrito por su ID
    @GET("carts/{id}")
    suspend fun getUserCartById(@Path("id") id: Int): CartModel
}
