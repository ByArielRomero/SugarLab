package com.byariel.sugarlab.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.byariel.sugarlab.logic.CalculadoraLogic
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import androidx.compose.runtime.getValue
import com.byariel.sugarlab.R

@Preview
@Composable

fun CalculadoraScreen() {
    val context = LocalContext.current

    var lineas by remember { mutableStateOf<List<String>>(emptyList()) }
    var mensajeValidacion by remember { mutableStateOf("") }
    var resultadoCalculo by remember { mutableStateOf<Double?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            val contenido = leerArchivo(uri, context)
            lineas = contenido

            val resultado = CalculadoraLogic(contenido)

            if (resultado.esValido) {
                mensajeValidacion = "Archivo válido"
                resultadoCalculo = resultado.resultado
            } else {
                mensajeValidacion = "Archivo inválido o error en el cálculo"
                resultadoCalculo = null
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7FB)), // Fondo gris claro
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Spacer(modifier = Modifier.height(30.dp))

        // Encabezado
        Text(
            "📱 Calculadora",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF333333)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF6F7FB)), // Fondo gris claro
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val headerAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.calculadora))
            val headerProgress by animateLottieCompositionAsState(
                composition = headerAnimation,
                iterations = LottieConstants.IterateForever
            )
            // Mostrar animación
            LottieAnimation(
                composition = headerAnimation,
                progress = { headerProgress },
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
            )
            Text(
                "Carga un archivo .txt para calcular el resultado",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón grande
            Button(
                onClick = { launcher.launch(arrayOf("text/plain")) },
                modifier = Modifier
                    .height(60.dp)
                    .width(280.dp)
            ) {
                Text(
                    "Seleccionar archivo .txt",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Mostrar contenido del archivo en tarjeta si existe
            if (lineas.isNotEmpty()) {
                androidx.compose.material3.Card(
                    modifier = Modifier
                        .width(300.dp)
                        .height(150.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = androidx.compose.material3.CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    LazyColumn(modifier = Modifier.padding(8.dp)) {
                        items(lineas) { linea ->
                            Text(text = linea, color = Color.DarkGray)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Mensaje y animación
            if (mensajeValidacion.isNotEmpty()) {
                Text(
                    text = mensajeValidacion,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    color = if (resultadoCalculo != null) Color(0xFF4CAF50) else Color.Red
                )

                Spacer(modifier = Modifier.height(16.dp))

                val animationRes = if (resultadoCalculo != null) R.raw.calculadora else R.raw.error
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(
                        animationRes
                    )
                )
                val progress by animateLottieCompositionAsState(
                    composition = composition,
                    iterations = 2
                )

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .height(250.dp)
                        .width(250.dp)
                )
            }

            // Resultado final
            resultadoCalculo?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Resultado: $it",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF2196F3)
                )
            }
        }
    }
}


// Función que recibe la URI del archivo y el contexto, y devuelve una lista con las líneas leídas
fun leerArchivo(uri: Uri, context: Context): List<String> {
    val lineas = mutableListOf<String>()
    try {
        context.contentResolver.openInputStream(uri)?.bufferedReader().use { reader ->
            reader?.forEachLine { linea ->
                val lineaTrim = linea.trim()
                if (lineaTrim.isNotEmpty()) {
                    lineas.add(lineaTrim)
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return lineas
}
