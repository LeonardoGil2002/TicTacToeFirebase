package com.example.tictactoe.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Game(val gameId:String, val userId:String, val owner:Boolean)