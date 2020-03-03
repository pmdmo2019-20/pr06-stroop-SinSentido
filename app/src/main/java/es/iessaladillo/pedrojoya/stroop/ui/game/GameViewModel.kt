package es.iessaladillo.pedrojoya.stroop.ui.game

import android.graphics.Color
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import es.iessaladillo.pedrojoya.stroop.database.Game
import es.iessaladillo.pedrojoya.stroop.database.GameDao
import es.iessaladillo.pedrojoya.stroop.database.Player
import es.iessaladillo.pedrojoya.stroop.database.PlayerDao
import kotlin.concurrent.thread


class GameViewModel(val navController: NavController, val playerDao: PlayerDao, val gameDao: GameDao) : ViewModel() {

    @Volatile
    private var isGameFinished: Boolean = false
    @Volatile
    private var currentWordMillis: Int = 0
    @Volatile
    private var millisUntilFinished: Int = 0
    private val handler: Handler = Handler()

    var gameModeName: String = ""

    val wordList: List<String> = listOf("RED", "GREEN", "YELLOW", "BLUE")
    val colorList: List<Int> = listOf(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE)

    var currentColor: MutableLiveData<Int> = MutableLiveData(colorList.random())
    val currentColorObserve: MutableLiveData<Int> get() = currentColor

    var word: MutableLiveData<String> = MutableLiveData(wordList.random())
    val wordObserve: LiveData<String> get() = word

    var timeLeft: MutableLiveData<Int> = MutableLiveData(millisUntilFinished)
    val timeLeftObserve: LiveData<Int> get() = timeLeft

    var words: MutableLiveData<Int> = MutableLiveData(0)
    val wordsObserve: LiveData<Int> get() = words

    var correctAnswers: MutableLiveData<Int> = MutableLiveData(0)
    val correctAnswersObserve: LiveData<Int> get() = correctAnswers

    var totalPoints: MutableLiveData<Int> = MutableLiveData(0)
    val totalPointsObserve: LiveData<Int> get() = totalPoints

    var totalAttempts: MutableLiveData<Int> = MutableLiveData(0)
    val totalAttemptsOberve: LiveData<Int> get() = totalAttempts

    var isFinished: MutableLiveData<Boolean> = MutableLiveData(isGameFinished)
    val isFinishedObserve: LiveData<Boolean> get() = isFinished

    private fun onGameTimeTick(millisUntilFinished: Int) {
        timeLeft.value = millisUntilFinished
    }

    private fun onGameTimeFinish() {
        isGameFinished = true
        isFinished.value = true
    }

    fun nextWord() {
        //Increments the words counter
        words.value = words.value?.plus(1)
        //Changes word value
        word.value = wordList.random()
        //Changes color value
        currentColor.value = colorList.random()
    }

    fun checkRight() {
        currentWordMillis = 0
        if(isRightAnswer()){
            correctAnswers.value = correctAnswers.value?.plus(1)
            totalPoints.value = totalPoints.value?.plus(10)
        }
        else{
            if(gameModeName == "Attempts"){
                totalAttempts.value = totalAttempts.value?.minus(1)
                if(totalAttempts.value == 0){
                    onGameTimeFinish()
                }
            }
        }
        nextWord()
    }

    fun checkWrong() {
        currentWordMillis = 0
        if(!isRightAnswer()){
            correctAnswers.value = correctAnswers.value?.plus(1)
            totalPoints.value = totalPoints.value?.plus(10)
        }
        else{
            if(gameModeName == "Attempts"){
                totalAttempts.value = totalAttempts.value?.minus(1)
                if(totalAttempts.value == 0){
                    onGameTimeFinish()
                }
            }
        }
        nextWord()
    }

    private fun isRightAnswer(): Boolean{
        return wordList.indexOf(word.value) == colorList.indexOf(currentColor.value)
    }


    fun queryPlayerById(playerId: Int): Player{
        return playerDao.queryPlayerById(playerId)
    }

    fun insertGame(playerId: Int, gameMode: String, time: Int, words: Int, correct: Int){
        gameDao.insertGame(Game(0, playerId, gameMode, time, words, correct))
    }

    fun queryLastGame(): Game{
        return gameDao.queryLastGame()
    }


    fun startGameThread(gameTime: Int, wordTime: Int) {
        millisUntilFinished = gameTime
        currentWordMillis = 0
        isGameFinished = false
        val checkTimeMillis: Int = wordTime / 2
        thread {
            try {
                while (!isGameFinished) {
                    Thread.sleep(checkTimeMillis.toLong())
                    // Check and publish on main thread.
                    handler.post {
                        if (!isGameFinished) {
                            if (currentWordMillis >= wordTime) {
                                currentWordMillis = 0
                                nextWord()
                            }
                            currentWordMillis += checkTimeMillis
                            millisUntilFinished -= checkTimeMillis
                            onGameTimeTick(millisUntilFinished)
                            if (millisUntilFinished <= 0) {
                                onGameTimeFinish()
                            }
                        }
                    }
                }
            } catch (ignored: Exception) {
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        isGameFinished = true
    }

}