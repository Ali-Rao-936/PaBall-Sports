package com.pa.ball.sports.quiz.data.network

import com.pa.ball.sports.quiz.domain.models.base.ErrorResponse

sealed class DataState<out T> {
    data class Success<out T>(val value: T) : DataState<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) :
        DataState<Nothing>()
}
