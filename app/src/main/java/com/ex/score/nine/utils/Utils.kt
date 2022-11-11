package com.ex.score.nine.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

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

    suspend fun addDoubleValue(preferences: DataStore<Preferences>, key: String, value: Double) {
        preferences.edit {
            it[doublePreferencesKey(key)] = value
        }
    }

    suspend fun getDoubleValue(preferences: DataStore<Preferences>, key: String) = flow {
        val dataStoreKey = doublePreferencesKey(key)
        val preference = preferences.data.first()
        emit(preference[dataStoreKey])
    }

    suspend fun setLocale(preferences: DataStore<Preferences>, value : String){
        addStringValue(preferences, Constants.LOCALE_KEY, value)
    }

    suspend fun getLocale(preferences: DataStore<Preferences>) : String {
        return getStringValue(preferences, Constants.LOCALE_KEY).first() ?: Constants.CHINESE
    }
}