package com.ex.score.nine.data

import com.ex.score.nine.domain.models.BaseClassIndexNew
import com.ex.score.nine.domain.models.lineup.Players
import retrofit2.http.*

interface Api {

    @GET("/api/zqbf-list-page/{locale}/{page}")
    suspend fun getHomeMatchesData(@Path("locale") locale: String, @Path("page") pageNumber: String): BaseClassIndexNew

    @GET("/api/zqbf-list-lineup")
    suspend fun getPlayersLineUp() : Players
}