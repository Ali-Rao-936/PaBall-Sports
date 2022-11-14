package com.pa.ball.sports.quiz.data


import com.pa.ball.sports.quiz.data.network.DataState
import com.pa.ball.sports.quiz.domain.models.BaseClassIndexNew
import com.pa.ball.sports.quiz.domain.models.lineup.Players
import com.pa.ball.sports.quiz.domain.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepoImp @Inject constructor(private val api: Api) : ApiRepo {

    override suspend fun getHomeMatches(
        locale: String,
        pageNumber: String
    ): Flow<DataState<BaseClassIndexNew>> = flow {
        emit(DataState.Success(api.getHomeMatchesData(locale, pageNumber)))
    }

    override suspend fun getPlayersData(): Flow<DataState<Players>> = flow {
        emit(DataState.Success(api.getPlayersLineUp()))
    }
}
