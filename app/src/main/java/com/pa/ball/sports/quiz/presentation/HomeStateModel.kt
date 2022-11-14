package com.pa.ball.sports.quiz.presentation

import com.pa.ball.sports.quiz.domain.models.BaseClassIndexNew
import com.pa.ball.sports.quiz.domain.models.lineup.Players

sealed class HomeStateModel {
    object Init : HomeStateModel()
    data class IsLoading(val isLoading: Boolean) : HomeStateModel()
    data class NoInternetException(val message:String): HomeStateModel()
    data class GeneralException(val message:String):HomeStateModel()
    data class StatusFailed(val message: String) : HomeStateModel()
    data class Response(val videos: BaseClassIndexNew) : HomeStateModel()
    data class PlayersResponse(val players : Players) : HomeStateModel()
}