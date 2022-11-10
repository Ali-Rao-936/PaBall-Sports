package com.ex.score.nine.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.score.nine.data.network.DataState
import com.ex.score.nine.data.network.NoConnectionException
import com.ex.score.nine.data.network.NoInternetException
import com.ex.score.nine.domain.models.HomeBody
import com.ex.score.nine.domain.repo.ApiRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repo : ApiRepo) : ViewModel(){

    private val state = MutableStateFlow<HomeStateModel>(HomeStateModel.Init)
    val mState: StateFlow<HomeStateModel> get() = state
    private val TAG = "MainViewModel"
    private fun setLoading() {

        state.value = HomeStateModel.IsLoading(true)
    }

    private fun hideLoading() {

        state.value = HomeStateModel.IsLoading(false)
    }


    fun getMainStreams(body: HomeBody) {
        viewModelScope.launch {
            try {
                repo.getVideosData(body).onStart {
                    Log.d(TAG, " Called on start")
                    setLoading()
                }
                    .collect{
                        hideLoading()
                        Log.d(TAG, " Called collect")
                        when (it) {
                            is DataState.GenericError -> {
                                Log.d(TAG, " Called Generic error")
                            }

                            is DataState.Success -> {
                                Log.d(TAG, "Enter SUCCESS")
//                                val status = it.value.response?.status ?: "Unknown"
//                                if (status == "FAILED") {
//                                    val message =
//                                        it.value.response?.message ?: "Unknown FAILED message"
//                                    Log.d(TAG, message)
//                                    state.value = HomeStateModel.StatusFailed(message)
//                                } else {
//
//                                    state.value = it.value.data?.let { it1 ->
//                                        HomeStateModel.VideosResponse(it1)
//                                    }!!
//                                }
                                state.value = it.value.let { it1 ->
                                    HomeStateModel.VideoResponse(it1)
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