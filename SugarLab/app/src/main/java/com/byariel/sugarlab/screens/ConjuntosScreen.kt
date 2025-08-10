package com.byariel.sugarlab.screens

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.byariel.sugarlab.logic.ConjuntosLogic


@Preview(showBackground = true)
@Composable
fun ConjuntosScreen() {
    var inputArray1 by remember { mutableStateOf("") }
    var inputArray2 by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf(arrayOf<Int>()) }
    var esComun by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Center
    ) {


        //TITIULO MAIN
        Text(
            "🚀Conjuntos", style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(24.dp))

        //SPACE
        Spacer(modifier = Modifier.height(24.dp))

        //INPUT TEXT 1
        Text(text = "IIngrese el conjunto 1 (máximo 15 números separados por punto (\".\")",
            fontSize = 20.sp, // o headlineMedium para más grande
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(), // ocupa todo el ancho disponible
            maxLines = 2,  // máximo 2 líneas, por ejemplo
            //overflow = TextOverflow.Ellipsis // si no entra en 2 líneas, muestra "..."
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = inputArray1,
            onValueChange = {
                if ( (it.length <= 10)){
                    inputArray1 = limitarA5(it)
                }
            },
            placeholder = { Text("Ej: 1.2.35.42.5") },

            //TECLADO NUMERICO
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(24.dp))

        //INPUT TEXT 2
        Text(text = "Ingrese el conjunto 2 (máximo 15 números separados por punto (\".\")",
            fontSize = 20.sp, // o headlineMedium para más grande
            modifier = Modifier.fillMaxWidth(), // ocupa todo el ancho disponible
            maxLines = 2,  // máximo 2 líneas, por ejemplo
            textAlign = TextAlign.Center
            //overflow = TextOverflow.Ellipsis // si no entra en 2 líneas, muestra "...")
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = inputArray2,
            onValueChange = {
                if ((it.length <= 10)){
                    inputArray2 = limitarA5(it)
                }
            },
            placeholder = { Text("Ej: 1.22.35.4.5") },

            //TECLADO NUMERICO
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button({
                esComun = true
                val arrayEnteros = parsearArray(inputArray1)
                val arrayEnteros2 = parsearArray(inputArray2)

                 resultado = ConjuntosLogic(arrayEnteros, arrayEnteros2, esComun)
            }) {
                Text("Comunes")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button({
                esComun = false
                val arrayInt1 = parsearArray(inputArray1)
                val arrayInt2 = parsearArray(inputArray2)

                 resultado = ConjuntosLogic(arrayInt1, arrayInt2, esComun)
            }) {
                Text("No Comunes")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        val texto = resultado.joinToString(separator = ", ")
        Text("Resultado: $texto",
            fontSize = 20.sp,
            )

        Spacer(modifier = Modifier.height(24.dp))

        ConjuntosAyuda()


    }


}

// Solo permite dígitos, comas y opcionalmente espacios


// Limita la entrada a máximo 5 números
fun limitarA5(texto: String): String {
    return texto // solo números
        .take(10)                         // tomar máximo 5 dígitos
}

// Convierte el texto a Array<Int>
fun parsearArray(texto: String): Array<Int> {
    return texto.split(".")
        .mapNotNull { it.trim().toIntOrNull() }
        .toTypedArray()
}

@Composable
fun ConjuntosAyuda() {
    var mostrarDialogo by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2),  // color de fondo
                contentColor = Color.White, // color del texto

            ),
            onClick = { mostrarDialogo = true }) {
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