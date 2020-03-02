package es.iessaladillo.pedrojoya.stroop.ui.ranking

import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.database.Game
import es.iessaladillo.pedrojoya.stroop.database.GameDao
import es.iessaladillo.pedrojoya.stroop.database.Player

class RankingViewModel(val gameDao: GameDao): ViewModel() {

    var player: Player = Player(1, "Joselete", R.drawable.avatar_03_pirates)

    var gameList: List<Game> = listOf(Game(1, player, "Time", 2, 35, 10),
        Game(2, player, "Attempts", 1, 20, 8),
        Game(3, player, "Attempts", 5, 60, 30))

    var gameListTime: List<Game> = listOf(Game(1, player, "Time", 2, 35, 10))

    var gameListAttemps: List<Game> = listOf(Game(2, player, "Attempts", 1, 20, 8),
        Game(3, player, "Attempts", 5, 60, 30))



    fun queryAllGames(): List<Game>{
        return gameDao.queryAllGames()
    }

    fun queryAttemptsGames(){

    }

    fun queryTimeGames(){

    }
}