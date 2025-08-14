package com.byariel.sugarlab.logic


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class ViewMode { LIST, GRID, BARS, ANIMATED }

class CountGameViewModel : ViewModel() {

    var mode = mutableStateOf(ViewMode.LIST)
        private set

    var numbers = mutableStateOf(listOf<Int>())
        private set

    var isCounting = mutableStateOf(false)
        private set

    var showConfetti = mutableStateOf(false)
        private set

    fun setMode(newMode: ViewMode) {
        mode.value = newMode
    }

    fun toggleCounting() {
        isCounting.value = !isCounting.value
        if (isCounting.value) startCounting()
    }

    private fun startCounting() {
        viewModelScope.launch {
            numbers.value = emptyList()
            for (n in 1..100) {
                numbers.value = numbers.value + n
                delay(50)
            }
            showConfetti.value = true
            delay(2000)
            showConfetti.value = false
        }
    }
}
