package es.iessaladillo.pedrojoya.stroop.database

import androidx.room.Embedded
import androidx.room.Relation

data class GamePlayer (
    @Embedded
    val player: Player,
    @Relation(
        parentColumn = "userId",
        entityColumn = "playerId"
    )
    val game: Game
)