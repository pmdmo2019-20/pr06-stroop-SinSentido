package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.database.GamePlayer
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.ranking_item.*

class RankingAdapter: RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    lateinit var listData: List<GamePlayer>
    private var onItemClickListener: RankingAdapter.OnItemClickListener? = null

    init {
        setHasStableIds(true);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.ranking_item, parent, false)
        return ViewHolder(itemView)
    }

    fun setOnItemClickListener(listener: RankingAdapter.OnItemClickListener){
        onItemClickListener = listener
    }

    fun submitData(newData: List<GamePlayer>){
        listData = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: RankingAdapter.ViewHolder, position: Int) {
        var game: GamePlayer = listData[position]
        holder.bind(game)
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener{ onItemClickListener?.onClick(listData[adapterPosition]) }
        }

        fun bind(gamePlayer: GamePlayer){
            imgRankingAvatar.setImageDrawable(containerView.resources.getDrawable(gamePlayer.player.avatarId))
            lblRankingNickname.setText(gamePlayer.player.nickname)
            lblRankingGamemode.setText(containerView.resources.getString(R.string.ranking_item_gameMode, gamePlayer.game.gameMode))
            lblRankingMinutes.setText(containerView.resources.getString(R.string.ranking_item_time, gamePlayer.game.time))
            lblRankingWords.setText(containerView.resources.getString(R.string.ranking_item_words, gamePlayer.game.words))
            lblRankingCorrectAnswers.setText(containerView.resources.getString(R.string.ranking_item_correct, gamePlayer.game.correct))
            lblRankingPoints.setText(containerView.resources.getString(R.string.totalPoints, gamePlayer.game.correct * 10))
        }
    }

    interface OnItemClickListener{
        fun onClick(gamePlayer: GamePlayer)
    }
}