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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.byariel.sugarlab.logic.carreraDeObstaculosLogic
import com.byariel.sugarlab.logic.generarAccionesAleatorias
import com.byariel.sugarlab.logic.generarPistaAleatoria
import com.byariel.sugarlab.R
import com.airbnb.lottie.compose.*

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun CarreraObstaculosScreen() {
    var pista by remember { mutableStateOf(generarPistaAleatoria(6)) }
    var indiceActual by remember { mutableStateOf(0) }
    var pistaMostrada by remember { mutableStateOf(true) }
    var gano by remember { mutableStateOf(true) }
    var juegoTerminado by remember { mutableStateOf(false) }
    var reiniciarJuego by remember { mutableStateOf(0) } // para controlar reinicio

    LaunchedEffect(reiniciarJuego) {
        pistaMostrada = true
        kotlinx.coroutines.delay(3000)
        pistaMostrada = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("🏃 Carrera de obstáculos", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (juegoTerminado) {
                Text(
                    text = if (gano) "🎉 ¡Ganaste la carrera!" else "😢 ¡Fallaste en la carrera!",
                    color = if (gano) Color(0xFF388E3C) else Color.Red,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    indiceActual = 0
                    gano = true
                    juegoTerminado = false
                    pista = generarPistaAleatoria(6)  // <-- pista nueva!
                    reiniciarJuego++  // para mostrar pista nueva
                }) {
                    Text("Volver a jugar")
                }
            } else {
                if (pistaMostrada) {
                    // Mostrar toda la pista una sola vez
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.start), // tu imagen de start
                            contentDescription = "Inicio",
                            modifier = Modifier.size(120.dp),

                            contentScale = ContentScale.Crop
                        )
                        pista.forEachIndexed { i, c ->
                            Card(
                                modifier = Modifier
                                    .size(120.dp),


                                ) {

                                Box(contentAlignment = Alignment.Center) {

                                    val imagenId =
                                        if (c == '_') R.drawable.piso else R.drawable.valla
                                    Image(
                                        painter = painterResource(id = imagenId),
                                        contentDescription = if (c == '_') "Piso" else "Valla",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                }

                            }
                        }
                        // Imagen Finish
                        Image(
                            painter = painterResource(id = R.drawable.finish), // tu imagen de finish
                            contentDescription = "Fin",
                            modifier = Modifier.size(120.dp),

                            )

                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("¡Memorizá la pista!", style = MaterialTheme.typography.titleLarge)
                } else {
                    // Aquí mostramos la animación o imagen antes de los botones
                    AnimacionCorredor()

                }
            }

            Spacer(modifier = Modifier.height(24.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    modifier = Modifier
                        .width(150.dp)  // ancho fijo
                        .height(60.dp), onClick = {
                        if (pista[indiceActual] == '_') {
                            indiceActual++
                            if (indiceActual >= pista.length) juegoTerminado = true
                        } else {
                            gano = false
                            juegoTerminado = true
                        }
                    }) {
                    Text(
                        text = "Correr 🏃", modifier =
                            Modifier.fillMaxWidth(),
                        fontSize = 20.sp,
                        // Alineamos el texto centrado horizontalmente:
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                Button(
                    modifier = Modifier
                        .width(150.dp)  // ancho fijo
                        .height(60.dp), onClick = {
                        if (pista[indiceActual] == '|') {
                            indiceActual++
                            if (indiceActual >= pista.length) juegoTerminado = true
                        } else {
                            gano = false
                            juegoTerminado = true
                        }
                    }) {
                    Text(
                        "Saltar ⛔", modifier =
                            Modifier.fillMaxWidth(),
                        fontSize = 20.sp,
                        // Alineamos el texto centrado horizontalmente:
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Obstáculo ${indiceActual + 1} de ${pista.length}")
            Spacer(modifier = Modifier.height(16.dp))
            CarreraAyuda()
        }


    }
}


@Composable
fun AnimacionCorredor() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.corredor_animado))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition,
        progress,
        modifier = Modifier.size(400.dp)
    )
}


@Composable
fun CarreraAyuda() {
    var mostrarDialogo by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2),  // color de fondo
                contentColor = Color.White            // color del texto
            ), onClick = { mostrarDialogo = true }) {
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