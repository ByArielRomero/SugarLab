package com.byariel.sugarlab.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.airbnb.lottie.compose.*
import com.byariel.sugarlab.R
import com.byariel.sugarlab.model.*

@Preview
@Composable
fun AnillosDelPoderScreen() {
    val razasBien = razasBondadosas
    val razasMal = razasMalvadas

    // Cantidades de soldados en cada raza para ambos ejércitos
    val cantidadesBien = remember { mutableStateMapOf<Raza, Int>() }
    val cantidadesMal = remember { mutableStateMapOf<Raza, Int>() }

    // Controla si la animación está en curso
    var mostrandoAnimacion by remember { mutableStateOf(false) }

    // Resultado del combate para mostrar al final
    var resultado by remember { mutableStateOf("") }

    // Carga animación Lottie
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.batalla))

    // Controla progreso de animación, reiniciando siempre que se active
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = mostrandoAnimacion,
        iterations = 1,
        speed = 1.0f,
        restartOnPlay = true, // Aquí cambiamos a true para que siempre reinicie al comenzar
        cancellationBehavior = LottieCancellationBehavior.OnIterationFinish
    )

    // Cuando la animación termina (progress = 1f) calculamos ganador y paramos animación
    LaunchedEffect(progress) {
        if (progress == 1f) {
            resultado = calcularGanadorSimple(cantidadesBien, cantidadesMal)
            mostrandoAnimacion = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("⚔\uFE0FLos Anillos de Poder: Batalla", style = MaterialTheme.typography.headlineLarge,
            fontSize = 25.sp)

        Spacer(Modifier.height(16.dp))

        // Ejércitos uno debajo del otro
        Column(Modifier.fillMaxWidth()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Blue)
                    .padding(8.dp)
            ) {
                EjercitoColumn("Ejército Bondadoso", razasBien, cantidadesBien)
            }

            Spacer(Modifier.height(16.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Red)
                    .padding(8.dp)
            ) {
                EjercitoColumn("Ejército Malvado", razasMal, cantidadesMal)
            }
        }

        Spacer(Modifier.height(24.dp))

        // Mensajes si no hay soldados en algún ejército
        if (cantidadesBien.isEmpty()) {
            Text(
                "El Ejército Bondadoso no tiene soldados.",
                color = Color.Gray,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        if (cantidadesMal.isEmpty()) {
            Text(
                "El Ejército Malvado no tiene soldados.",
                color = Color.Gray,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Mostrar animación o botones
        if (mostrandoAnimacion) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.size(200.dp)
            )
            Text(
                "Batalla en curso...",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        mostrandoAnimacion = true
                        resultado = "" // limpiar resultado viejo
                    },
                    enabled = cantidadesBien.isNotEmpty() || cantidadesMal.isNotEmpty()
                ) {
                    Text("Enfrentar")
                }

                Button(
                    onClick = {
                        // Reiniciar todo: limpiar cantidades y resultado
                        cantidadesBien.clear()
                        cantidadesMal.clear()
                        resultado = ""
                        mostrandoAnimacion = false
                    }
                ) {
                    Text("Reiniciar")
                }
            }
        }

        anillosDelPoderAyuda()

        Spacer(Modifier.height(24.dp))

        // Mostrar resultado final de la batalla
        if (resultado.isNotEmpty()) {
            Text(
                resultado,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = when {
                        resultado.contains("Bondadoso", ignoreCase = true) -> Color.Green
                        resultado.contains("Malvado", ignoreCase = true) -> Color.Red
                        else -> Color.Gray
                    }
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

// Función simple para decidir ganador sumando soldados
fun calcularGanadorSimple(
    cantidadesBien: Map<Raza, Int>,
    cantidadesMal: Map<Raza, Int>
): String {
    val totalBien = cantidadesBien.values.sum()
    val totalMal = cantidadesMal.values.sum()

    return when {
        totalBien > totalMal -> "¡Gana el ejército Bondadoso!"
        totalMal > totalBien -> "¡Gana el ejército Malvado!"
        else -> "¡Empate entre ambos ejércitos!"
    }
}

@Composable
fun EjercitoColumn(
    titulo: String,
    razas: List<Raza>,
    cantidades: MutableMap<Raza, Int>
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(titulo, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(razas.size) { index ->
                val raza = razas[index]
                CartaRaza(raza, cantidades)
            }
        }
    }
}

@Composable
fun CartaRaza(
    raza: Raza,
    cantidades: MutableMap<Raza, Int>
) {
    Card(
        modifier = Modifier
            .size(180.dp)
            .border(2.dp, Color.DarkGray),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()            // Ocupa todo el espacio del Card
                .padding(8.dp),           // Espacio interno para no pegarse a bordes
            verticalArrangement = Arrangement.Center,  // Centrar verticalmente
            horizontalAlignment = Alignment.CenterHorizontally  // Centrar horizontalmente
        ) {
            Image(
                painter = painterResource(id = raza.iconResId),
                contentDescription = raza.nombre,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(8.dp))
            Text(raza.nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        val actual = cantidades[raza] ?: 0
                        cantidades[raza] = actual + 1
                    },
                    modifier = Modifier.size(28.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("+")
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    (cantidades[raza] ?: 0).toString(),
                    fontSize = 16.sp,
                    modifier = Modifier.width(24.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = {
                        val actual = cantidades[raza] ?: 0
                        if (actual > 0) {
                            cantidades[raza] = actual - 1
                        }
                    },
                    modifier = Modifier.size(28.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("-")
                }
            }
        }
    }
}

@Composable
fun anillosDelPoderAyuda() {
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
                         💍 Los Anillos de Poder

                            Dos ejércitos luchan: el bien y el mal.
                        
                            Cada raza tiene un valor:
                            - Bien: Pelosos(1), Sureños buenos(2), Enanos(3), Númenóreanos(4), Elfos(5)
                            - Mal: Sureños malos(2), Orcos(2), Goblins(2), Huargos(3), Trolls(5)
                        
                            El ejército con mayor suma de valores gana la batalla.
                        
                            ¿Quién vencerá en esta guerra épica?

                        """.trimIndent()
                    )
                }
            )
        }
    }
}
