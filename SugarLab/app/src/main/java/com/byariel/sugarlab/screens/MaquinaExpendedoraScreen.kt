package com.byariel.sugarlab.screens

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.byariel.sugarlab.logic.MaquinaExpendedora
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.byariel.sugarlab.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.byariel.sugarlab.logic.Producto
import androidx.compose.material3.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.*


// Función auxiliar para reproducir sonido
fun reproducirSonido(context: Context, resId: Int) {
    val mediaPlayer = MediaPlayer.create(context, resId)
    mediaPlayer.start()
    mediaPlayer.setOnCompletionListener { mp -> mp.release() }
}

// Composable para mostrar cada producto como imagen circular con borde llamativo
@Composable
fun ProductoCircle(producto: Producto, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(100.dp)
            .clip(CircleShape)
            .background(if (selected) Color(0xFF4CAF50) else Color(0xAAFFFFFF))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = producto.imagenResId),
            contentDescription = producto.nombre,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MaquinaExpendedoraScreen() {
    val context = LocalContext.current
    val vm = remember { MaquinaExpendedora() }

    var resultado by remember { mutableStateOf("") }
    val productos = vm.getProductos()
    var saldo by remember { mutableStateOf(vm.getSaldo()) }
    var dineroIngresado by remember { mutableStateOf("") }
    var historial by remember { mutableStateOf(listOf<String>()) }
    var productoSeleccionado by remember { mutableStateOf<Producto?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo original de la máquina
        Image(
            painter = painterResource(id = R.drawable.vending_machine),
            contentDescription = "Máquina expendedora",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Box(
                modifier = Modifier
                    .background(Color(0xAA000000), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    "🥤 Máquina Expendedora",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(16.dp))

            // Saldo
            Box(
                modifier = Modifier
                    .background(Color(0xAA000000), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    "💰 Saldo: $${saldo}",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(16.dp))

            // Ingreso de dinero
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = dineroIngresado,
                    onValueChange = { dineroIngresado = it },
                    label = { Text("Ingresar dinero ($)", color = Color.White) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = {
                        val monto = dineroIngresado.toIntOrNull()
                        if (monto != null && monto > 0) {
                            vm.ingresarDinero(monto)
                            saldo = vm.getSaldo()
                            dineroIngresado = ""
                            resultado = "💵 Se agregaron $$monto"
                            historial = historial + "💵 Ingresaste $$monto (Saldo: $$saldo)"
                            reproducirSonido(context, R.raw.caja_registradora)
                        } else {
                            resultado = "⚠️ Ingresa un monto válido."
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B1FA2))
                ) {
                    Text("Añadir", fontSize = 18.sp, color = Color.White)
                }
            }

            Spacer(Modifier.height(24.dp))

            // Productos en grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = productos, key = { it.id }) { producto ->
                    ProductoCircle(
                        producto = producto,
                        selected = productoSeleccionado?.id == producto.id,
                        onClick = { productoSeleccionado = producto }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Botón comprar producto seleccionado con fondo oscuro para resaltar texto
            Button(
                onClick = {
                    productoSeleccionado?.let { prod ->
                        val compra = vm.comprar(prod.id)
                        saldo = vm.getSaldo()
                        resultado = compra
                        historial = historial + when {
                            compra.startsWith("¡Compraste") -> {
                                reproducirSonido(context, R.raw.caja_registradora)
                                "✅ ${prod.nombre} - $${prod.precio} | Saldo: $$saldo"
                            }

                            compra.startsWith("Saldo insuficiente") ->
                                "❌ ${prod.nombre} | $compra"

                            else -> compra
                        }
                    }
                },
                enabled = productoSeleccionado != null,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F00)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xAA000000), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        "Comprar producto seleccionado",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Botones de acción general
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        val cambio = vm.devolverCambio()
                        saldo = vm.getSaldo()
                        resultado = if (cambio > 0) "💸 Te devolvimos $$cambio" else "😅 No hay saldo"
                        historial = historial + "🔄 Cambio devuelto: $$cambio"
                        if (cambio > 0) reproducirSonido(context, R.raw.caja_registradora)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1))
                ) {
                    Text("Devolver cambio", fontSize = 17.sp, color = Color.White)
                }

                Spacer(Modifier.width(16.dp))

                Button(
                    onClick = {
                        vm.reset()
                        saldo = vm.getSaldo()
                        historial = emptyList()
                        resultado = "🔄 Máquina reiniciada"
                        productoSeleccionado = null
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Text("Reiniciar", fontSize = 18.sp, color = Color.White)
                }
            }

            Spacer(Modifier.height(24.dp))

            // Historial con fondo semitransparente
            if (historial.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xAA000000), shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                        .heightIn(min = 100.dp, max = 200.dp)
                ) {
                    LazyColumn {
                        items(historial) { h ->
                            Text(
                                h,
                                fontSize = 16.sp,
                                color = Color.White,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Resultado de compra o mensaje general
            if (resultado.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .background(Color(0xAA000000), shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        resultado,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MaquinaExpendedoraScreenPreview() {
    MaquinaExpendedoraScreen()
}



