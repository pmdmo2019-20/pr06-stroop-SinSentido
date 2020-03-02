package es.iessaladillo.pedrojoya.stroop.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.database.PlayerDao

class PlayerViewModelFactory(val playerDao: PlayerDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        PlayerViewModel(playerDao) as T
}