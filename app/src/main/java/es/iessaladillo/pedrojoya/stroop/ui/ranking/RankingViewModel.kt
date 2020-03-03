package es.iessaladillo.pedrojoya.stroop.ui.ranking

import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.database.Game
import es.iessaladillo.pedrojoya.stroop.database.GameDao
import es.iessaladillo.pedrojoya.stroop.database.GamePlayer
import es.iessaladillo.pedrojoya.stroop.database.Player

class RankingViewModel(val gameDao: GameDao): ViewModel() {

    fun queryAllGames(): List<GamePlayer>{
        return gameDao.queryAllGamesWithPlayer()
    }

    fun queryAttemptsGames(): List<GamePlayer>{
        return gameDao.queryAttemptsGamesWithPlayer()
    }

    fun queryTimeGames(): List<GamePlayer>{
        return gameDao.queryTimeGamesWithPlayer()
    }
}