package com.ex.score.nine.presentation.fragments.quiz_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ex.score.nine.R
import com.ex.score.nine.domain.models.*
import com.ex.score.nine.presentation.SplashScreen.Companion.playersList
import com.ex.score.nine.presentation.SplashScreen.Companion.suggestionPlayersList
import com.ex.score.nine.presentation.SplashScreen.Companion.suggestionTeamsList
import com.ex.score.nine.presentation.SplashScreen.Companion.teamsList
import com.ex.score.nine.presentation.adapters.AdapterAnswar
import com.ex.score.nine.presentation.adapters.AdapterAnswar.PassTheAnswer
import com.ex.score.nine.presentation.adapters.AdapterAnswarWithoutListener
import com.ex.score.nine.presentation.fragments.reward_fragments.RewardFragment
import com.ex.score.nine.presentation.quiz.QuizActivity
import com.ex.score.nine.presentation.sharedPreferences.Functions
import com.ex.score.nine.presentation.sharedPreferences.QuizInfo.getCorrectAnswerFromSP
import com.ex.score.nine.presentation.sharedPreferences.QuizInfo.increCorrectAnswerFromSP
import com.ex.score.nine.presentation.sharedPreferences.TeamsOrPlayers
import com.ex.score.nine.utils.Constants
import com.ex.score.nine.utils.Utils
import com.ex.score.nine.utils.Utils.generateAnswersList
import com.ex.score.nine.utils.Utils.getPlayerIndex
import com.ex.score.nine.utils.Utils.getTeamIndex
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.util.*
import java.util.Collections.shuffle
import javax.inject.Inject
import kotlin.concurrent.schedule


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class FragmentQuestion : Fragment(), PassTheAnswer {

    @Inject
    lateinit var preference: DataStore<Preferences>

    private var postID: String? = null
    private var param2: String? = null

    private var first_details_text: TextView? = null
    var back_rl: RelativeLayout? = null
    var question_number: TextView? = null
    var qp_number_tv: TextView? = null
    var first_details_info: TextView? = null
    var sec_details_text: TextView? = null
    var sec_details_info: TextView? = null
    var thr_details_text: TextView? = null
    var thr_details_info: TextView? = null
    var fou_details_text: TextView? = null
    var fou_details_info: TextView? = null
    var question_number_tv: TextView? = null
    var image_view: ImageView? = null
    var recycler_view_answers: RecyclerView? = null

    lateinit var questionText : TextView

    val gson = Gson()
    private lateinit var suggestionsTeamNamesList: ArrayList<String>
    lateinit var teamsQuestionsList: ArrayList<TeamInfo>
    private var currentIndex = 0
    private lateinit var correctAnswer: String
    lateinit var answersList: ArrayList<AnswersModel>
    private var savedQuestionsList = ArrayList<String>()
    private lateinit var suggestionsList: ArrayList<String>
    lateinit var showPlayersList: ArrayList<PlayerBio>
    lateinit var teamInfo: TeamInfo
    lateinit var playerBio: PlayerBio

    var adapterAnswar: AdapterAnswar? = null
    var adapterAnswarWithoutListener: AdapterAnswarWithoutListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postID = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // get teams List
        teamsQuestionsList = teamsList
        suggestionsTeamNamesList = suggestionTeamsList

        // get players list
        suggestionsList = suggestionPlayersList
        showPlayersList = playersList


        // get alreadyAsked questionsList from preferences
        lifecycleScope.launch {
            val json =
                Utils.getStringValue(preference, Constants.SAVED_QUESTIONS).first() ?: "no data"
            if (json != "no data") {
                // below line is to get the type of our array list.
                val type: Type = object : TypeToken<java.util.ArrayList<String?>?>() {}.type
                savedQuestionsList = gson.fromJson<Any>(json, type) as ArrayList<String>
                println(savedQuestionsList)
            } else
                currentIndex = 0


            cast(view)
            actionListenerToBack()
            setNumberOfPointsCollect()

            if (TeamsOrPlayers.getTeamsOrPlayersInSP(activity?.applicationContext).isNotEmpty()
                &&
                (TeamsOrPlayers.getTeamsOrPlayersInSP(activity?.applicationContext).equals("empty")
                        || TeamsOrPlayers.getTeamsOrPlayersInSP(activity?.applicationContext)
                    .equals("teams"))
            ) {
                questionText.text = getString(R.string.guess_the_team)
                currentIndex = getTeamIndex(teamsQuestionsList, savedQuestionsList)
                correctAnswer = teamsQuestionsList[currentIndex].homeName
                teamInfo = teamsQuestionsList[currentIndex]
                //generate random answers
                answersList = generateAnswersList(correctAnswer, suggestionsTeamNamesList)
                answersList.add(AnswersModel(true, correctAnswer))
                setTeamDetails(teamInfo)
            } else {
                //for players
                questionText.text = getString(R.string.guess_the_player)
                currentIndex = getPlayerIndex(showPlayersList, savedQuestionsList)
                correctAnswer = showPlayersList[currentIndex].name
                playerBio = showPlayersList[currentIndex]
                //generate random answers
                answersList = generateAnswersList(correctAnswer, suggestionPlayersList)
                answersList.add(AnswersModel(true, correctAnswer))
                setPlayerDetails(playerBio)
            }


            println(answersList)
            shuffle(answersList)
            println(answersList)

            if (answersList.isNotEmpty())
            createAnswerRV(answersList)

//            teamAnswersList = generateAnswersList(correctAnswer, suggestionTeamsList)
//            teamAnswersList.add(correctAnswer)
//            shuffle(teamAnswersList)

        }

    }

    var answersArrayList = ArrayList<AnswersModelT>()

    private fun createAnswerRV(answersList: ArrayList<AnswersModel>) {
        //  answersArrayList = Functions.fillTestAnswer(activity?.applicationContext)
        answersArrayList.clear()
        answersArrayList.add(
            AnswersModelT(
                answersList[0].correctOrNo,
                answersList[0].answer,
                context!!.resources.getString(R.string.a)
            )
        )
        answersArrayList.add(
            AnswersModelT(
                answersList[1].correctOrNo,
                answersList[1].answer,
                context!!.resources.getString(R.string.b)
            )
        )
        answersArrayList.add(
            AnswersModelT(
                answersList[2].correctOrNo,
                answersList[2].answer,
                context!!.resources.getString(R.string.c)
            )
        )
        answersArrayList.add(
            AnswersModelT(
                answersList[3].correctOrNo,
                answersList[3].answer,
                context!!.resources.getString(R.string.d)
            )
        )

        recycler_view_answers?.setHasFixedSize(true)
        val mLayoutManager = GridLayoutManager(activity?.applicationContext, 1)
        recycler_view_answers?.layoutManager = mLayoutManager
        adapterAnswar = AdapterAnswar(activity?.applicationContext, answersArrayList, this)
        recycler_view_answers?.adapter = adapterAnswar
    }

    private fun fillImage(url: String) {
        activity?.applicationContext?.let { Glide.with(it).load(url).into(image_view!!) }
    }

    private fun setNumberOfPointsCollect() {
        var i = getCorrectAnswerFromSP(activity?.applicationContext).toInt()
        i *= 10

        question_number?.text =
            activity?.resources?.getString(R.string.question_number) + " " + getCorrectAnswerFromSP(
                activity?.applicationContext
            )
        qp_number_tv?.text =
            activity?.resources?.getString(R.string.qp) + " " + java.lang.String.valueOf(
                i
            )

    }

    private fun actionListenerToBack() {
        back_rl?.setOnClickListener {
            (activity as QuizActivity?)?.goBack()
        }
    }


    private fun setTeamDetails(team: TeamInfo) {
        first_details_text?.text = activity?.resources?.getString(R.string.league)
        first_details_info?.text = team.leagueName

        thr_details_text?.text = activity?.resources?.getString(R.string.current_location)
        thr_details_info?.text = team.currentLocation

        fillImage(team.photoUrl)

        sec_details_text?.visibility = View.GONE
        sec_details_info?.visibility = View.GONE

        fou_details_text?.visibility = View.GONE
        fou_details_info?.visibility = View.GONE
    }

    private fun setPlayerDetails(player: PlayerBio) {
        first_details_text?.text = activity?.resources?.getString(R.string.current_location)
        first_details_info?.text = player.country

        thr_details_text?.text = activity?.resources?.getString(R.string.height)
        thr_details_info?.text = player.height + " cm"
        println(" player height   "+player.height)

        fillImage(player.photoUrl)

        sec_details_text?.visibility = View.GONE
        sec_details_info?.visibility = View.GONE

        fou_details_text?.visibility = View.GONE
        fou_details_info?.visibility = View.GONE
    }

    private fun cast(view: View) {
        back_rl = view.findViewById(R.id.back_rl)
        question_number = view.findViewById(R.id.question_number_tv)
        qp_number_tv = view.findViewById(R.id.qp_number_tv)

        first_details_text = view.findViewById(R.id.first_details_text)
        first_details_info = view.findViewById(R.id.first_details_info)
        sec_details_text = view.findViewById(R.id.sec_details_text)
        sec_details_info = view.findViewById(R.id.sec_details_info)
        thr_details_text = view.findViewById(R.id.thr_details_text)
        thr_details_info = view.findViewById(R.id.thr_details_info)
        fou_details_text = view.findViewById(R.id.fou_details_text)
        fou_details_info = view.findViewById(R.id.fou_details_info)
        question_number_tv = view.findViewById(R.id.question_number_tv)
        image_view = view.findViewById(R.id.image_view)
        recycler_view_answers = view.findViewById(R.id.recycler_view_answers)
        questionText = view.findViewById(R.id.txtQuestion)

    }

    companion object {
        @JvmStatic
        fun newInstance(postID: String, unused: String) =
            FragmentQuestion().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, postID)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(answer: Boolean,answer_model:AnswersModelT) {


        if (answer)
            doForCorrectAnswer()
        else {
            createAnswerWithoutListener(answer_model)
            Toast.makeText(context, "Wrong Answer", Toast.LENGTH_LONG).show()
        }
    }

    var answersWithoutListenerArrayList = ArrayList<AnswersModelIncorrect>()

    private fun createAnswerWithoutListener(answer_model:AnswersModelT) {
        //answersArrayList
        answersWithoutListenerArrayList.clear()
        answersWithoutListenerArrayList.add(
            AnswersModelIncorrect(
                answersList[0].correctOrNo,
                1,
                answersList[0].answer,
                context!!.resources.getString(R.string.a)
            )
        )
        answersWithoutListenerArrayList.add(
            AnswersModelIncorrect(
                answersList[1].correctOrNo,
                1,
                answersList[1].answer,
                context!!.resources.getString(R.string.b)
            )
        )
        answersWithoutListenerArrayList.add(
            AnswersModelIncorrect(
                answersList[2].correctOrNo,
                1,
                answersList[2].answer,
                context!!.resources.getString(R.string.c)
            )
        )
        answersWithoutListenerArrayList.add(
            AnswersModelIncorrect(
                answersList[3].correctOrNo,
                1,
                answersList[3].answer,
                context!!.resources.getString(R.string.d)
            )
        )



        for (i in answersWithoutListenerArrayList)
        {
            if (i.answer == answer_model.answer)
            {
                //user choose then shod to set case number 2
                i.check = 2
            }

            if (i.correctOrNo)
            {
                //user choose then shod to set case number 3
                i.check = 3

            }
        }
        recycler_view_answers?.setHasFixedSize(true)
        val mLayoutManager = GridLayoutManager(activity?.applicationContext, 1)
        recycler_view_answers?.layoutManager = mLayoutManager
        adapterAnswarWithoutListener = AdapterAnswarWithoutListener(activity?.applicationContext, answersWithoutListenerArrayList)
        recycler_view_answers?.adapter = adapterAnswarWithoutListener

        Timer("delayedxTime", true).schedule(2000) {
            val frag = RewardFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container_question_fragment, frag)
                .commit()
        }
    }

    private fun doForCorrectAnswer() {
        // when user answer is correct

        Timer("delayTime", true).schedule(2000) {

            lifecycleScope.launch {
                if (TeamsOrPlayers.getTeamsOrPlayersInSP(activity?.applicationContext).isNotEmpty()
                    &&
                    (TeamsOrPlayers.getTeamsOrPlayersInSP(activity?.applicationContext)
                        .equals("empty")
                            || TeamsOrPlayers.getTeamsOrPlayersInSP(activity?.applicationContext)
                        .equals("teams"))
                ) {
                    val newPhotoUrl = teamsQuestionsList[currentIndex].photoUrl
                    //   save the question
                    savedQuestionsList.add(newPhotoUrl)
                    Utils.addStringValue(
                        preference,
                        Constants.SAVED_QUESTIONS,
                        gson.toJson(savedQuestionsList)
                    )
                    //  remove from current questions list
                    teamsQuestionsList.removeAt(currentIndex)
                    currentIndex = getTeamIndex(teamsQuestionsList, savedQuestionsList)
                    answersList.clear()
                    correctAnswer = teamsQuestionsList[currentIndex].homeName
                    answersList = generateAnswersList(correctAnswer, suggestionTeamsList)
                    answersList.add(AnswersModel(true, correctAnswer))
                    shuffle(answersList)
                    setTeamDetails(teamsQuestionsList[currentIndex])
                    createAnswerRV(answersList)
                } else {
                    Log.d("Qoo", " in players after correct")
                    val newPhotoUrl = showPlayersList[currentIndex].photoUrl
                    // save the question
                    savedQuestionsList.add(newPhotoUrl)

                    Utils.addStringValue(
                        preference,
                        Constants.SAVED_QUESTIONS,
                        gson.toJson(savedQuestionsList)
                    )
                    // remove from current questions list
                    showPlayersList.removeAt(currentIndex)
                    currentIndex = getPlayerIndex(playersList, savedQuestionsList)
                    answersList.clear()
                    correctAnswer = showPlayersList[currentIndex].name
                    answersList = generateAnswersList(correctAnswer, suggestionsList)
                    answersList.add(AnswersModel(true, correctAnswer))
                    shuffle(answersList)
                    println(answersList)
                    setPlayerDetails(showPlayersList[currentIndex])
                    increCorrectAnswerFromSP(context)
                    // increase question nuber
                    setNumberOfPointsCollect()
                    createAnswerRV(answersList)
                }
            }
        }
    }
}