package com.ex.score.nine.data

import com.ex.score.nine.data.network.DataState
import com.ex.score.nine.domain.models.BaseClassIndexNew
import com.ex.score.nine.domain.models.lineup.Players
import com.ex.score.nine.domain.repo.ApiRepo
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
