package com.byariel.sugarlab.logic

import com.byariel.sugarlab.R

class MaquinaExpendedora {

    private var saldo: Int = 0

    private val productos = listOf(
        Producto(1, "🥤 Gaseosa", 150, R.drawable.gaseosa),
        Producto(2, "💧 Agua", 100, R.drawable.agua),
        Producto(3, "🍫 Galletitas", 250, R.drawable.galletitas),
        Producto(4, "🧃 Jugo", 200, R.drawable.jugo),
        Producto(5, "🍫 Chocolate", 250, R.drawable.chocolate)
    )

    fun getSaldo(): Int = saldo

    fun getProductos(): List<Producto> = productos

    fun ingresarDinero(monto: Int) {
        if (monto > 0) saldo += monto
    }

    fun comprar(idProducto: Int): String {
        val producto = productos.find { it.id == idProducto }
        return if (producto != null) {
            if (saldo >= producto.precio) {
                saldo -= producto.precio
                val mensaje = "¡Compraste ${producto.nombre}! ✅"
                if (saldo > 0) "$mensaje Te queda $$saldo de saldo."
                else "$mensaje Te quedaste sin saldo."
            } else "Saldo insuficiente ❌. Te faltan $${producto.precio - saldo}"
        } else "Producto no encontrado."
    }

    fun devolverCambio(): Int {
        val cambio = saldo
        saldo = 0
        return cambio
    }

    fun reset() {
        saldo = 0
        // Reiniciar stock si lo tuvieras
    }
}

// ✅ Ahora Producto incluye el recurso de imagen
data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val imagenResId: Int // ID del drawable
)
