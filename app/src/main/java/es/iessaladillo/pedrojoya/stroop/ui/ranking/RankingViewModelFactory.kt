package es.iessaladillo.pedrojoya.stroop.ui.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.database.GameDao
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerViewModel

class RankingViewModelFactory(val gameDao: GameDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        RankingViewModel(gameDao) as T
}