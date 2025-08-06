package com.byariel.sugarlab.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byariel.sugarlab.R
import com.byariel.sugarlab.logic.FizzBuzzEvaluar


@Preview(showBackground = true)
@Composable
fun FizzBuzzScreen() {
    val numero = remember { mutableStateOf("") }
    val resultado = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🚀Pantalla FizzBuzz", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        Text("🔢 Ingresa un número:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = numero.value,
            onValueChange = { numero.value = it },
            label = { Text("Número entre 1 y 100") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color(0xFFF5F5F5),
                cursorColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val num = numero.value.toIntOrNull()
            resultado.value = if (num == null) {
                "⚠️ Número inválido"
            } else {
                FizzBuzzEvaluar(num)  // Aquí usás la función que definiste
            }
        }) {
            Text("Ver resultado")
        }

        Text(
            text = resultado.value,
            style = MaterialTheme.typography.titleLarge
        )
        fizzBuzzAyuda()
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = painterResource(id = R.drawable.fizzbuzz),
            contentDescription = "Logo decorativo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(1f)
        )


    }
}


@Composable
fun fizzBuzzAyuda() {
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
                        🔤🧠 ¿Cómo funciona?

                        Vas a ver los números del 1 al 100.
                        
                        Pero ojo:
                        - Si un número es múltiplo de 3, aparece "fizz"
                        - Si es múltiplo de 5, aparece "buzz"
                        - Si es múltiplo de ambos, aparece "fizzbuzz"
                        
                        ¡Probá a descubrir el patrón!


                        """.trimIndent()
                    )
                }
            )
        }
    }
}

