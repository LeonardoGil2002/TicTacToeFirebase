package com.example.tictactoe.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.data.network.FirebaseService
import com.example.tictactoe.ui.response.GameResponse
import com.example.tictactoe.ui.response.PlayerResponse
import com.example.tictactoe.ui.response.PlayerType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val firebaseService: FirebaseService) :
    ViewModel() {
    private val _uiState = MutableStateFlow<GameResponse?>(null)
    val uiState: StateFlow<GameResponse?> = _uiState

    private val _winner = MutableStateFlow<PlayerType?>(null)
    val winner: StateFlow<PlayerType?> = _winner

    private val _draw = MutableStateFlow(false)
    val draw: StateFlow<Boolean> = _draw

    private lateinit var userId: String

    fun joinGame(gameId: String, userId: String, owner: Boolean) {
        this.userId = userId
        if (owner) {
            join(gameId)
        } else {
            joinToGameLikeGuest(gameId)
        }
    }

    private fun join(gameId: String) {
        viewModelScope.launch {
            firebaseService.joinToGame(gameId).collect {
                val result =
                    it?.copy(isGameReady = it.player2 != null, isMyTurn = isMyTurn(it.playerTurn))
                _uiState.value = result
                verifyWinner()
            }
        }
    }

    private fun isMyTurn(playerTurn: PlayerResponse): Boolean {
        return playerTurn.userId == userId
    }

    private fun joinToGameLikeGuest(gameId: String) {
        viewModelScope.launch {
            firebaseService.joinToGame(gameId).take(1).collect {
                if (it != null) {
                    val result = it.copy(player2 = PlayerResponse(userId, PlayerType.SecondPlayer))
                    firebaseService.updateGame(result.toModel())
                }
            }
            join(gameId)
        }
    }

    fun onItemSelected(position: Int) {
        val currentGame = _uiState.value ?: return
        if (currentGame.isGameReady && currentGame.board[position] == PlayerType.Empty && isMyTurn(
                currentGame.playerTurn
            )
        ) {
            viewModelScope.launch {
                val newBoard = currentGame.board.toMutableList()
                newBoard[position] = getPlayerType()

                firebaseService.updateGame(
                    currentGame.copy(
                        board = newBoard,
                        playerTurn = getRivalPlayer()!!
                    ).toModel()
                )
            }
        }
    }

    private fun getPlayerType(): PlayerType {
        return when {
            (_uiState.value?.player1?.userId == userId) -> PlayerType.FirstPlayer
            (_uiState.value?.player2?.userId == userId) -> PlayerType.SecondPlayer
            else -> PlayerType.Empty
        }
    }

    private fun getRivalPlayer(): PlayerResponse? {
        return when {
            (_uiState.value?.player1?.userId == userId) -> _uiState.value?.player2
            (_uiState.value?.player2?.userId == userId) -> _uiState.value?.player1
            else -> null
        }
    }

    private fun verifyWinner() {
        val board = _uiState.value?.board
        //Validación para que no crashee. Aunque no debería pasar nunca esto
        if (board != null && board.size == 9) {
            when {
                isGameWon(board, PlayerType.FirstPlayer) -> _winner.value = PlayerType.FirstPlayer
                isGameWon(board, PlayerType.SecondPlayer) -> _winner.value = PlayerType.SecondPlayer
                isGameDraw(board) -> _draw.value = true
            }
        }
    }

    private fun isGameWon(board: List<PlayerType>, playerType: PlayerType): Boolean {
        return when {
            //Row
            board[0] == playerType && board[1] == playerType && board[2] == playerType -> true
            board[3] == playerType && board[4] == playerType && board[5] == playerType -> true
            board[6] == playerType && board[7] == playerType && board[8] == playerType -> true

            //Column
            board[0] == playerType && board[3] == playerType && board[6] == playerType -> true
            board[1] == playerType && board[4] == playerType && board[7] == playerType -> true
            board[2] == playerType && board[5] == playerType && board[8] == playerType -> true

            //Diagonal
            board[0] == playerType && board[4] == playerType && board[8] == playerType -> true
            board[2] == playerType && board[4] == playerType && board[6] == playerType -> true

            else -> false
        }
    }

    private fun isGameDraw(board: List<PlayerType>): Boolean {
        if (_winner.value != null) return false

        for (cell in board) {
            if (cell == PlayerType.Empty) return false
        }

        return true
    }

}