package es.iessaladillo.pedrojoya.stroop.ui.player


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.avatars
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerCreationAdapter.ViewHolder
import kotlinx.android.extensions.LayoutContainer


class PlayerCreationAdapter: RecyclerView.Adapter<ViewHolder>() {

    var data: List<Int> = avatars
    private var onItemClickListener: PlayerCreationAdapter.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.player_creation_item, parent, false)
        return ViewHolder(itemView)
    }

    fun setOnItemClickListener(listener: PlayerCreationAdapter.OnItemClickListener){
        onItemClickListener = listener
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val avatar: Int = data[position]
        holder.bind(avatar)
    }

    fun getItem(position: Int): Int {
        return data[position]
    }


    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        val imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatar)

        init {
            containerView.setOnClickListener{ onItemClickListener?.onClick(adapterPosition) }
        }

        fun bind(resId: Int){
            imgAvatar.setImageDrawable(itemView.resources.getDrawable(resId))
        }
    }

    interface OnItemClickListener{
        fun onClick(position: Int)
    }
}