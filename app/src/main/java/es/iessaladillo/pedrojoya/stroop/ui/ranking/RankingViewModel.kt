package es.iessaladillo.pedrojoya.stroop.ui.ranking

import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.database.Game
import es.iessaladillo.pedrojoya.stroop.database.GameDao
import es.iessaladillo.pedrojoya.stroop.database.Player

class RankingViewModel(val gameDao: GameDao): ViewModel() {

    fun queryAllGames(): List<Game>{
        return gameDao.queryAllGames()
    }

    fun queryAttemptsGames(): List<Game>{
        return gameDao.queryAttemptsGames()
    }

    fun queryTimeGames(): List<Game>{
        return gameDao.queryTimeGames()
    }
}