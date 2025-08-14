package com.example.tictactoe.ui.response

import com.example.tictactoe.data.network.model.GameModel
import com.example.tictactoe.data.network.model.PlayerModel


data class GameResponse(
    val board: List<PlayerType>,
    val gameId: String,
    val player1: PlayerResponse,
    val player2: PlayerResponse?,
    val playerTurn: PlayerResponse,
    val isGameReady: Boolean = false,
    val isMyTurn: Boolean = false
) {
    fun toModel(): GameModel {
        return GameModel(
            board = board.map { it.id },
            gameId = gameId,
            player1 = player1.toModel(),
            player2 = player2?.toModel(),
            playerTurn = playerTurn.toModel()
            )
    }
}

sealed class PlayerType(val id: Int, val symbol: String) {
    object Empty : PlayerType(0, "")
    object FirstPlayer : PlayerType(1, "X")
    object SecondPlayer : PlayerType(2, "O")

    companion object {
        fun getPlayerById(id: Int?): PlayerType {
            return when (id) {
                FirstPlayer.id -> FirstPlayer
                SecondPlayer.id -> SecondPlayer
                else -> Empty
            }
        }
    }
}

data class PlayerResponse(val userId: String, val playerType: PlayerType){
    fun toModel():PlayerModel{
        return PlayerModel(
            userId = userId,
            playerType = playerType.id
        )
    }
}