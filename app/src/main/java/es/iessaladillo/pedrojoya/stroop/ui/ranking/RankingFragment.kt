package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.app.AlertDialog
import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.database.AppDatabase
import es.iessaladillo.pedrojoya.stroop.database.Game
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerSelectionAdapter
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerViewModel
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerViewModelFactory
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.dashboard_fragment.toolbar
import kotlinx.android.synthetic.main.player_selection_fragment.*
import kotlinx.android.synthetic.main.ranking_fragment.*

class RankingFragment : Fragment(R.layout.ranking_fragment), RankingAdapter.OnItemClickListener {

    private val viewModel: RankingViewModel by viewModels {
        RankingViewModelFactory(AppDatabase.getInstance(this.requireContext()).gameDao)
    }

    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }

    private val navController: NavController by lazy{
        findNavController()
    }

    val listAdapter: RankingAdapter = RankingAdapter().also {
        it.setOnItemClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews(requireView())
    }

    private fun setupViews(view: View){
        setupToolbar()
        setupRecyclerView()
        submitData()
        setupSpinner()
    }

    private fun submitData(){
        if(spnGameMode.selectedItem == "All"){
            listAdapter.submitData(viewModel.queryAllGames())
        }
        else if(spnGameMode.selectedItem == "Attempts"){
            listAdapter.submitData(viewModel.queryAttemptsGames())
        }
        else if(spnGameMode.selectedItem == "Time"){
            listAdapter.submitData(viewModel.queryTimeGames())
        }
    }

    private fun setupToolbar() {
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
        toolbar.inflateMenu(R.menu.main_activity)
        toolbar.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.mnuHelp -> { showDialod() }
            }
            true
        }
    }

    private fun setupSpinner(){
        spnGameMode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                submitData()
            }

        }
    }

    private fun setupRecyclerView(){
        lstGames.setHasFixedSize(true)
        lstGames.layoutManager = LinearLayoutManager(context)
        lstGames.itemAnimator = DefaultItemAnimator()
        lstGames.adapter = listAdapter
    }

    private fun showDialod(){
        val dialog: Dialog = AlertDialog.Builder(context)
            .setTitle(getString(R.string.ranking_title))
            .setMessage(getString(R.string.ranking_help_description))
            .create()

        dialog.show()
    }

    override fun onClick(game: Game) {
        settings.edit{
            putInt(getString(R.string.current_game_key), game.gameId)
        }
        navController.navigate(R.id.resultDestination)
    }
}
