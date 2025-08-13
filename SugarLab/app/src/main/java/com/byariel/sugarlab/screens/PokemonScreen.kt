package com.byariel.sugarlab.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byariel.sugarlab.R
import com.byariel.sugarlab.componentes.Pokemon.PokemonCard
import com.byariel.sugarlab.componentes.TituloSeccion
import com.byariel.sugarlab.data.pokemones
import com.byariel.sugarlab.logic.PokemonLogic
import com.byariel.sugarlab.ui.theme.Blanco
import com.byariel.sugarlab.ui.theme.EspaciadoMedio
import kotlinx.coroutines.delay

@Composable
fun PokemonScreen() {
    val seleccionados = remember { mutableStateListOf<Int>() }
    val dañoResultado = remember { mutableStateOf<Double?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(EspaciadoMedio)
            .background(Color.White)
    ) {
        // UI principal: siempre visible
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.pokeball),
                contentDescription = stringResource(id = R.string.calculadora),
                tint = Color.Unspecified,
                modifier = Modifier.size(48.dp)
            )

            TituloSeccion("Batalla pokemon")
            Spacer(modifier = Modifier.height(EspaciadoMedio))
            Text(text = "Elije 2 pokemones y ¡lucha!")
            Spacer(modifier = Modifier.height(EspaciadoMedio))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(pokemones.size) { index ->
                    PokemonCard(
                        pokemon = pokemones[index],
                        seleccionado = seleccionados.contains(index),
                        onClick = {
                            if (seleccionados.contains(index)) {
                                seleccionados.remove(index)
                            } else if (seleccionados.size < 2) {
                                seleccionados.add(index)
                            }
                            dañoResultado.value = null
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(EspaciadoMedio))
        }

        // Botón fijo abajo
        if (seleccionados.size == 2) {
            Button(
                onClick = {
                    val p1 = pokemones[seleccionados[0]]
                    val p2 = pokemones[seleccionados[1]]
                    val daño = PokemonLogic(p1, p2)
                    dañoResultado.value = daño
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp)
            ) {
                Text("Empezar Batalla")
            }
        }

        // Cartel flotante tipo card
        dañoResultado.value?.let { daño ->
            LaunchedEffect(daño) {
                delay(4000)
                dañoResultado.value = null
            }

            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFfce4ec) // rosa suave
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Text(
                    text = "Daño causado: $daño",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Red,
                    modifier = Modifier.padding(24.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun PokemonScreePreviewn() {
    PokemonScreen()
}