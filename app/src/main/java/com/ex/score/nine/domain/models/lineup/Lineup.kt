package com.ex.score.nine.domain.models.lineup

data class Lineup(
    val awayArray: String,
    val awayBackup: List<AwayBackup>,
    val awayLineup: List<AwayLineup>,
    val homeArray: String,
    val homeBackup: List<HomeBackup>,
    val homeLineup: List<HomeLineup>,
    val matchId: Int
)