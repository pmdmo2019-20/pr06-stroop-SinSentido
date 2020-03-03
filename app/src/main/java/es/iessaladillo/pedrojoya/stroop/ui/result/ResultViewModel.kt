package es.iessaladillo.pedrojoya.stroop.ui.result

import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.database.Game
import es.iessaladillo.pedrojoya.stroop.database.GameDao
import es.iessaladillo.pedrojoya.stroop.database.GamePlayer

class ResultViewModel(val gameDao: GameDao): ViewModel() {
    fun queryGameByIdWithPlayer(gameId: Int): GamePlayer {
        return gameDao.queryGameByIdWithPlayer(gameId)
    }
}