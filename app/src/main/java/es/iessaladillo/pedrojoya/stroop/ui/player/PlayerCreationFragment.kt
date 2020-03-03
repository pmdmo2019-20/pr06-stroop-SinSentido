package es.iessaladillo.pedrojoya.stroop.ui.player

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager

import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.database.AppDatabase
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.player_creation_fragment.*
import kotlinx.android.synthetic.main.player_selection_fragment.toolbar

class PlayerCreationFragment : Fragment(R.layout.player_creation_fragment), PlayerCreationAdapter.OnItemClickListener {

    var avatarId: Int = 0

    private val viewModel: PlayerViewModel by viewModels {
        PlayerViewModelFactory(AppDatabase.getInstance(requireContext()).playerDao)
    }
    val listAdapter: PlayerCreationAdapter = PlayerCreationAdapter().also {
        it.setOnItemClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews(requireView())
    }

    private fun setupViews(view: View){
        fabCreatePlayer.setOnClickListener{ viewModel.insertPlayer(avatarId, txtNicknameCreation.text.toString()) }
        setupToolbar()
        setupRecyclerView()
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

    private fun setupRecyclerView(){
        lstAvatarsCreation.setHasFixedSize(true)
        lstAvatarsCreation.layoutManager = GridLayoutManager(activity, 3)
        lstAvatarsCreation.itemAnimator = DefaultItemAnimator()
        lstAvatarsCreation.adapter = listAdapter
    }

    private fun showDialod(){
        val dialog: Dialog = AlertDialog.Builder(context)
            .setTitle(getString(R.string.player_creation_title))
            .setMessage(getString(R.string.player_creation_help_description))
            .create()

        dialog.show()
    }

    override fun onClick(position: Int) {
        imgPlayerCreation.setImageDrawable(resources.getDrawable(listAdapter.getItem(position)))
        avatarId = listAdapter.getItem(position)
    }
}
