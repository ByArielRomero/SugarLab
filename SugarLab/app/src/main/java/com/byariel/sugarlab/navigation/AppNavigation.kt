package com.byariel.sugarlab.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.byariel.sugarlab.logic.AnillosDelPoderLogic
import com.byariel.sugarlab.screens.AnagramaScreen
import com.byariel.sugarlab.screens.AnillosDelPoderScreen
import com.byariel.sugarlab.screens.CarreraObstaculosScreen
import com.byariel.sugarlab.screens.ConjuntosScreen
import com.byariel.sugarlab.screens.FizzBuzzScreen
import com.byariel.sugarlab.screens.HomeScreen
import com.byariel.sugarlab.screens.PiedraPapelTijeraScreen
import com.byariel.sugarlab.screens.PoligonoScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("fizzbuzz") { FizzBuzzScreen() }
        composable("anagrama") { AnagramaScreen() }
        composable("poligono") { PoligonoScreen() }
        composable("CarreraObstaculos") { CarreraObstaculosScreen() }
        composable("Conjuntos") { ConjuntosScreen() }
        composable("piedraPapelTijera") { PiedraPapelTijeraScreen() }
        composable("anillosDelPoder") { AnillosDelPoderScreen() }
    }
}


