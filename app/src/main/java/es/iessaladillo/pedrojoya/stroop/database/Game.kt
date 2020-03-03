package es.iessaladillo.pedrojoya.stroop.database

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity


@Entity(tableName = "game")
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "gameId")
    val gameId: Int,
    @ColumnInfo(name = "playerId")
    val playerId: Int,
    @ColumnInfo(name= "gameMode")
    var gameMode: String,
    @ColumnInfo(name = "time")
    var time: Int,
    @ColumnInfo(name = "words")
    var words: Int,
    @ColumnInfo(name = "correct")
    var correct: Int
)