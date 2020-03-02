package es.iessaladillo.pedrojoya.stroop.ui.dashboard

import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.database.Player
import es.iessaladillo.pedrojoya.stroop.database.PlayerDao

class DashboardViewModel(val playerDao: PlayerDao): ViewModel() {

    fun queryPlayerById(id: Int): Player {
        return playerDao.queryPlayerById(id)
    }
}