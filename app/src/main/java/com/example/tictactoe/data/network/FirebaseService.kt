package com.example.tictactoe.data.network

import com.example.tictactoe.data.network.model.GameModel
import com.example.tictactoe.ui.response.GameResponse
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FirebaseService @Inject constructor(private val reference: DatabaseReference) {

    companion object {
        private const val PATH = "games"
    }

    fun createGame(gameModel: GameModel): String {
        val gameReference = reference.child(PATH).push()
        val key = gameReference.key
        val newGame = gameModel.copy(gameId = key)
        gameReference.setValue(newGame)
        return newGame.gameId.orEmpty()
    }

    fun joinToGame(gameId: String): Flow<GameResponse?> {
        return reference.database.reference.child("$PATH/$gameId").snapshots.map { dataSnapshot ->
            dataSnapshot.getValue(GameModel::class.java)?.toResponse()
        }
    }

    fun updateGame(gameModel: GameModel){
        if(gameModel.gameId != null){
            reference.child(PATH).child(gameModel.gameId).setValue(gameModel)
        }
    }

}