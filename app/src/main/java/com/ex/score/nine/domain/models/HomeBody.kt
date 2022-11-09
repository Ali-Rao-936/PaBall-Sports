package com.ex.score.nine.domain.models

import com.google.gson.annotations.SerializedName

data class HomeBody(  @SerializedName("start_pk")
                      val PK: String,
                      @SerializedName("start_sk")
                      val SK: String,
                      @SerializedName("user_id")
                      val id: String)
