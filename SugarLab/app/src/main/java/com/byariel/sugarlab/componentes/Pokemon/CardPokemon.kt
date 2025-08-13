package com.byariel.sugarlab.componentes.Pokemon



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

