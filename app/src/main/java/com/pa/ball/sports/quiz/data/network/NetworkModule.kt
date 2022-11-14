package com.pa.ball.sports.quiz.data.network

import com.pa.ball.sports.quiz.BuildConfig
import com.pa.ball.sports.quiz.utils.Constants.BaseURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent :: class)
object NetworkModule {



    @Provides
    fun provideOkHttpClient (noInternetConnectionInterceptor: NoInternetConnectionInterceptor): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(RequestInterceptor())
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(noInternetConnectionInterceptor)
        }.build()
    }

    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttp)
            baseUrl(BaseURL)
        }.build()
    }

}