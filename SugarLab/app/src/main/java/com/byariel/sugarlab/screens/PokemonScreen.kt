package com.byariel.sugarlab.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byariel.sugarlab.componentes.Pokemon.PokemonCardConGradiente3Colores
import com.byariel.sugarlab.componentes.TituloSeccion
import com.byariel.sugarlab.models.Pokemon
import com.byariel.sugarlab.network.RetroFitClient
import com.byariel.sugarlab.utils.getPokemonImageUrl


@Composable
fun PokemonScreen() {
    var pokemons by remember { mutableStateOf(listOf<Pokemon>()) }
    var loading by remember { mutableStateOf(true) }
    val seleccionados = remember { mutableStateListOf<Pokemon>() }
    var resultadoBatalla by remember { mutableStateOf<String?>(null) }
    var rondaActual by remember { mutableStateOf(1) }
    var puntosP1 by remember { mutableStateOf(0) }
    var puntosP2 by remember { mutableStateOf(0) }
    var batallaFinalizada by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val response = RetroFitClient.retrofit.getPokemons(20)
            pokemons = response.results.map {
                Pokemon(
                    name = it.name.replaceFirstChar { c -> c.uppercaseChar() },
                    url = getPokemonImageUrl(it.url)
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            loading = false
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column {
                Column (modifier = Modifier.align(Alignment.CenterHorizontally)){
                    Spacer(modifier = Modifier.height(20.dp))
                    TituloSeccion("Batalla Pokemon")
                    Spacer(modifier = Modifier.height(20.dp))
                }
                Text("Ronda $rondaActual / 3", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Jugador 1:\uD83D\uDD35 Azul  |  Jugador 2:\uD83D\uDD34 Rojo",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(pokemons) { pokemon ->
                        val indexSeleccion = when {
                            seleccionados.isEmpty() -> null
                            seleccionados[0] == pokemon -> 0
                            seleccionados.size > 1 && seleccionados[1] == pokemon -> 1
                            else -> null
                        }

                        PokemonCardConGradiente3Colores(
                            pokemon = pokemon,
                            indexSeleccion = indexSeleccion,
                            onClick = {
                                if (!batallaFinalizada) {
                                    if (seleccionados.contains(pokemon)) seleccionados.remove(pokemon)
                                    else if (seleccionados.size < 2) seleccionados.add(pokemon)
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (seleccionados.size == 2 && !batallaFinalizada) {
                    Button(
                        onClick = {
                            val p1 = seleccionados[0]
                            val p2 = seleccionados[1]
                            var resultado = ""

                            val daño1 = (p1.ataque - p2.defensa + (0..20).random()).coerceAtLeast(0)
                            val daño2 = (p2.ataque - p1.defensa + (0..20).random()).coerceAtLeast(0)

                            resultado += "${p1.name} causa $daño1 | ${p2.name} causa $daño2\n"

                            when {
                                daño1 > daño2 -> {
                                    puntosP1++
                                    resultado += "${p1.name} gana esta ronda ✅"
                                }
                                daño2 > daño1 -> {
                                    puntosP2++
                                    resultado += "${p2.name} gana esta ronda ✅"
                                }
                                else -> resultado += "Empate ⚖️"
                            }

                            resultadoBatalla = resultado
                            seleccionados.clear()

                            if (rondaActual < 3) rondaActual++ else batallaFinalizada = true
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Empezar Batalla")
                    }
                }

                if (batallaFinalizada) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            rondaActual = 1
                            puntosP1 = 0
                            puntosP2 = 0
                            seleccionados.clear()
                            resultadoBatalla = null
                            batallaFinalizada = false
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Jugar de Nuevo")
                    }
                }

                resultadoBatalla?.let { res ->
                    Card(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFfce4ec))
                    ) {
                        Text(
                            text = if (batallaFinalizada) {
                                val ganadorFinal = when {
                                    puntosP1 > puntosP2 -> " Jugador 1 gana 🏆"
                                    puntosP2 > puntosP1 -> "Jugador 2 gana 🏆"
                                    else -> "Empate final ⚖️"
                                }
                                "$res\n\n$ganadorFinal"
                            } else res,
                            color = Color.Red,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonScreenPreview() {
    PokemonScreen()
}
