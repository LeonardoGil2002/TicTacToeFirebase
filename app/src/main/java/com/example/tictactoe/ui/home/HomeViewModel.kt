package com.example.tictactoe.ui.home

import androidx.lifecycle.ViewModel
import com.example.tictactoe.data.network.FirebaseService
import com.example.tictactoe.data.network.model.GameModel
import com.example.tictactoe.data.network.model.PlayerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val firebaseService: FirebaseService): ViewModel(){
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun onCreateGame(navigateToGame: (String, String, Boolean) -> Unit) {
        val game = createNewGame()
        val gameId = firebaseService.createGame(game)
        val userId = game.player1?.userId.orEmpty()
        val owner = true
        navigateToGame(gameId, userId, owner)
    }

    fun onSearchGame(gameId: String, navigateToGame: (String, String, Boolean) -> Unit) {
        val owner = false
        navigateToGame(gameId, createUserId(), owner)
    }

    private fun createUserId(): String{
        return UUID.randomUUID().toString()
    }

    private fun createNewGame():GameModel{
        val currentPlayer = PlayerModel(playerType = 1)
        return GameModel(
            board = List(9) {0},
            player1 = currentPlayer,
            playerTurn = currentPlayer,
            player2 = null
        )
    }

    fun onValueChangeSearchGame() {
        _uiState.update {
            it.copy(searchGameValue = it.searchGameValue)
        }
    }

}

data class HomeUiState(
    val searchGameValue: String = ""
)