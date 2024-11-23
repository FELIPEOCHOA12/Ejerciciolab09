package com.example.ejercicio09

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ScreenCarts(navController: NavHostController, servicio: CartApiService) {
    val listaCarts = remember { mutableStateListOf<CartModel>() }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val carts = servicio.getUserCarts()
            Log.d("ScreenCarts", "Datos recibidos: $carts")
            listaCarts.addAll(carts)
        } catch (e: Exception) {
            Log.e("ScreenCarts", "Error al cargar los carritos: ${e.message}")
            listaCarts.addAll(
                listOf(
                    CartModel(1, 101, 299.99, listOf("Producto 1", "Producto 2")),
                    CartModel(2, 102, 399.99, listOf("Producto A", "Producto B"))
                )
            )
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(listaCarts) { cart ->
                CartItem(
                    cart = cart,
                    onCartClick = { cartId ->
                        navController.navigate("cartVer/$cartId")
                    }
                )
            }
        }
    }
}

@Composable
fun CartItem(cart: CartModel, onCartClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Carrito ${cart.id}",
            modifier = Modifier.weight(0.5f),
            textAlign = TextAlign.Start
        )
        Text(
            text = "Precio: \$${cart.total}",
            modifier = Modifier.weight(0.3f),
            textAlign = TextAlign.End
        )
        IconButton(
            onClick = { onCartClick(cart.id) },
            modifier = Modifier.weight(0.2f)
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Ver detalles"
            )
        }
    }
}
