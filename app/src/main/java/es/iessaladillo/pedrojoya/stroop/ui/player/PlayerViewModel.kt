package es.iessaladillo.pedrojoya.stroop.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.database.Player
import es.iessaladillo.pedrojoya.stroop.database.PlayerDao
import kotlin.concurrent.thread

class PlayerViewModel(val playerDao: PlayerDao): ViewModel() {

    var playerList: List<Player> = returnAllPlayers()

    fun insertPlayer(avatarId: Int, nickname: String){
        thread {
            try {
                playerDao.insertPlayer(Player(0, nickname, avatarId))
                queryAllPlayers()
            }
            catch (e: Exception){

            }

        }
    }

    fun queryAllPlayers() {
        playerList = playerDao.queryAllPlayers()
    }

    fun queryPlayerById(id: Int): Player{
        return playerDao.queryPlayerById(id)
    }

    fun returnAllPlayers(): List<Player> {
        return playerDao.queryAllPlayers()
    }

    fun updatePlayer(player: Player){
        playerDao.updatePlayer(player)
    }

    fun deletePlayer(player: Player){
        playerDao.deletePlayer(player)
    }
}