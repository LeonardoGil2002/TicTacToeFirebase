package com.example.tictactoe.data.network.model

import com.example.tictactoe.ui.response.GameResponse
import com.example.tictactoe.ui.response.PlayerResponse
import com.example.tictactoe.ui.response.PlayerType
import java.util.Calendar

data class GameModel(
    val board: List<Int?>? = null,
    val gameId: String? = null,
    val player1: PlayerModel? = null,
    val player2: PlayerModel? = null,
    val playerTurn: PlayerModel? = null
){
    fun toResponse(): GameResponse{
        return GameResponse(
            board = board?.map { PlayerType.getPlayerById(it) } ?: mutableListOf(),
            gameId = gameId.orEmpty(),
            player1 = player1!!.toResponse(),
            player2 = player2?.toResponse(),
            playerTurn = playerTurn!!.toResponse()
        )
    }
}

data class PlayerModel(
    val userId: String? = Calendar.getInstance().timeInMillis.hashCode().toString(),
    val playerType: Int? = null
){
    fun toResponse(): PlayerResponse{
        return PlayerResponse(userId!!, PlayerType.getPlayerById(playerType))
    }
}