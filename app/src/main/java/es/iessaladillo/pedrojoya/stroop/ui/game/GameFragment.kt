package es.iessaladillo.pedrojoya.stroop.ui.game

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager

import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.database.AppDatabase
import es.iessaladillo.pedrojoya.stroop.database.Game
import kotlinx.android.synthetic.main.game_fragment.*
import kotlin.concurrent.thread

class GameFragment : Fragment(R.layout.game_fragment) {

    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }

    private val navController: NavController by lazy{
        findNavController()
    }

    private val viewModel: GameViewModel by viewModels {
        GameViewModelFactory(navController, AppDatabase.getInstance(this.requireContext()).playerDao, AppDatabase.getInstance(this.requireContext()).gameDao)
    }

    var gameId: Int = 0
    var playerId: Int = 0
    var time: String = ""
    var gameModeName: String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews(requireView())
    }

    private fun setupViews(view: View){
        playerId = settings.getInt(getString(R.string.selected_player_key), 0)
        time = settings.getString(getString(R.string.prefGameTime_key), R.string.prefGameTime_defaultValue.toString())!!
        gameModeName = settings.getString(getString(R.string.prefGameMode_key), R.string.prefGameMode_defaultValue.toString())!!

        setupGame()
        observers()
        setupButtons()
    }

    private fun observers(){
        viewModel.run{
            //Observar el tiempo restante
            timeLeftObserve.observe(viewLifecycleOwner){
                progressBar.progress = it
            }
            //Observar el numero de palabras mostradas
            wordsObserve.observe(viewLifecycleOwner){
                lblTotalWords.setText(wordsObserve.value.toString())
            }
            //Observar la palabra actual
            wordObserve.observe(viewLifecycleOwner){
                lblWord.setText(wordObserve.value)
            }
            //Observar el color actual
            currentColorObserve.observe(viewLifecycleOwner){
                lblWord.setTextColor(currentColorObserve.value!!)
            }
            //Observar los aciertos
            correctAnswersObserve.observe(viewLifecycleOwner){
                lblTotalCorrect.setText(correctAnswersObserve.value.toString())
            }
            if(gameModeName == "Time"){
                //Obervar los puntos
                totalPointsObserve.observe(viewLifecycleOwner){
                    lblTotalPoints.setText(totalPointsObserve.value.toString())
                }
            }
            else{
                //Observar los intentos
                totalAttemptsOberve.observe(viewLifecycleOwner){
                    lblTotalPoints.setText(totalAttemptsOberve.value.toString())
                }
            }
            isFinishedObserve.observe(viewLifecycleOwner){
                if(isFinishedObserve.value!!){
                    thread {
                        viewModel.insertGame(Game(0,
                            playerId,
                            gameModeName,
                            time.toInt(),
                            wordsObserve.value!!,
                            correctAnswersObserve.value!!
                        ))
                        gameId = viewModel.queryLastGame().gameId
                        settings.edit{
                            putInt(getString(R.string.current_game_key), gameId)
                        }
                    }
                    navController.navigate(R.id.action_gameDestination_to_resultDestination)
                }
            }

        }
    }

    private fun setupGame(){
        gameMode()

        var wordTime: String = settings.getString(getString(R.string.prefWordTime_key), R.string.prefWordTime_defaultValue.toString())!!

        progressBar.max = time.toInt()
        viewModel.startGameThread(time.toInt(), wordTime.toInt())
    }

    private fun setupButtons(){
        btnRight.setOnClickListener{ viewModel.checkRight() }
        btnWrong.setOnClickListener{ viewModel.checkWrong() }
    }

    private fun gameMode(){
        var gameModeName: String = settings.getString(getString(R.string.prefGameMode_key), R.string.prefGameMode_defaultValue.toString())!!
        viewModel.gameModeName = gameModeName


        if(gameModeName == "Attempts"){
            var totalAttemtps = settings.getString(getString(R.string.prefAttempts_key),
                R.string.prefAttempts_defaultValue.toString())

            viewModel.totalAttempts.value = totalAttemtps!!.toInt()
            lblGamePoints.setText(R.string.game_attempts)
        }
    }

}
