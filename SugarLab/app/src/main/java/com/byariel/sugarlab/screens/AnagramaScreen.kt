package com.byariel.sugarlab.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.byariel.sugarlab.logic.sonAnagramas
import com.byariel.sugarlab.ui.theme.AzulPrimario


@Preview
@Composable
fun AnagramaScreen() {
    val palabra1 = remember { mutableStateOf("") }
    val palabra2 = remember { mutableStateOf("") }
    val resultado = remember { mutableStateOf<Boolean?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {
        Text(
            text = "🧩 ¿Es un anagrama?",
            style = MaterialTheme.typography.headlineMedium,
            color = AzulPrimario,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))


        Text(
            text = "✏\uFE0FIngrese la primer palabra",
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold

        )

        OutlinedTextField(
            value = palabra1.value,
            onValueChange = { palabra1.value = it },
            label = { Text("Palabra 1") },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "✏\uFE0FIngrese la segunda palabra",
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold

        )
        OutlinedTextField(
            value = palabra2.value,
            onValueChange = { palabra2.value = it },
            label = { Text("Palabra 2") },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = {
            resultado.value = sonAnagramas(palabra1.value, palabra2.value)
        }) {
            Text("VERIFICAR")
        }

        Spacer(modifier = Modifier.height(30.dp))

        if (resultado.value != null) {
            val esAnagrama = resultado.value!!

            if (esAnagrama) {
                Text(
                    text = "✅ Son anagramas",
                    fontSize = 30.sp,
                    color = Color.Green
                )
            } else {
                Text(
                    text = "❌ No son anagramas",
                    fontSize = 30.sp,
                    color = Color.Red
                )
            }


        }


    }
}


