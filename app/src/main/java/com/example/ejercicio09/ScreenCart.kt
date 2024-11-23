package com.example.ejercicio09

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ScreenCart(navController: NavHostController, servicio: CartApiService, id: Int) {
    var cart by remember { mutableStateOf<CartModel?>(null) }

    LaunchedEffect(Unit) {
        try {
            val fetchedCart = servicio.getUserCartById(id)
            cart = fetchedCart
        } catch (e: Exception) {
            Log.e("ScreenCart", "Error al cargar el carrito: ${e.message}")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (cart != null) {
            Column {
                Text("Carrito ID: ${cart!!.id}", style = MaterialTheme.typography.bodyLarge)
                Text("Usuario: ${cart!!.userId}", style = MaterialTheme.typography.bodyLarge)
                Text("Total: \$${cart!!.total}", style = MaterialTheme.typography.headlineSmall)
                Text("Items:", style = MaterialTheme.typography.headlineSmall)
                cart!!.items.forEach { item ->
                    Text("- $item", style = MaterialTheme.typography.bodyMedium)
                }
            }
        } else {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
