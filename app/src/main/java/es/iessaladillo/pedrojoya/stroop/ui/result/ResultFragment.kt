package es.iessaladillo.pedrojoya.stroop.ui.result

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager

import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.database.AppDatabase
import es.iessaladillo.pedrojoya.stroop.database.GamePlayer
import kotlinx.android.synthetic.main.dashboard_fragment.toolbar
import kotlinx.android.synthetic.main.result_fragment.*

class ResultFragment : Fragment(R.layout.result_fragment) {


    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }

    private val viewModel: ResultViewModel by viewModels {
        ResultViewModelFactory(AppDatabase.getInstance(this.requireContext()).gameDao)
    }

    var gameId: Int = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews(requireView())
    }

    private fun setupViews(view: View){
        setupToolbar()
        loadResult()
    }

    private fun setupToolbar() {
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
    }

    private fun loadResult(){
        gameId = settings.getInt(getString(R.string.current_game_key), 0)
        var gamePlayer: GamePlayer = viewModel.queryGameByIdWithPlayer(gameId)

        imgHeaderAvatar.setImageDrawable(resources.getDrawable(gamePlayer.avatarId))
        lblHeaderNickname.setText(gamePlayer.nickname)
        lblCorrectAnswers.setText(gamePlayer.correct.toString())
        lblIncorrectAnswers.setText((gamePlayer.words - gamePlayer.correct).toString())
        lblGamePoints.setText((gamePlayer.correct*10).toString())
    }
}
