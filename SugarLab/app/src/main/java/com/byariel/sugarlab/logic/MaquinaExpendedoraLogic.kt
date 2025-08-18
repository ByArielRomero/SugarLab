package com.byariel.sugarlab.logic

data class Producto(val id: Int, val nombre: String, val precio: Int)

class MaquinaExpendedora(private var saldo: Int = 500) {
    private val productos = listOf(
        Producto(1, "Agua", 100),
        Producto(2, "Gaseosa", 150),
        Producto(3, "Chocolate", 200),
        Producto(4, "Papas", 120)
    )

    fun getSaldo(): Int = saldo

    fun getProductos() = productos

    fun comprar(productoId: Int): String {
        val producto = productos.find { it.id == productoId }
            ?: return "❌ Producto inexistente"

        if (saldo < producto.precio) {
            return "❌ No tenés suficiente dinero (saldo: $saldo¢)"
        }

        saldo -= producto.precio
        return "✅ Compraste ${producto.nombre}, saldo restante: $saldo¢"
    }
}

