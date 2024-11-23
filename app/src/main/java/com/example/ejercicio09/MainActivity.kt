package com.example.ejercicio09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ejercicio09.ui.theme.Ejercicio09Theme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//felipe Hebert Ochoa

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ejercicio09Theme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    // Configuración de Retrofit
    val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val cartApiService = retrofit.create(CartApiService::class.java)

    val navController = rememberNavController()

    Scaffold(
        topBar = { AppTopBar() },
        bottomBar = { AppBottomBar(navController) },
        content = { paddingValues ->
            NavigationContent(
                paddingValues = paddingValues,
                navController = navController,
                cartApiService = cartApiService
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Carros y Compras",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun AppBottomBar(navController: NavHostController) {
    NavigationBar(containerColor = Color.LightGray) {
        val currentRoute = navController.currentDestination?.route
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = currentRoute == "inicio",
            onClick = { navController.navigate("inicio") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.ShoppingCart, contentDescription = "Carros") },
            label = { Text("Carros") },
            selected = currentRoute == "carts",
            onClick = { navController.navigate("carts") }
        )
    }
}

@Composable
fun NavigationContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    cartApiService: CartApiService
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        NavHost(
            navController = navController,
            startDestination = "carts"
        ) {
            composable("inicio") { ScreenInicio() }
            composable("carts") {
                ScreenCarts(navController = navController, servicio = cartApiService)
            }
            composable(
                route = "cartVer/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val cartId = backStackEntry.arguments?.getInt("id") ?: 0
                ScreenCart(navController = navController, servicio = cartApiService, id = cartId)
            }
        }
    }
}
