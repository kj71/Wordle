package com.example.wordle

import androidx.lifecycle.ViewModel
import com.example.wordle.ui.theme.GuessBlack
import com.example.wordle.ui.theme.GuessGreen
import com.example.wordle.ui.theme.GuessYellow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

val wordToGuess = "BEACH"
val WORD_LENGTH = 5
val MAX_ATTEMPTS = 5

data class WordleState(
    var noOfAttempts: Int = 0,
    var currentTypedWord: String = "",
    var gameState: GameState = GameState.PENDING,
    var pastGuesses: List<PastGuess> = listOf(),
    var errorMessage: String = "",
)

class WordleViewModel : ViewModel() {
    private val _wordleUiState = MutableStateFlow(WordleState())
    val wordleUiState: StateFlow<WordleState> = _wordleUiState.asStateFlow()

    private fun isWordValid(): Boolean {
        if (!_wordleUiState.value.currentTypedWord.all { it.isLetter() }) {
            return false;
        }
        if (_wordleUiState.value.currentTypedWord.length != WORD_LENGTH) {
            return false;
        }
        return true;
    }

    private fun doesWordMatch(): Boolean {
        return wordToGuess.equals(_wordleUiState.value.currentTypedWord, ignoreCase = true);
    }

    private fun createNewPastGuess(): PastGuess {
        val pastGuess = PastGuess()
        pastGuess.word = _wordleUiState.value.currentTypedWord.uppercase()
        for (index in pastGuess.word.indices) {
            if (pastGuess.word[index] == wordToGuess[index]) {
                pastGuess.colorMapping.add(GuessGreen)
            } else if (pastGuess.word[index] in wordToGuess) {
                pastGuess.colorMapping.add(GuessYellow)
            } else {
                pastGuess.colorMapping.add(GuessBlack)
            }
        }
        return pastGuess;
    }

    fun updateCurrentGuess(typedWord: String) {
        _wordleUiState.value = _wordleUiState.value.copy(
            currentTypedWord = typedWord
        )
    }

    fun isGamePending(): Boolean {
        return _wordleUiState.value.gameState == GameState.PENDING
    }

    fun checkSubmittedWord() {
        if (!isWordValid()) {
            _wordleUiState.value = _wordleUiState.value.copy(
                errorMessage = "Invalid Word",
                currentTypedWord = ""
            )
            return;
        }
        val newPastGuess = createNewPastGuess();
        _wordleUiState.value = _wordleUiState.value.copy(
            noOfAttempts = _wordleUiState.value.noOfAttempts.inc(),
            errorMessage = "",
            pastGuesses = wordleUiState.value.pastGuesses + newPastGuess,
        )
        if (doesWordMatch()) {
            _wordleUiState.value = _wordleUiState.value.copy(
                gameState = GameState.WON,
            )
        } else if (_wordleUiState.value.noOfAttempts == MAX_ATTEMPTS) {
            _wordleUiState.value = _wordleUiState.value.copy(
                gameState = GameState.LOST,
            )
        }
        _wordleUiState.value = _wordleUiState.value.copy(
            currentTypedWord = ""
        )
    }
}