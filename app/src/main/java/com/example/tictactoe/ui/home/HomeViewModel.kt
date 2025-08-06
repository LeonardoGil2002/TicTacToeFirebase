package com.example.tictactoe.ui.home

import androidx.lifecycle.ViewModel
import com.example.tictactoe.data.network.FirebaseService
import com.example.tictactoe.data.network.model.GameModel
import com.example.tictactoe.data.network.model.PlayerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val firebaseService: FirebaseService): ViewModel(){
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun onCreateGame() {
        firebaseService.createGame(createNewGame())
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

    fun onSearchGame() {

    }

}

data class HomeUiState(
    val searchGameValue: String = ""
)