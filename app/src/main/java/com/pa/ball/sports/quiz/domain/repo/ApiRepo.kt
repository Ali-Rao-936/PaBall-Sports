package com.pa.ball.sports.quiz.domain.repo

import com.pa.ball.sports.quiz.data.network.DataState
import com.pa.ball.sports.quiz.domain.models.BaseClassIndexNew
import com.pa.ball.sports.quiz.domain.models.lineup.Players
import kotlinx.coroutines.flow.Flow

interface ApiRepo {

    suspend fun getHomeMatches(locale : String, pageNumber : String) : Flow<DataState<BaseClassIndexNew>>

    suspend fun getPlayersData() : Flow<DataState<Players>>
}