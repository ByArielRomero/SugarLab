package com.byariel.sugarlab.utils

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.byariel.sugarlab.R

@Composable
fun AgregarAnimacion(
    recurso: Int,           // Recurso de animación Lottie
    size: Dp = 400.dp,      // Tamaño de la animación
    repetir: Boolean = true  // Si debe repetir infinitamente
    ) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(recurso))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = if (repetir) LottieConstants.IterateForever else 1
    )

    LottieAnimation(
        composition,
        progress,
        modifier = Modifier.size(size)
    )
}

