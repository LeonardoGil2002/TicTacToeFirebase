package com.example.tictactoe.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tictactoe.ui.game.GameScreen
import com.example.tictactoe.ui.home.HomeScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Home){
        composable<Home> {
            HomeScreen(navigateToGame = {navController.navigate(Game)})
        }
        composable<Game> {
            GameScreen()
        }
    }
}