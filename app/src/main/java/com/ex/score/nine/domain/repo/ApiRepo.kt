package com.ex.score.nine.domain.repo

import com.ex.score.nine.data.network.DataState
import com.ex.score.nine.domain.models.HomeBody
import com.ex.score.nine.domain.models.VideosResponse
import kotlinx.coroutines.flow.Flow

interface ApiRepo {

    suspend fun getVideosData(body: HomeBody) : Flow<DataState<VideosResponse>>
}