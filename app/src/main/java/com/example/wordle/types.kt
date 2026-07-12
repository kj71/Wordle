package com.example.wordle

import androidx.compose.ui.graphics.Color

enum class GameState {
    WON, LOST, PENDING
}


data class PastGuess (
    var word: String = "",
    var colorMapping: MutableList<Color> = mutableListOf()
)