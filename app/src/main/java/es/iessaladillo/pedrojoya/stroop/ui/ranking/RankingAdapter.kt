package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.database.Game
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerSelectionAdapter
import kotlinx.android.extensions.LayoutContainer

class RankingAdapter: RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    lateinit var listData: List<Game>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.ranking_item, parent, false)
        return ViewHolder(itemView)
    }

    fun submitData(newData: List<Game>){
        listData = newData
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: RankingAdapter.ViewHolder, position: Int) {
        var game: Game = listData[position]
        holder.bind(game)
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        val imgRankingAvatar: ImageView = containerView.findViewById(R.id.imgRankingAvatar)
        val lblRankingNickname: TextView = containerView.findViewById(R.id.lblRankingNickname)
        val lblRankingGameMode: TextView = containerView.findViewById(R.id.lblRankingGamemode)
        val lblRankingMinutes: TextView = containerView.findViewById(R.id.lblRankingMinutes)
        val lblRankingWords: TextView = containerView.findViewById(R.id.lblRankingWords)
        val lblRankingCorrectAnswers: TextView = containerView.findViewById(R.id.lblRankingCorrectAnswers)
        val lblRankingPoints: TextView = containerView.findViewById(R.id.lblRankingPoints)

        fun bind(game: Game){
            imgRankingAvatar.setImageDrawable(containerView.resources.getDrawable(game.player.avatarId))
            lblRankingNickname.setText(game.player.nickname)
            lblRankingGameMode.setText(containerView.resources.getString(R.string.ranking_item_gameMode, game.gameMode))
            lblRankingMinutes.setText(containerView.resources.getString(R.string.ranking_item_time, game.time))
            lblRankingWords.setText(containerView.resources.getString(R.string.ranking_item_words, game.words))
            lblRankingCorrectAnswers.setText(containerView.resources.getString(R.string.ranking_item_correct, game.correct))
            lblRankingPoints.setText(containerView.resources.getString(R.string.totalPoints, game.correct * 10))
        }
    }
}