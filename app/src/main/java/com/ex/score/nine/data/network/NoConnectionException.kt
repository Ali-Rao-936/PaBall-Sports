package com.ex.score.nine.data.network

import android.content.Context
import com.ex.score.nine.R
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NoConnectionException(private val context: Context): IOException() {
    override val message: String
        get() =context.getString(R.string.no_internet_connection_message)
}

class NoInternetException(private val context: Context) : IOException() {
    override val message: String
        get() = context.getString(R.string.no_internet_active_message)
}

class RequestInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .build()
        return chain.proceed(newRequest)
    }
}