package es.iessaladillo.pedrojoya.stroop.ui.player

import android.app.AlertDialog
import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager

import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.database.AppDatabase
import es.iessaladillo.pedrojoya.stroop.database.Player
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.player_creation_fragment.*
import kotlinx.android.synthetic.main.player_creation_fragment.fabCreatePlayer
import kotlinx.android.synthetic.main.player_creation_fragment.toolbar
import kotlinx.android.synthetic.main.player_edit_fragment.*
import kotlinx.android.synthetic.main.player_selection_fragment.*


class PlayerEditFragment : Fragment(R.layout.player_edit_fragment), PlayerEditAdapter.OnItemClickListener {

    var playerId: Int = 0
    var avatarId: Int = 0

    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }

    private val viewModel: PlayerViewModel by viewModels {
        PlayerViewModelFactory(AppDatabase.getInstance(this.requireContext()).playerDao)
    }

    val listAdapter: PlayerEditAdapter = PlayerEditAdapter().also {
        it.setOnItemClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews(requireView())
    }

    private fun setupViews(view: View){
        fabEditPlayer.setOnClickListener{ updateUser() }
        setupToolbar()
        setupRecyclerView()
        checkForPlayer()
    }

    private fun updateUser(){
        if(txtNicknameEdit.text.toString() != "" && avatarId != 0){
            viewModel.updatePlayer(Player(playerId, txtNicknameEdit.text.toString(), avatarId))
            activity!!.onBackPressed()
        }
    }

    private fun setupRecyclerView(){
        lstAvatarEdit.setHasFixedSize(true)
        lstAvatarEdit.layoutManager = GridLayoutManager(activity, 3)
        lstAvatarEdit.itemAnimator = DefaultItemAnimator()
        lstAvatarEdit.adapter = listAdapter
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

    private fun checkForPlayer(){
        playerId = settings.getInt(getString(R.string.selected_player_key), 0)

        if(playerId != 0) {
            avatarId = viewModel.queryPlayerById(playerId).avatarId
            imgAvatarEdit.setImageDrawable(resources.getDrawable(avatarId))
            txtNicknameEdit.setText(viewModel.queryPlayerById(playerId).nickname)
        }
    }

    private fun showDialod(){
        val dialog: Dialog = AlertDialog.Builder(context)
            .setTitle(getString(R.string.player_edition_title))
            .setMessage(getString(R.string.player_edition_help_description))
            .create()

        dialog.show()
    }

    override fun onClick(position: Int) {
        imgAvatarEdit.setImageDrawable(resources.getDrawable(listAdapter.getItem(position)))
        avatarId = listAdapter.getItem(position)
    }

}
