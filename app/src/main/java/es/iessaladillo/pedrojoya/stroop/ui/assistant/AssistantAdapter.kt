package es.iessaladillo.pedrojoya.stroop.ui.assistant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import kotlinx.android.extensions.LayoutContainer

class AssistantAdapter: RecyclerView.Adapter<AssistantAdapter.ViewHolder>() {

    private var onItemClickListener: AssistantAdapter.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.assistant_main_item, parent, false)
        return ViewHolder(itemView)
    }

    fun setOnItemClickListener(listener: AssistantAdapter.OnItemClickListener){
        onItemClickListener = listener
    }

    override fun getItemCount(): Int = 8

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{
        val assistantCV: CardView = containerView.findViewById(R.id.assistantCV)
        val imgAssistant: ImageView = containerView.findViewById(R.id.imgAssistant)
        val lblAssistant: TextView = containerView.findViewById(R.id.lblAssistant)
        val btnFinishAssistant: Button = containerView.findViewById(R.id.btnFinishAssistant)

        init {
            btnFinishAssistant.setOnClickListener{ onItemClickListener?.onClick() }
        }

        fun bind (position: Int){

            when(position){
                0 -> paintAssistantStroop()
                1 -> paintAssistantPlay()
                2 -> paintAssistantSettings()
                3 -> paintAssistantRanking()
                4 -> paintAssistantAssistant()
                5 -> paintAssistantPlayer()
                6 -> paintAssistantAbout()
                7 -> paintAssistantFinish()
            }
        }

        private fun paintAssistantStroop(){
            assistantCV.setCardBackgroundColor(itemView.resources.getColor(R.color.stroopOption))
            imgAssistant.setImageDrawable(itemView.resources.getDrawable(R.drawable.logo))
            lblAssistant.setText(R.string.assistant_stroopDescription)
            btnFinishAssistant.isVisible = false;
        }

        private fun paintAssistantPlay(){
            assistantCV.setCardBackgroundColor(itemView.resources.getColor(R.color.playOption))
            imgAssistant.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_play_black_24dp))
            lblAssistant.setText(R.string.assistant_playDescription)
            btnFinishAssistant.isVisible = false;
        }

        private fun paintAssistantSettings(){
            assistantCV.setCardBackgroundColor(itemView.resources.getColor(R.color.settingsOption))
            imgAssistant.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_settings_black_24dp))
            lblAssistant.setText(R.string.assistant_settingsDescription)
            btnFinishAssistant.isVisible = false;
        }

        private fun paintAssistantRanking(){
            assistantCV.setCardBackgroundColor(itemView.resources.getColor(R.color.rankingOption))
            imgAssistant.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_ranking_black_24dp))
            lblAssistant.setText(R.string.assistant_rankingDescription)
            btnFinishAssistant.isVisible = false;
        }

        private fun paintAssistantAssistant(){
            assistantCV.setCardBackgroundColor(itemView.resources.getColor(R.color.assistantOption))
            imgAssistant.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_assistant_black_24dp))
            lblAssistant.setText(R.string.assistant_assistantDescription)
            btnFinishAssistant.isVisible = false;
        }

        private fun paintAssistantPlayer(){
            assistantCV.setCardBackgroundColor(itemView.resources.getColor(R.color.playOption))
            imgAssistant.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_player_add_black_24dp))
            lblAssistant.setText(R.string.assistant_playerDescription)
            btnFinishAssistant.isVisible = false;
        }

        private fun paintAssistantAbout(){
            assistantCV.setCardBackgroundColor(itemView.resources.getColor(R.color.aboutOption))
            imgAssistant.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_about_black_24dp))
            lblAssistant.setText(R.string.assistant_aboutDescription)
            btnFinishAssistant.isVisible = false;
        }

        private fun paintAssistantFinish(){
            assistantCV.setCardBackgroundColor(itemView.resources.getColor(R.color.finishOption))
            imgAssistant.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_finish_black_24dp))
            lblAssistant.setText(R.string.assistant_finishDescription)
            btnFinishAssistant.isVisible = true;
        }
    }

    interface OnItemClickListener{
        fun onClick()
    }
}