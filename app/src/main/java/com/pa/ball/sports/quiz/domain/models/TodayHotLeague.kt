package com.pa.ball.sports.quiz.domain.models

import com.google.gson.annotations.SerializedName

data class TodayHotLeague(
    @SerializedName("leagueChsShort")
    var leagueChsShort: String = "",
    @SerializedName("leagueEn")
    var leagueEn: String = "",
    @SerializedName("leagueEnShort")
    var leagueEnShort: String = "",
    @SerializedName("leagueId")
    var leagueId: Int = 0,
    @SerializedName("leagueName")
    var leagueName: String = "",
    @SerializedName("leagueNameShort")
    var leagueNameShort: String = ""
)
