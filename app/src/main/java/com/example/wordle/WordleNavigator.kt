package com.example.wordle

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class WordleScreens {
    Welcome,
    Game,
}

@Composable
fun WordleNavigator(
    viewModel: WordleViewModel = viewModel(),
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = WordleScreens.Welcome.name
    ) {
        composable(
            route = WordleScreens.Welcome.name
        ) {
            Welcome(
                onPressingPlay = {
                    navController.navigate(WordleScreens.Game.name)
                },
                modifier = Modifier
            )
        }
        composable(
            route = WordleScreens.Game.name
        ) {
            WordleGame(
                viewModel = viewModel,
                modifier = Modifier
            )
        }
    }
}