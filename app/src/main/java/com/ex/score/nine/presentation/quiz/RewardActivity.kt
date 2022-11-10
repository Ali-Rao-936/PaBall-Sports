package com.ex.score.nine.presentation.quiz

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.ex.score.nine.BaseActivity
import com.ex.score.nine.R
import com.ex.score.nine.domain.models.Match
import com.ex.score.nine.presentation.MainActivity.Companion.matchesList
import com.ex.score.nine.presentation.MainActivity.Companion.suggestionTeamsList
import com.ex.score.nine.utils.Constants
import com.ex.score.nine.utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import kotlin.random.Random

@AndroidEntryPoint
class RewardActivity : BaseActivity() {

    val gson = Gson()
    private lateinit var suggestionsTeamNamesList: ArrayList<String>
    lateinit var teamsQuestionsList: ArrayList<Match>
    private var currentIndex = 0
    private lateinit var correctAnswer: String
    lateinit var savedQuestionsList: ArrayList<String>
    lateinit var teamAnswersList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward)

        teamsQuestionsList = matchesList
        suggestionsTeamNamesList = suggestionTeamsList


        // get alreadyAsked questionsList from preferences
        lifecycleScope.launch {
            val json =
                Utils.getStringValue(preferences, Constants.SAVED_QUESTIONS).first() ?: "no data"
            if (json != "no data") {
                // below line is to get the type of our array list.
                val type: Type = object : TypeToken<java.util.ArrayList<String?>?>() {}.type
                savedQuestionsList = gson.fromJson<Any>(json, type) as java.util.ArrayList<String>
                currentIndex = getIndex(teamsQuestionsList, savedQuestionsList)
            } else
                currentIndex = 0

            correctAnswer = teamsQuestionsList[currentIndex].homeName
            //generate random answers
            teamAnswersList = generateAnswersList(correctAnswer, suggestionsTeamNamesList)
            teamAnswersList.add(correctAnswer)

            println(teamAnswersList)
            shuffle(teamAnswersList)
            println(teamAnswersList)

            // when user answer is correct
//            val newPhotoUrl = teamsQuestionsList[currentIndex].homeLogo
//            //   save the question
//            savedQuestionsList.add(newPhotoUrl)
//            Utils.addStringValue(
//                preferences,
//                Constants.SAVED_QUESTIONS,
//                gson.toJson(savedQuestionsList)
//            )
//            //  remove from current questions list
//            teamsQuestionsList.removeAt(currentIndex)
//            currentIndex = getIndex(teamsQuestionsList, savedQuestionsList)
//            teamAnswersList.clear()
//            //    correctAnswer = teamsQuestionsList[currentIndex].homeName
//            teamAnswersList = generateAnswersList(correctAnswer, suggestionTeamsList)
//            teamAnswersList.add(correctAnswer)
//            shuffle(teamAnswersList)

        }
    }

    private fun getIndex(teamsList: ArrayList<Match>, questionsList: ArrayList<String>): Int {
        val index = 0
        for (i in 0..teamsList.size) {
            if (!questionsList.contains(teamsList[i].homeLogo)) {
                return i
            }
        }
        return index
    }

    private fun generateAnswersList(
        correctAnswer: String,
        suggestionsList: ArrayList<String>
    ): ArrayList<String> {
        val list = ArrayList<String>()
        for (i in 0..2) {
            val randomName = generateAnswers(correctAnswer, suggestionsList, list)
            list.add(randomName)
        }
        return list
    }

    private fun generateAnswers(
        name: String,
        suggestionList: ArrayList<String>,
        answersList: ArrayList<String>
    ): String {


        val randomIndex = Random.nextInt(suggestionList.size)
        val randomElement = suggestionList[randomIndex]
        println(randomIndex)
        return if (randomElement != name) {
            if (!answersList.contains(randomElement))
                randomElement
            else {
                generateAnswers(name, suggestionList, answersList)
            }
        } else {
            generateAnswers(name, suggestionList, answersList)
        }

    }

    private fun shuffle(list: MutableList<String>) {
        // start from the end of the list
        for (i in list.size - 1 downTo 1) {
            // get a random index `j` such that `0 <= j <= i`
            val j = Random.nextInt(i + 1)

            // swap element at i'th position in the list with the element at j'th position
            val temp = list[i]
            list[i] = list[j]
            list[j] = temp
        }
    }
}