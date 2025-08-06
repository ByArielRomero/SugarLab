package com.byariel.sugarlab.screens

import android.R.attr.contentDescription
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.byariel.sugarlab.logic.carreraDeObstaculosLogic
import com.byariel.sugarlab.logic.generarAccionesAleatorias
import com.byariel.sugarlab.logic.generarPistaAleatoria
import com.byariel.sugarlab.R

@Preview
@Composable
fun CarreraObstaculosScreen() {
    // Estados que se actualizan al presionar el botón
    var pista by remember { mutableStateOf(generarPistaAleatoria(6)) }
    var acciones by remember { mutableStateOf(generarAccionesAleatorias(6)) }
    val (resultado, gano) = carreraDeObstaculosLogic(acciones, pista)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🏃 Carrera de obstáculos", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))

        Text("Pista:      $pista", fontSize = 20.sp) // Ej: _|_|__
        Spacer(modifier = Modifier.height(10.dp))
        Text("Acciones:   ${acciones.joinToString()}", fontSize = 17.sp)  // Ej: run, jump, jump...
        Spacer(modifier = Modifier.height(10.dp))
        Text("Resultado:  $resultado", fontSize = 20.sp)  // Ej: _|x/_/

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            if (gano) "✅ ¡Carrera superada!" else "❌ Falló en la carrera",
            color = if (gano) Color(0xFF388E3C) else Color.Red,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            pista = generarPistaAleatoria(6)
            acciones = generarAccionesAleatorias(6)
        }) {
            Text("🎲 Nueva carrera")
        }

        CarreraAyuda()

        Spacer(modifier = Modifier.height(45.dp))

        Image(
            painter = painterResource(id = R.drawable.saltando),
            contentDescription = "Logo decorativo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(1f)

        )


    }
}

@Composable
fun CarreraAyuda() {
    var mostrarDialogo by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1976D2),  // color de fondo
            contentColor = Color.White            // color del texto
        ),onClick = { mostrarDialogo = true }) {
            Text("¿Cómo funciona?")
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                confirmButton = {
                    TextButton(onClick = { mostrarDialogo = false }) {
                        Text("Entendido")
                    }
                },
                title = { Text("Reglas del reto") },
                text = {
                    Text(
                        """
                        🏃 Carrera de obstáculos

                        Vas a ver una pista con suelo (_) y vallas (|).

                        El atleta tiene que:
                        - correr (run) en el suelo (_)
                        - saltar (jump) en las vallas (|)

                        Si hace lo correcto, avanza. Si no, se equivoca.

                        ¿Podés completar la pista sin errores?


                        """.trimIndent()
                    )
                }
            )
        }
    }
}