package com.example.wordle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun WordleGame(
    viewModel: WordleViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val wordleUiState by viewModel.wordleUiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = wordleUiState.currentTypedWord,
            onValueChange = { viewModel.updateCurrentGuess(it) },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {viewModel.checkSubmittedWord()},
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            enabled = viewModel.isGamePending(),
            supportingText = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = wordleUiState.errorMessage,
                    color = Color.Red
                )
            },
        )
        Column(
            modifier = Modifier.padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Past Guesses",
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(top = 20.dp)
            ) {
                items(items = wordleUiState.pastGuesses) { pastGuess ->
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                    ) {
                        items(count = pastGuess.word.length) { index ->
                            Text(
                                text = pastGuess.word[index].toString(),
                                fontSize = 45.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                modifier = Modifier
                                    .background(color = pastGuess.colorMapping[index])
                                    .height(50.dp)
                                    .width(50.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}