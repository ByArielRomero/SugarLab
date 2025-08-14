package com.byariel.sugarlab.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.byariel.sugarlab.logic.CountGameViewModel
import com.byariel.sugarlab.logic.ViewMode
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountGameScreen(viewModel: CountGameViewModel = viewModel()) {
    val mode by viewModel.mode
    val numbers by viewModel.numbers
    val isCounting by viewModel.isCounting
    val showConfetti by viewModel.showConfetti

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Juego: Contar 1 a 100", fontSize = 32.sp)
                    }
                })
        }
    ) { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            // Vistas según modo
            when (mode) {
                ViewMode.LIST -> NumberListFancy(numbers)
                ViewMode.GRID -> NumberGridBubbles(numbers)
                ViewMode.BARS -> NumberBarsAnimated(numbers)
                ViewMode.ANIMATED -> NumberBigCounter(numbers.lastOrNull() ?: 0)
            }

            // Confetti
            if (showConfetti) ConfettiOverlay()

            // Controles
            Column(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = { viewModel.setMode(ViewMode.LIST) }) {
                        Icon(
                            Icons.Filled.List,
                            contentDescription = "Lista",
                            modifier = Modifier.size(100.dp),
                        )
                    }
                    IconButton(onClick = { viewModel.setMode(ViewMode.GRID) }) {
                        Icon(
                            Icons.Filled.GridOn,
                            contentDescription = "Grid",
                            modifier = Modifier.size(100.dp),
                        )
                    }
                    IconButton(onClick = { viewModel.setMode(ViewMode.BARS) }) {
                        Icon(
                            Icons.Filled.BarChart,
                            contentDescription = "Barras",
                            modifier = Modifier.size(100.dp),
                        )
                    }
                    IconButton(onClick = { viewModel.setMode(ViewMode.ANIMATED) }) {
                        Icon(
                            Icons.Filled.PlayArrow,
                            contentDescription = "Animado",
                            modifier = Modifier.size(100.dp),
                        )
                    }
                }
                Button(onClick = { viewModel.toggleCounting() }) {
                    Text(if (isCounting) "Parar" else "Iniciar")
                }
            }
        }
    }
}

// -----------------------------------------
// Vistas con animaciones y colores
// -----------------------------------------

@Composable
fun NumberListFancy(numbers: List<Int>) {
    LazyColumn(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(numbers.size) { i ->
            val scale by animateFloatAsState(
                targetValue = if (i == numbers.lastIndex) 1.2f else 1f,
                animationSpec = spring()
            )
            Text(
                text = "${numbers[i]}",
                fontSize = 35.sp,
                color = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat()),
                modifier = Modifier
                    .padding(6.dp)
                    .graphicsLayer(scaleX = scale, scaleY = scale)
            )
        }
    }
}

@Composable
fun NumberGridBubbles(numbers: List<Int>) {
    LazyVerticalGrid(columns = GridCells.Fixed(5), modifier = Modifier.fillMaxSize()) {
        items(numbers) { n ->
            val color = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
            Box(
                Modifier
                    .padding(6.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                Text(n.toString(), color = Color.White)
            }
        }
    }
}

@Composable
fun NumberBarsAnimated(numbers: List<Int>) {
    LazyColumn {
        items(numbers.size) { i ->
            val widthFraction by animateFloatAsState(numbers[i] / 100f)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp)
            ) {
                Text("${numbers[i]}", Modifier.width(30.dp))
                Box(
                    Modifier
                        .height(20.dp)
                        .fillMaxWidth()
                        .background(Color(0xFFB2DFDB))
                ) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(widthFraction)
                            .background(Color(0xFF00796B))
                    )
                }
            }
        }
    }
}

@Composable
fun NumberBigCounter(current: Int) {
    val scale by animateFloatAsState(targetValue = 1.5f, animationSpec = spring())
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "$current",
            fontSize = 96.sp,
            color = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat()),
            modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
        )
    }
}

@Composable
fun ConfettiOverlay() {
    Canvas(Modifier.fillMaxSize()) {
        repeat(100) {
            drawCircle(
                color = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat()),
                radius = Random.nextInt(5, 15).toFloat(),
                center = androidx.compose.ui.geometry.Offset(
                    Random.nextFloat() * size.width,
                    Random.nextFloat() * size.height
                )
            )
        }
    }
}

// -----------------------------------------
// Preview seguro
// -----------------------------------------
@Preview(showBackground = true)
@Composable
fun PreviewCountGameScreen() {
    val testViewModel = remember { CountGameViewModel() }
    testViewModel.numbers.value = (1..10).toList()
    testViewModel.mode.value = ViewMode.LIST

    CountGameScreen(viewModel = testViewModel)
}
