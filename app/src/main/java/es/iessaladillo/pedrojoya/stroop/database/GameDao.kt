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

    @Query("SELECT * FROM game JOIN player ON game.playerId = player.userId")
    fun queryAllGamesWithPlayer(): List<GamePlayer>

    @Query("SELECT * FROM game JOIN player ON game.playerId = player.userId WHERE gameMode LIKE 'Attempts'")
    fun queryAttemptsGamesWithPlayer(): List<GamePlayer>

    @Query("SELECT * FROM game JOIN player ON game.playerId = player.userId WHERE gameMode LIKE 'Time'")
    fun queryTimeGamesWithPlayer(): List<GamePlayer>

    @Query("SELECT * FROM game WHERE gameMode LIKE 'Attempts'")
    fun queryAttemptsGames(): List<Game>

    @Query("SELECT * FROM game WHERE gameMode LIKE 'Time'")
    fun queryTimeGames(): List<Game>

    @Query("SELECT * FROM game JOIN player ON game.playerId = player.userId WHERE gameId = :id")
    fun queryGameByIdWithPlayer(id: Int): GamePlayer

    @Query("SELECT * FROM game WHERE gameId = :id")
    fun queryGameById(id: Int): Game

    //Returns the last insertion
    @Query("SELECT * FROM game ORDER BY gameId DESC LIMIT 1")
    fun queryLastGame(): Game
}