package com.byariel.sugarlab.logic

class MaquinaExpendedora {

    private var saldo: Int = 0

    private val productos = listOf(
        Producto(1, "🥤 Gaseosa", 150),
        Producto(2, "💧 Agua", 100),
        Producto(3, "🧃 Jugo", 200),
        Producto(4, "🍫 Chocolate", 250)
    )

    fun getSaldo(): Int = saldo

    fun getProductos(): List<Producto> = productos

    // Método para ingresar dinero al saldo
    fun ingresarDinero(monto: Int) {
        if (monto > 0) {
            saldo += monto
        }
    }

    // Método para comprar un producto
    fun comprar(idProducto: Int): String {
        val producto = productos.find { it.id == idProducto }

        return if (producto != null) {
            if (saldo >= producto.precio) {
                saldo -= producto.precio
                val mensaje = "¡Compraste ${producto.nombre}! ✅"
                if (saldo > 0) {
                    "$mensaje Te queda $$saldo de saldo."
                } else {
                    "$mensaje Te quedaste sin saldo."
                }
            } else {
                "Saldo insuficiente ❌. Te faltan $${producto.precio - saldo}"
            }
        } else {
            "Producto no encontrado."
        }
    }

    // Método para devolver el cambio restante
    fun devolverCambio(): Int {
        val cambio = saldo
        saldo = 0
        return cambio
    }
}

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Int
)
