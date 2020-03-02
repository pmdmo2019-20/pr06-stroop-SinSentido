package es.iessaladillo.pedrojoya.stroop.ui.game

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import es.iessaladillo.pedrojoya.stroop.database.GameDao
import es.iessaladillo.pedrojoya.stroop.database.PlayerDao
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerViewModel

class GameViewModelFactory(val navController: NavController, val playerDao: PlayerDao, val gameDao: GameDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        GameViewModel(navController, playerDao, gameDao) as T
}