package com.ex.score.nine.data

import com.ex.score.nine.domain.models.HomeBody
import com.ex.score.nine.domain.models.VideosResponse
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Url

interface Api {

    @PUT
    suspend fun getHomeVideos(
        @Url url: String,
        @Body body: HomeBody
    ) : VideosResponse
}