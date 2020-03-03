package es.iessaladillo.pedrojoya.stroop.ui.result

import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.database.Game
import es.iessaladillo.pedrojoya.stroop.database.GameDao

class ResultViewModel(val gameDao: GameDao): ViewModel() {
    fun queryGameById(gameId: Int): Game {
        return gameDao.queryGameById(gameId)
    }
}