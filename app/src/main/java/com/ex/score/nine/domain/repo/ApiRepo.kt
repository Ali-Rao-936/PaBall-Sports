package com.ex.score.nine.domain.repo

import com.ex.score.nine.data.network.DataState
import com.ex.score.nine.domain.models.BaseClassIndexNew
import com.ex.score.nine.domain.models.lineup.Players
import kotlinx.coroutines.flow.Flow

interface ApiRepo {

    suspend fun getHomeMatches(locale : String, pageNumber : String) : Flow<DataState<BaseClassIndexNew>>

    suspend fun getPlayersData() : Flow<DataState<Players>>
}