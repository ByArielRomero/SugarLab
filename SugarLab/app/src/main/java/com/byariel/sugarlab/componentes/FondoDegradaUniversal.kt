package com.byariel.sugarlab.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


@Composable
fun FondoUniversal(
    colors: List<Color> = listOf(Color(0xFFFF0000), Color(0xFFFFD700)),
    content: @Composable () -> Unit
){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors))
        ) {
            content()
        }
    }



