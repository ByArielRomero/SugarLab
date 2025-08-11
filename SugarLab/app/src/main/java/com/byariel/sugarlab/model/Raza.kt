package com.byariel.sugarlab.model

import androidx.annotation.DrawableRes

data class Raza(
    val nombre: String,
    val valor: Int,
    val esBondadosa: Boolean,
    @DrawableRes val iconResId: Int
)
