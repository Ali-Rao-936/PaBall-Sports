package com.ex.score.nine.presentation

import com.ex.score.nine.domain.models.VideosResponse

sealed class HomeStateModel {
    object Init : HomeStateModel()
    data class IsLoading(val isLoading: Boolean) : HomeStateModel()
    data class NoInternetException(val message:String): HomeStateModel()
    data class GeneralException(val message:String):HomeStateModel()
    data class VideoResponse(val videos: VideosResponse) : HomeStateModel()
}