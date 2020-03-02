package es.iessaladillo.pedrojoya.stroop.ui.dashboard

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager

import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.database.AppDatabase
import es.iessaladillo.pedrojoya.stroop.database.Player
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerViewModel
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerViewModelFactory
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.dashboard_fragment.toolbar
import kotlinx.android.synthetic.main.player_selection_fragment.*

class DashboardFragment : Fragment(R.layout.dashboard_fragment) {

    private val navController: NavController by lazy{
        findNavController()
    }

    private val viewModel: DashboardViewModel by viewModels {
        DashboardViewModelFactory(AppDatabase.getInstance(this.requireContext()).playerDao)
    }

    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }

    var playerId: Int = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews(requireView())
    }

    private fun setupViews(view: View){
        showTutorial()
        setupToolbar()
        setupNavigationButtons()
        checkForPlayer()
    }

    private fun setupNavigationButtons() {
        playCV.setOnClickListener{ navigateToPlay() }
        rankingCV.setOnClickListener{ navController.navigate(R.id.rankingDestination) }
        playerCV.setOnClickListener { navController.navigate(R.id.playerSelectionDestination) }
        assistantCV.setOnClickListener { navController.navigate(R.id.assistantDestination) }
        settingsCV.setOnClickListener { navController.navigate(R.id.settingsDestination) }
        aboutCV.setOnClickListener { navController.navigate(R.id.aboutDestination) }
    }

    private fun navigateToPlay(){
        playerId = settings.getInt(getString(R.string.selected_player_key), 0)

        if(playerId != 0){
            navController.navigate(R.id.gameDestination)
        }
        else{
            navController.navigate(R.id.playerSelectionDestination)
        }
    }

    private fun setupToolbar() {
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
    }

    private fun showTutorial(){
        var isShowed: Boolean = settings.getBoolean(getString(R.string.show_tutorial_key), false)

        if(!isShowed){
            settings.edit{
                putBoolean(getString(R.string.show_tutorial_key), true)
            }
            navController.navigate(R.id.assistantDestination)
        }
    }

    private fun checkForPlayer(){
        playerId = settings.getInt(getString(R.string.selected_player_key), 0)

        if(playerId != 0) {
            imgDashboardAvatar.setImageDrawable(resources.getDrawable(viewModel.queryPlayerById(playerId).avatarId))
            lblDashboardNickname.setText(viewModel.queryPlayerById(playerId).nickname)
        }
    }

}
