package com.ex.score.nine.data.network

import com.ex.score.nine.domain.models.base.ErrorResponse

sealed class DataState<out T> {
    data class Success<out T>(val value: T) : DataState<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) : DataState<Nothing>()
}
