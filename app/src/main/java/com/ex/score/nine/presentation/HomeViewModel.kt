package com.ex.score.nine.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.score.nine.data.network.DataState
import com.ex.score.nine.data.network.NoConnectionException
import com.ex.score.nine.data.network.NoInternetException
import com.ex.score.nine.domain.repo.ApiRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repo : ApiRepo) : ViewModel(){

    private val state = MutableStateFlow<HomeStateModel>(HomeStateModel.Init)
    val mState: StateFlow<HomeStateModel> get() = state
    private val TAG = "HomeViewModel"
    private fun setLoading() {

        state.value = HomeStateModel.IsLoading(true)
    }

    private fun hideLoading() {

        state.value = HomeStateModel.IsLoading(false)
    }

    fun getMatches(locale : String, pageNumber : String) {
        viewModelScope.launch {
            try {
                repo.getHomeMatches(locale, pageNumber).onStart {
                    Log.d(TAG, " Called on start")
                    setLoading()
                }
                    .collect{
                        hideLoading()
                        Log.d(TAG, " Called collect")
                        when (it) {
                            is DataState.GenericError -> {
                                Log.d(TAG, " Called Generic error")
                                state.value = HomeStateModel.StatusFailed("Generic error")
                            }

                            is DataState.Success -> {
                                Log.d(TAG, "Enter SUCCESS")
                                state.value = it.value.let { it1 ->
                                    HomeStateModel.Response(it1)
                                }
                            }
                        }
                    }
            }
            catch (e : Exception){
                when (e) {
                    is NoInternetException -> {
                        state.value = HomeStateModel.NoInternetException(e.message)
                    }
                    is NoConnectionException -> {
                        state.value = HomeStateModel.NoInternetException(e.message)
                    }
                    else -> {
                        state.value =
                            HomeStateModel.GeneralException(e.message ?: "Exception Occurred")
                    }
                }
            }
        }
    }

    fun getPlayers() {
        viewModelScope.launch {
            try {
                repo.getPlayersData().onStart {
                    Log.d(TAG, " Called on start")
                    setLoading()
                }
                    .collect{
                        hideLoading()
                        Log.d(TAG, " Called collect")
                        when (it) {
                            is DataState.GenericError -> {
                                Log.d(TAG, " Called Generic error")
                                state.value = HomeStateModel.StatusFailed("Generic error")
                            }

                            is DataState.Success -> {
                                Log.d(TAG, "Enter SUCCESS")
                                state.value = it.value.let { it1 ->
                                    HomeStateModel.PlayersResponse(it1)
                                }
                            }
                        }
                    }
            }
            catch (e : Exception){
                when (e) {
                    is NoInternetException -> {
                        state.value = HomeStateModel.NoInternetException(e.message)
                    }
                    is NoConnectionException -> {
                        state.value = HomeStateModel.NoInternetException(e.message)
                    }
                    else -> {
                        state.value =
                            HomeStateModel.GeneralException(e.message ?: "Exception Occurred")
                    }
                }
            }
        }
    }

}