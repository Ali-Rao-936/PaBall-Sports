package com.pa.ball.sports.quiz.data


import com.pa.ball.sports.quiz.domain.models.BaseClassIndexNew
import com.pa.ball.sports.quiz.domain.models.lineup.Players
import retrofit2.http.*

interface Api {

    @GET("/api/zqbf-list-page/{locale}/{page}")
    suspend fun getHomeMatchesData(@Path("locale") locale: String, @Path("page") pageNumber: String): BaseClassIndexNew

    @GET("/api/zqbf-list-lineup")
    suspend fun getPlayersLineUp() : Players
}