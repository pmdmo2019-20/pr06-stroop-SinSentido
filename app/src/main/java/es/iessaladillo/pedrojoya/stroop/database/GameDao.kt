package es.iessaladillo.pedrojoya.stroop.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {
    @Insert
    fun insertGame(game: Game)

    @Query("SELECT * FROM game")
    fun queryAllGames(): List<Game>

    @Query("SELECT * FROM game ORDER BY gameId DESC LIMIT 1")
    fun queryLastGame(): Game
}