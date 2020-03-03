package es.iessaladillo.pedrojoya.stroop.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.database.GameDao
import es.iessaladillo.pedrojoya.stroop.ui.ranking.RankingViewModel

class ResultViewModelFactory(val gameDao: GameDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ResultViewModel(gameDao) as T
}