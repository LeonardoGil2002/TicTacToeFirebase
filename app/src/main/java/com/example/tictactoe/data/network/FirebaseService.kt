package com.example.tictactoe.data.network

import android.util.Log
import com.example.tictactoe.data.network.model.GameModel
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class FirebaseService @Inject constructor(private val reference: DatabaseReference){

    companion object{
        private const val PATH = "games"
    }

    fun createGame(gameModel: GameModel){
        val gameReference = reference.child(PATH).push()
        gameReference.setValue(gameModel)
            .addOnFailureListener{Log.i("OnFailure", it.toString())}
            .addOnCanceledListener { Log.i("OnCanceled", "Cancelado") }
            .addOnSuccessListener { Log.i("OnSuccess", it.toString()) }
    }

}