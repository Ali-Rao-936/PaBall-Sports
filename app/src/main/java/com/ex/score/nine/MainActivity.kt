package com.ex.score.nine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ex.score.nine.domain.models.HomeBody
import com.ex.score.nine.domain.models.VideosResponse
import com.ex.score.nine.presentation.HomeStateModel
import com.ex.score.nine.presentation.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: HomeViewModel
    val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initObserver()

        viewModel.getMainStreams(HomeBody("", "", "ali@bluewhale.host"))
    }

    private fun initObserver() {
        viewModel.mState.flowWithLifecycle(
            this.lifecycle, Lifecycle.State.STARTED
        ).onEach {
            handleState(it)
        }.launchIn(this.lifecycleScope)
    }

    private fun handleState(state: HomeStateModel) {
        when (state) {
            is HomeStateModel.IsLoading -> handleIsLoadingState(state.isLoading)
            is HomeStateModel.VideoResponse -> handleMainStream(state.videos)
            is HomeStateModel.NoInternetException -> handleNetworkFailure(state.message)
            is HomeStateModel.GeneralException -> handleException(state.message)
            else -> {
            }
        }
    }

    private fun handleMainStream(videos: VideosResponse) {
        Log.d(TAG, "success...."+videos.data.videosMetaData.count())
    }


    private fun handleException(exception: Exception) {
        Log.d(TAG, exception.message.toString())
    }

    private fun handleIsLoadingState(loading: Boolean) {
        if (loading) {
            Log.d(TAG, "show loader....")
        } else {
            Log.d(TAG, "..... stop loader")
        }
    }

    private fun handleFailure(message: String) {

        Log.d(TAG, "failure    $message")
        showToast(message)
    }


    private fun handleNetworkFailure(message: String) {
        Log.d(TAG, "network    $message")
    }

    private fun handleException(message: String) {
        Log.d(TAG, "exception    $message")
    }


    private fun showToast(s: String) {
        Toast.makeText(
            this,
            s,
            Toast.LENGTH_SHORT
        )
            .show()
    }

}