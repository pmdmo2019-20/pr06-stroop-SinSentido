package es.iessaladillo.pedrojoya.stroop.ui.player

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager

import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.database.AppDatabase
import es.iessaladillo.pedrojoya.stroop.database.Player
import kotlinx.android.synthetic.main.dashboard_fragment.toolbar
import kotlinx.android.synthetic.main.player_selection_fragment.*

class PlayerSelectionFragment : Fragment(R.layout.player_selection_fragment), PlayerSelectionAdapter.OnItemClickListener {

    private val navController: NavController by lazy{
        findNavController()
    }

    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }

    private val viewModel: PlayerViewModel by viewModels {
        PlayerViewModelFactory(AppDatabase.getInstance(this.requireContext()).playerDao)
    }

    val listAdapter: PlayerSelectionAdapter = PlayerSelectionAdapter().also {
        it.setOnItemClickListener(this)
    }

    var playerId: Int = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews(requireView())
        submitData()
    }

    private fun setupViews(view: View){
        setupToolbar()
        fabCreatePlayer.setOnClickListener { navController.navigate(R.id.playerCreationDestination) }
        submitData()
        setupRecyclerView()
        checkForPlayer()
    }

    private fun submitData() {
        listAdapter.submitData(viewModel.returnAllPlayers())
    }


    private fun setupToolbar() {
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
    }

    private fun setupRecyclerView(){
        lstPlayers.setHasFixedSize(true)
        lstPlayers.layoutManager = GridLayoutManager(activity, 2)
        lstPlayers.itemAnimator = DefaultItemAnimator()
        lstPlayers.adapter = listAdapter
    }

    private fun checkForPlayer(){
        playerId = settings.getInt(getString(R.string.selected_player_key), 0)

        if(playerId != 0) {
            imgPlayerSelected.setImageDrawable(resources.getDrawable(viewModel.queryPlayerById(playerId).avatarId))
            lblPlayerSelected.setText(viewModel.queryPlayerById(playerId).nickname)
        }
    }

    override fun onClick(player: Player) {
        imgPlayerSelected.setImageDrawable(resources.getDrawable(player.avatarId))
        lblPlayerSelected.setText(player.nickname)
        settings.edit{
            putInt(getString(R.string.selected_player_key), player.userId)
        }
    }
}
