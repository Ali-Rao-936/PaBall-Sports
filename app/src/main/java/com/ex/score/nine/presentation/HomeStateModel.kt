package com.ex.score.nine.presentation

import com.ex.score.nine.domain.models.BaseClassIndexNew
import com.ex.score.nine.domain.models.lineup.Players

sealed class HomeStateModel {
    object Init : HomeStateModel()
    data class IsLoading(val isLoading: Boolean) : HomeStateModel()
    data class NoInternetException(val message:String): HomeStateModel()
    data class GeneralException(val message:String):HomeStateModel()
    data class StatusFailed(val message: String) : HomeStateModel()
    data class Response(val videos: BaseClassIndexNew) : HomeStateModel()
    data class PlayersResponse(val players : Players) : HomeStateModel()
}