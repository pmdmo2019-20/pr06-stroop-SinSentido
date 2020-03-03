package es.iessaladillo.pedrojoya.stroop.database

import androidx.room.Embedded
import androidx.room.Relation

data class GamePlayer (
    val gameId: Int,
    val playerId: Int,
    var gameMode: String,
    var time: Int,
    var words: Int,
    var correct: Int,
    val userId: Int,
    var nickname: String,
    var avatarId: Int
)