package com.byariel.sugarlab.screens

import android.content.res.Resources
import androidx.compose.foundation.background
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.byariel.sugarlab.logic.sonAnagramas


@Preview
@Composable
fun AnagramaScreen() {
    val palabra1 = remember { mutableStateOf("") }
    val palabra2 = remember { mutableStateOf("") }
    val resultado = remember { mutableStateOf<Boolean?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {
        Text(
            text = "🧩 ¿Es un anagrama?",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))




        Text(
            text = "✏\uFE0FIngrese la primer palabra",
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold

        )

        OutlinedTextField(
            value = palabra1.value,
            onValueChange = { palabra1.value = it },
            label = { Text("Palabra 1") },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "✏\uFE0FIngrese la segunda palabra",
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold

        )
        OutlinedTextField(
            value = palabra2.value,
            onValueChange = { palabra2.value = it },
            label = { Text("Palabra 2") },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = {
            resultado.value = sonAnagramas(palabra1.value, palabra2.value)
        }) {
            Text("VERIFICAR")
        }

        if (resultado.value != null) {
            val esAnagrama = resultado.value!!

            if (esAnagrama) {
                Text(
                    text = "✅ Son anagramas",
                    fontSize = 30.sp,
                    color = Color.Green
                )
            } else {
                Text(
                    text = "❌ No son anagramas",
                    fontSize = 30.sp,
                    color = Color.Red
                )
            }


        }
        Spacer(modifier = Modifier.height(30.dp))

        AnagramaAyuda()

    }
}

@Composable
fun AnagramaAyuda() {
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
                        🔤 ¿ES UN ANAGRAMA?

                        Debés ingresar dos palabras.

                        Vamos a comprobar si son anagramas, es decir, si una se puede formar reordenando todas las letras de la otra.

                        📌 Importante:
                        - Si las palabras son exactamente iguales, no cuentan como anagrama.

                        """.trimIndent()
                    )
                }
            )
        }
    }
}

