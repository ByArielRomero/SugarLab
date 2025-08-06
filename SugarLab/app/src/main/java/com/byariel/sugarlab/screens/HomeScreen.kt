package com.byariel.sugarlab.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import com.byariel.sugarlab.R
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

/**
 * Pantalla principal con botones para elegir ejercicios.
 * El contenido está centrado y con buen espaciado.
 */
@Composable
fun HomeScreen(navController: NavHostController) {

    // Columna principal que ocupa toda la pantalla
    // Contenido centrado vertical y horizontalmente
    Column(
        modifier = Modifier
            .fillMaxSize()         // Ocupa toda la pantalla
            .padding(20.dp),       // Padding alrededor para que no pegue a los bordes
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically), // Espacio vertical entre hijos + centrar verticalmente
        horizontalAlignment = Alignment.CenterHorizontally  // Centra horizontalmente los hijos
    ) {

        // Título principal
        Text(
            text = "📝 Elige un ejercicio",
            style = MaterialTheme.typography.headlineMedium
        )

        // Botón para navegar a FizzBuzz
        Button(
            modifier = Modifier.fillMaxWidth(0.7f),  // Ancho 70% del contenedor padre
            onClick = { navController.navigate("fizzbuzz") }
        ) {
            Text("🚀 FizzBuzz", style = MaterialTheme.typography.titleLarge)
        }

        // Botón para navegar a Anagrama
        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = { navController.navigate("anagrama") }
        ) {
            Text("🔤 Anagrama", style = MaterialTheme.typography.titleLarge)
        }

        // Botón para navegar a Área Polígono
        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = { navController.navigate("poligono") }
        ) {
            Text("📐 Área Polígono", style = MaterialTheme.typography.titleLarge)
        }

        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = { navController.navigate("CarreraObstaculos") }
        ) {
            Text("\uD83C\uDFC3Carrera de obstaculos", fontSize = 18.sp, style = MaterialTheme.typography.titleLarge)
        }

        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = { navController.navigate("") }
        ) {
            Text("\uD83C\uDFC3Conjuntos",  style = MaterialTheme.typography.titleLarge)
        }


        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo decorativo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(1f)
                .fillMaxHeight(0.4f)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Ariel Text
        Text(
            text = "Hecho con ❤\uFE0F por Ariel Romero",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}


/**
 * Preview para Android Studio, crea un navController falso para simular la pantalla
 */
@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = androidx.navigation.compose.rememberNavController()
    HomeScreen(navController)
}
