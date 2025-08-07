package com.example.tictactoe.ui.response


data class GameResponse(
    val board: List<PlayerType>,
    val gameId: String,
    val player1: PlayerResponse,
    val player2: PlayerResponse?,
    val playerTurn: PlayerResponse
)

sealed class PlayerType(val id:Int, symbol:String){
    object Empty:PlayerType(0, "")
    object FirstPlayer:PlayerType(2, "X")
    object SecondPlayer:PlayerType(3, "O")

    companion object{
        fun getPlayerById(id:Int?):PlayerType{
            return when(id){
                FirstPlayer.id -> FirstPlayer
                SecondPlayer.id -> SecondPlayer
                else -> Empty
            }
        }
    }
}

data class PlayerResponse(val userId:String, val playerType: PlayerType)