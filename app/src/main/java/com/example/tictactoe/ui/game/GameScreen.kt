package com.example.tictactoe.ui.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tictactoe.R
import com.example.tictactoe.ui.response.GameResponse
import com.example.tictactoe.ui.response.PlayerType

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = hiltViewModel(),
    gameId: String,
    userId: String,
    owner: Boolean
) {
    LaunchedEffect(true) {
        gameViewModel.joinGame(gameId, userId, owner)
    }

    val uiState by gameViewModel.uiState.collectAsStateWithLifecycle()
    val winner by gameViewModel.winner.collectAsStateWithLifecycle()
    val draw by gameViewModel.draw.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(16.dp))
        Text(text = stringResource(R.string.game_lobby_id) + uiState?.gameId.orEmpty())

        val status: String = if (uiState?.isGameReady == true) {
            if (uiState?.isMyTurn == true) {
                stringResource(R.string.game_your_turn)
            } else {
                stringResource(R.string.game_rival_turn)
            }
        } else {
            stringResource(R.string.game_turn_waiting_player_2)
        }

        Text(text = status)
        Spacer(Modifier.height(32.dp))

        if(draw){
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val winnerString =
                    stringResource(R.string.game_draw)
                Text(text = winnerString)
            }
        } else {
            if (winner == null) {
                TicTacToeBoard(
                    uiState = uiState,
                    onItemSelected = { gameViewModel.onItemSelected(it) })
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    val winnerString =
                        stringResource(R.string.game_player_winner, winner!!.id.toString())
                    Text(text = winnerString)
                }
            }
        }
    }
}

@Composable
fun TicTacToeBoard(uiState: GameResponse?, onItemSelected: (Int) -> Unit) {
    if (uiState == null) return
    Column {
        Row {
            TicTacToeBox(uiState.board[0]) { onItemSelected(0) }
            TicTacToeBox(uiState.board[1]) { onItemSelected(1) }
            TicTacToeBox(uiState.board[2]) { onItemSelected(2) }
        }
        Row {
            TicTacToeBox(uiState.board[3]) { onItemSelected(3) }
            TicTacToeBox(uiState.board[4]) { onItemSelected(4) }
            TicTacToeBox(uiState.board[5]) { onItemSelected(5) }
        }
        Row {
            TicTacToeBox(uiState.board[6]) { onItemSelected(6) }
            TicTacToeBox(uiState.board[7]) { onItemSelected(7) }
            TicTacToeBox(uiState.board[8]) { onItemSelected(8) }
        }
    }
}

@Composable
fun TicTacToeBox(playerType: PlayerType, onItemSelected: () -> Unit) {
    Box(
        Modifier
            .size(64.dp)
            .border(BorderStroke(2.dp, Color.Black))
            .clickable { onItemSelected() },
        contentAlignment = Alignment.Center
    ) { Text(text = playerType.symbol) }
}