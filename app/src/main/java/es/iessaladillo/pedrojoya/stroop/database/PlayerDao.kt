package es.iessaladillo.pedrojoya.stroop.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDao {
    @Insert
    fun insertPlayer(player: Player)

    @Query("SELECT * FROM player")
    fun queryAllPlayers(): List<Player>

    @Query("SELECT * FROM player WHERE userId = :id")
    fun queryPlayerById(id: Int): Player

    @Update
    fun updatePlayer(player: Player)

    @Delete
    fun deletePlayer(player: Player)
}