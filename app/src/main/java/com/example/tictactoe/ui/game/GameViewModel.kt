package com.example.tictactoe.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.data.network.FirebaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val firebaseService: FirebaseService) : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState

    lateinit var userId: String

    fun joinGame(gameId: String, userId: String, owner: Boolean) {
        this.userId = userId
        if(owner){
            joinToGameLikeOwner(gameId)
        } else {
            joinToGameLikeGuest(gameId)
        }
    }

    private fun joinToGameLikeOwner(gameId: String) {
        viewModelScope.launch {
            firebaseService.joinToGame(gameId)
        }
    }

    private fun joinToGameLikeGuest(gameId: String) {

    }
}

data class GameUiState(
    val id: String = ""
)