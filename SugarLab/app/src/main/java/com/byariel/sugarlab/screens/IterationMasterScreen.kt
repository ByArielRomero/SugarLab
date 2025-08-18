package com.byariel.sugarlab.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.system.measureNanoTime
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


data class Metodo(
    val nombre: String,
    val color: Color,
    val accion: suspend (updateProgress: (Int) -> Unit) -> Unit,
    val recomendacion: String
)

@Composable
fun CountComparisonRaceScreen() {
    val scope = rememberCoroutineScope()
    var progressMap by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    var results by remember { mutableStateOf<List<Pair<String, Long>>>(emptyList()) }
    var fastest by remember { mutableStateOf<Pair<String, Long>?>(null) }
    var started by remember { mutableStateOf(false) }
    var currentNumber by remember { mutableStateOf(0) }
    var currentMetodo by remember { mutableStateOf("") }

    val metodos = listOf(
        Metodo(
            "For loop", Color(0xFF3DDC84),
            accion = { updateProgress ->
                for (i in 1..100) {
                    delay(Random.nextLong(5, 15))
                    updateProgress(i)
                    currentNumber = i
                    currentMetodo = "For loop"
                }
            },
            recomendacion = "Rápido y clásico. Útil cuando querés control total sobre el índice."
        ),
        Metodo(
            "While loop", Color(0xFF4285F4),
            accion = { updateProgress ->
                var i = 1
                while (i <= 100) {
                    delay(Random.nextLong(6, 18))
                    updateProgress(i)
                    currentNumber = i
                    currentMetodo = "While loop"
                    i++
                }
            },
            recomendacion = "Flexible si no sabés cuántas iteraciones habrá."
        ),
        Metodo(
            "Range loop", Color(0xFFFFC107),
            accion = { updateProgress ->
                (1..100).forEach { i ->
                    delay(Random.nextLong(8, 20))
                    updateProgress(i)
                    currentNumber = i
                    currentMetodo = "Range loop"
                }
            },
            recomendacion = "Muy legible y expresivo. Ideal para recorrer rangos."
        ),
        Metodo(
            "Sequence loop", Color(0xFFE91E63),
            accion = { updateProgress ->
                generateSequence(1) { it + 1 }.take(100).forEach { i ->
                    delay(Random.nextLong(10, 25))
                    updateProgress(i)
                    currentNumber = i
                    currentMetodo = "Sequence loop"
                }
            },
            recomendacion = "Más lento en rangos pequeños, pero potente para flujos grandes o infinitos."
        )
    )

    // Devuelve la lista de nombres ordenados por progreso (mayor primero)
    fun posiciones(): List<String> =
        progressMap.entries.sortedByDescending { it.value }.map { it.key }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🏁 Carrera de Métodos (1 a 100)", fontSize = 22.sp)
        Spacer(Modifier.height(8.dp))
        if (currentMetodo.isNotEmpty())
            Text("⏱ $currentMetodo está contando: $currentNumber", fontSize = 16.sp)

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            started = true
            progressMap = metodos.associate { it.nombre to 0 }
            results = emptyList()
            fastest = null

            scope.launch {
                val tiempos = mutableListOf<Pair<String, Long>>()

                // Lanzamos cada método en paralelo
                val jobs = metodos.map { metodo ->
                    launch {
                        val tiempo = measureNanoTime {
                            metodo.accion { progreso ->
                                progressMap = progressMap.toMutableMap().also { it[metodo.nombre] = progreso }
                            }
                        }
                        synchronized(tiempos) { tiempos.add(metodo.nombre to tiempo) }
                    }
                }
                jobs.forEach { it.join() }

                results = tiempos.sortedBy { it.second }
                fastest = results.firstOrNull()
            }
        }) {
            Text("Iniciar Carrera")
        }

        Spacer(Modifier.height(24.dp))

        // Barras de progreso
        metodos.forEach { metodo ->
            val progreso = progressMap[metodo.nombre] ?: 0
            Column(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Text("${metodo.nombre} (${progreso}%)")
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .background(Color.LightGray, RoundedCornerShape(12.dp))
                ) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progreso / 100f)
                            .background(metodo.color, RoundedCornerShape(12.dp))
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Posiciones en tiempo real
        if (started) {
            Text("🏆 Posiciones actuales:", fontSize = 18.sp)
            posiciones().forEachIndexed { index, nombre ->
                Text("${index + 1}°: $nombre")
            }
        }

        fastest?.let {
            Spacer(Modifier.height(24.dp))
            Text("🔥 Ganador: ${it.first}", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
        }

        Spacer(Modifier.height(16.dp))

        // Tarjetas con recomendaciones
        Column {
            metodos.forEach { metodo ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = metodo.color.copy(alpha = 0.2f))
                ) {
                    Column(Modifier.padding(8.dp)) {
                        Text(metodo.nombre, fontSize = 18.sp, color = metodo.color)
                        Text(metodo.recomendacion, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCountComparisonRaceScreen() {
    CountComparisonRaceScreen()
}
