package com.ex.score.nine.data

import com.ex.score.nine.data.network.DataState
import com.ex.score.nine.data.network.NetworkHelper
import com.ex.score.nine.domain.models.HomeBody
import com.ex.score.nine.domain.models.VideosResponse
import com.ex.score.nine.domain.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepoImp @Inject constructor(private val api : Api) : ApiRepo {
    override suspend fun getVideosData(body: HomeBody): Flow<DataState<VideosResponse>> = flow {
        emit(DataState.Success(api.getHomeVideos("get-main-stream",body )))
    }
}

class HomeVideosUseCase @Inject constructor(private val repo: ApiRepo) :
    NetworkHelper<HomeVideosUseCase.Params, VideosResponse>() {
    data class Params(val body: HomeBody)
    //  override suspend fun buildUseCase(parameter: Params): Flow<DataState<WrappedResponse<HomeVideosResponse>>> {
    override suspend fun buildUseCase(parameter: Params): Flow<DataState<VideosResponse>> {
        return repo.getVideosData(parameter.body)
    }


}