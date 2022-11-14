package com.pa.ball.sports.quiz.data


import com.pa.ball.sports.quiz.domain.repo.ApiRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent :: class)
object ApiModule {


    @Provides
    fun providesApiClass(retrofit: Retrofit) : Api {
      return  retrofit.create(Api::class.java)
    }

    @Provides
    fun providesApiRepository(api: Api) : ApiRepo {
        return ApiRepoImp(api)
    }
}