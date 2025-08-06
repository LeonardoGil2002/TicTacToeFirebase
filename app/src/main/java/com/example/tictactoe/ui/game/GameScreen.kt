package com.example.tictactoe.ui.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tictactoe.R

@Preview(showBackground = true)
@Composable
fun GameScreen(gameViewModel: GameViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(16.dp))
        Text(text = stringResource(R.string.game_lobby_id))
        Text(text = stringResource(R.string.game_turn))
        Spacer(Modifier.height(32.dp))
        TicTacToeBoard()


    }
}

@Preview(showBackground = true)
@Composable
fun TicTacToeBoard(modifier: Modifier = Modifier) {
    Column {
        Row {
            TicTacToeBox()
            TicTacToeBox()
            TicTacToeBox()
        }
        Row {
            TicTacToeBox()
            TicTacToeBox()
            TicTacToeBox()
        }
        Row {
            TicTacToeBox()
            TicTacToeBox()
            TicTacToeBox()
        }
    }
}

@Composable
fun TicTacToeBox() {
    Box(Modifier
        .size(64.dp)
        .border(BorderStroke(2.dp, Color.Black)),
        contentAlignment = Alignment.Center) { }
}