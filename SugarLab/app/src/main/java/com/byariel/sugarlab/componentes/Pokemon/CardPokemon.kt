package com.byariel.sugarlab.componentes.Pokemon

import androidx.compose.foundation.BorderStroke
import com.byariel.sugarlab.models.Pokemon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import coil.compose.AsyncImage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color



/**
 * Card de Pokémon con gradiente y borde según el jugador
 *
 * @param pokemon Pokémon a mostrar
 * @param indexSeleccion null si no está seleccionado, 0 si es jugador 1, 1 si es jugador 2
 * @param onClick acción al hacer click en la card
 */

@Composable
fun PokemonCardConGradiente3Colores(
    pokemon: Pokemon,
    indexSeleccion: Int?, // null si no está seleccionado, 0 jugador1, 1 jugador2
    onClick: () -> Unit
) {
    // Borde según el jugador
    val borde = when (indexSeleccion) {
        0 -> BorderStroke(4.dp, Color.Blue)
        1 -> BorderStroke(4.dp, Color.Red)
        else -> null
    }

    // Gradiente de 3 colores según el Pokémon
    val gradiente = when (pokemon.name.first().code % 5) {
        0 -> Brush.linearGradient(
            listOf(Color(0xFFf44336), Color(0xFFff7961), Color(0xFFffcdd2))
        ) // rojo → rosa claro
        1 -> Brush.linearGradient(
            listOf(Color(0xFF2196f3), Color(0xFF64b5f6), Color(0xFFbbdefb))
        ) // azul → celeste claro
        2 -> Brush.linearGradient(
            listOf(Color(0xFF4caf50), Color(0xFF81c784), Color(0xFFA5D6A7))
        ) // verde → verde pastel
        3 -> Brush.linearGradient(
            listOf(Color(0xFFff9800), Color(0xFFffb74d), Color(0xFFFFE0B2))
        ) // naranja → naranja claro
        else -> Brush.linearGradient(
            listOf(Color(0xFF9c27b0), Color(0xFFba68c8), Color(0xFFE1BEE7))
        ) // morado → lila
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable { onClick() }
            .then(
                if (borde != null) Modifier.border(borde, RoundedCornerShape(12.dp))
                else Modifier
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradiente)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                AsyncImage(
                    model = pokemon.url,
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(pokemon.name, color = Color.White, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text("HP: ${pokemon.hp}", color = Color.White)
                Text("ATK: ${pokemon.ataque}", color = Color.White)
                Text("DEF: ${pokemon.defensa}", color = Color.White)
            }
        }
    }
}

/*

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.byariel.sugarlab.data.Pokemon
import com.byariel.sugarlab.data.TipoPokemon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.graphics.Color
import com.byariel.sugarlab.data.getGradientByTipo

@Composable
fun PokemonCard(
    pokemon: Pokemon,
    seleccionado: Boolean = false,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .clickable { onClick() }
            .border(
                width = if (seleccionado) 3.dp else 0.dp,
                color = if (seleccionado) Color.Red else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Box(
            modifier = Modifier
                .background(getGradientByTipo(pokemon.tipo))
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    model = pokemon.imagenUrl,
                    contentDescription = pokemon.nombre,
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = pokemon.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Ataque: ${pokemon.ataque}", color = Color.Black)
                Text(text = "Defensa: ${pokemon.defensa}", color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Tipo: ${pokemon.tipo}", color = Color.Black)
            }
        }
    }
}


@Preview
@Composable
fun cardPokemonPreview() {
    val pikachu = Pokemon(
        nombre = "Pikachu",
        tipo = TipoPokemon.ELECTRICO,
        ataque = 55,
        defensa = 40,
        imagenUrl = "https://pngimg.com/d/pokemon_PNG140.png"
    )
    PokemonCard(pikachu)
}

*/