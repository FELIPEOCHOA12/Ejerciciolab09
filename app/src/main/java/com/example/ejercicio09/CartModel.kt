package com.example.ejercicio09

import com.google.gson.annotations.SerializedName

data class CartModel(
    @SerializedName("id") val id: Int, // ID único del carrito
    @SerializedName("userId") val userId: Int, // ID del usuario propietario del carrito
    @SerializedName("total") val total: Double, // Total del carrito
    @SerializedName("items") val items: List<String> // Lista de ítems en el carrito
)
