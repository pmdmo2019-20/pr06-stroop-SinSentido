package es.iessaladillo.pedrojoya.stroop.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.database.PlayerDao
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerViewModel

class DashboardViewModelFactory(val playerDao: PlayerDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DashboardViewModel(playerDao) as T
}