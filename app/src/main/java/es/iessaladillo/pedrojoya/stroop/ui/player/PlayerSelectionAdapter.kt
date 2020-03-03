package es.iessaladillo.pedrojoya.stroop.ui.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.database.Player
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerSelectionAdapter.ViewHolder
import kotlinx.android.extensions.LayoutContainer

class PlayerSelectionAdapter: RecyclerView.Adapter<ViewHolder>()  {

    lateinit var dataList: List<Player>
    private var onItemClickListener: PlayerSelectionAdapter.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.player_selection_item, parent, false)
        return ViewHolder(itemView)
    }

    fun setOnItemClickListener(listener: PlayerSelectionAdapter.OnItemClickListener){
        onItemClickListener = listener
    }

    fun submitData(newData: List<Player>){
        dataList = newData
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var player: Player = dataList[position]
        holder.bind(player)
    }


    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        val imgPlayerSelection: ImageView = containerView.findViewById(R.id.imgPlayerSelection)
        val lblNicknamePlayerSelection: TextView = containerView.findViewById(R.id.lblNiknamePlayerSelection)

        init {
           containerView.setOnClickListener{ onItemClickListener?.onClick(dataList[adapterPosition]) }
        }

        fun bind(player: Player){
            imgPlayerSelection.setImageDrawable(containerView.resources.getDrawable(player.avatarId))
            lblNicknamePlayerSelection.setText(player.nickname)
        }

    }

    interface OnItemClickListener{
        fun onClick(player: Player)
    }


}