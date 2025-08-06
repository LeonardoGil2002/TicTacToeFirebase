package com.example.tictactoe.data.network.model

import java.util.Calendar

data class GameModel(
    val board: List<Int?>? = null,
    val gameId: String? = null,
    val player1: PlayerModel? = null,
    val player2: PlayerModel? = null,
    val playerTurn: PlayerModel? = null
)

data class PlayerModel(
    val userId: String? = Calendar.getInstance().timeInMillis.hashCode().toString(),
    val playerType: Int? = null
)