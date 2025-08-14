package com.byariel.sugarlab.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byariel.sugarlab.R
import com.byariel.sugarlab.componentes.BotonPrimario
import com.byariel.sugarlab.componentes.CardPair
import com.byariel.sugarlab.componentes.InputNumber
import com.byariel.sugarlab.componentes.TituloSeccion
import com.byariel.sugarlab.componentes.sexagenarioChino.BotonChino
import com.byariel.sugarlab.logic.SexagenarioChinoLogic
import com.byariel.sugarlab.ui.theme.Blanco
import com.byariel.sugarlab.ui.theme.EspaciadoGrande
import com.byariel.sugarlab.ui.theme.EspaciadoMedio
import com.byariel.sugarlab.utils.AgregarAnimacion
import com.byariel.sugarlab.utils.ConverrtirStringToInt
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import com.byariel.sugarlab.componentes.sexagenarioChino.SignoChinoCard

import com.byariel.sugarlab.logic.SignoChino


@Composable
fun SexagenarioChinoScreen() {
    var previewValue by remember { mutableStateOf("") }
    var valorConvertido by remember { mutableStateOf(0) }
    var signo by remember { mutableStateOf<SignoChino?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(EspaciadoMedio),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(EspaciadoGrande))
        TituloSeccion("Sexagenario Chino")

        AgregarAnimacion(
            recurso = R.raw.dragon_chino,
            size = 300.dp
        )

        InputNumber(
            value = previewValue,
            placeHolder = "Ej: 1984",
            label = "Ingrese el año",
            onValueChange = { previewValue = it }
        )

        Spacer(modifier = Modifier.height(EspaciadoGrande))

        if (previewValue.isNotEmpty()) {
            valorConvertido = ConverrtirStringToInt(previewValue)
        }

        BotonChino(
            "Mostrar elemento y animal",
            onClick = {
                signo = SexagenarioChinoLogic(valorConvertido)
            }
        )

        Spacer(modifier = Modifier.height(EspaciadoGrande))

        signo?.let {
            SignoChinoCard(signo = it)
        }
    }
}



@Preview
@Composable
fun SexagenarioChinoPreview() {
    SexagenarioChinoScreen()
}
