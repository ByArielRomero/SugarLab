package com.byariel.sugarlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.byariel.sugarlab.navigation.AppNavigation
import com.byariel.sugarlab.screens.AnillosDelPoderScreen
import com.byariel.sugarlab.ui.theme.SugarLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SugarLabTheme (darkTheme = false){  // Si usás tema personalizado
                AppNavigation()

            }
        }
    }
}

