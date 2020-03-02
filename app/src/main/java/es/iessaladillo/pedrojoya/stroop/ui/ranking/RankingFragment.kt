package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.database.AppDatabase
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerSelectionAdapter
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerViewModel
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerViewModelFactory
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.dashboard_fragment.toolbar
import kotlinx.android.synthetic.main.player_selection_fragment.*
import kotlinx.android.synthetic.main.ranking_fragment.*

class RankingFragment : Fragment(R.layout.ranking_fragment) {

    val listAdapter: RankingAdapter = RankingAdapter()


    private val viewModel: RankingViewModel by viewModels {
        RankingViewModelFactory(AppDatabase.getInstance(this.requireContext()).gameDao)
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
            listAdapter.submitData(viewModel.gameList)
        }
        else if(spnGameMode.selectedItem == "Attempts"){
            listAdapter.submitData(viewModel.gameListAttemps)
        }
        else if(spnGameMode.selectedItem == "Time"){
            listAdapter.submitData(viewModel.gameListTime)
        }
    }

    private fun setupToolbar() {
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
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
}
