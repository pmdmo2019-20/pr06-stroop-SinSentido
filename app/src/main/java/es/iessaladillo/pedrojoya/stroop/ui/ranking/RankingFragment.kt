package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.app.AlertDialog
import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager

import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.database.AppDatabase
import es.iessaladillo.pedrojoya.stroop.database.Game
import es.iessaladillo.pedrojoya.stroop.database.GamePlayer
import kotlinx.android.synthetic.main.dashboard_fragment.toolbar
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
        setupSpinner()
        spinnerDefault()
        submitData()
    }

    private fun submitData(){
        if(spnGameMode.selectedItem == "All"){
            checkEmptyList(viewModel.queryAllGames())
            listAdapter.submitData(viewModel.queryAllGames())
        }
        else if(spnGameMode.selectedItem == "Attempts"){
            checkEmptyList(viewModel.queryAttemptsGames())
            listAdapter.submitData(viewModel.queryAttemptsGames())
        }
        else if(spnGameMode.selectedItem == "Time"){
            checkEmptyList(viewModel.queryTimeGames())
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
            override fun onNothingSelected(parent: AdapterView<*>?) {}

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

    override fun onClick(gamePlayer: GamePlayer) {
        settings.edit{
            putInt(getString(R.string.current_game_key), gamePlayer.gameId)
        }
        navController.navigate(R.id.resultDestination)
    }

    private fun spinnerDefault(){
        var value: String = settings.getString(getString(R.string.prefRankingFilter_key), getString(R.string.prefRankingFilter_defaultValue))!!

        if(value == "All"){
            spnGameMode.setSelection(0)
        }
        else if(value == "Time"){
            spnGameMode.setSelection(1)
        }
        else{
            spnGameMode.setSelection(2)
        }
    }

    private fun checkEmptyList(list: List<GamePlayer>){
        if (list.size > 0){
            imgNoGamesYet.isVisible = false
            lblNoGamesYet.isVisible = false
        }
        else{
            imgNoGamesYet.isVisible = true
            lblNoGamesYet.isVisible = true
        }
    }
}
