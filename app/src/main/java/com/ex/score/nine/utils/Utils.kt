package com.ex.score.nine.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.ex.score.nine.domain.models.AnswersModel
import com.ex.score.nine.domain.models.PlayerBio
import com.ex.score.nine.domain.models.TeamInfo
import com.ex.score.nine.presentation.sharedPreferences.SharedPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

object Utils {

    suspend fun addStringValue(preferences: DataStore<Preferences>, key: String, value: String) {
        preferences.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    suspend fun addBooleanValue(preferences: DataStore<Preferences>, key: String, value: Boolean) {
        preferences.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    suspend fun getStringValue(preferences: DataStore<Preferences>, key: String) = flow {
        val dataStoreKey = stringPreferencesKey(key)
        val preference = preferences.data.first()
        emit(preference[dataStoreKey])
    }

    suspend fun getBooleanValue(preferences: DataStore<Preferences>, key: String) = flow {
        val dataStoreKey = booleanPreferencesKey(key)
        val preference = preferences.data.first()
        emit(preference[dataStoreKey])
    }


    suspend fun getLocale(preferences: DataStore<Preferences>): String {
        return getStringValue(preferences, Constants.LOCALE_KEY).first() ?: Constants.CHINESE
    }

    fun setLocale(context: Context, locale: String) {
        SharedPreference.getInstance()
            .saveStringToPreferences(SharedPreference.LOCALE_KEY, locale, context)
    }

    fun getTeamIndex(teamsList: ArrayList<TeamInfo>, questionsList: ArrayList<String>): Int {
        val index = 0
        for (i in 0..teamsList.size) {
            if (!questionsList.contains(teamsList[i].photoUrl)) {
                return i
            }
        }
        return index
    }

     fun getPlayerIndex(playersList: ArrayList<PlayerBio>, questionsList: ArrayList<String>): Int {
        val index = 0
        for (i in 0..playersList.size) {
            if (!questionsList.contains(playersList[i].photoUrl)) {
                return i
            }
        }
        return index
    }

    fun generateAnswersList(
        correctAnswer: String,
        suggestionsList: ArrayList<String>
    ): ArrayList<AnswersModel> {
        val list = ArrayList<AnswersModel>()
        for (i in 0..2) {
            val randomName = generateAnswers(correctAnswer, suggestionsList, list)
            list.add(AnswersModel(false, randomName))
        }
        return list
    }


    fun generateAnswers(
        name: String,
        suggestionList: ArrayList<String>,
        answersList: ArrayList<AnswersModel>
    ): String {


        val randomIndex = Random.nextInt(suggestionList.size)
        val randomElement = suggestionList[randomIndex]
        println(randomIndex)
        return if (randomElement != name) {

            for (answer in answersList) {
                if (answer.answer == randomElement) {
                    generateAnswers(name, suggestionList, answersList)
                }
            }
            randomElement

        } else {
            generateAnswers(name, suggestionList, answersList)
        }

    }
}