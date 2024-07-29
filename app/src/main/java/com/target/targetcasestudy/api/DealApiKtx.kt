package com.target.targetcasestudy.api

import retrofit2.http.GET
import retrofit2.http.Path

interface DealApiKtx {

  @GET("${BASE_URL}deals")
  suspend fun retrieveDeals(): DealResponse

  @GET("${BASE_URL}deals/{dealId}")
  suspend fun retrieveDeal(@Path("dealId") dealId: String): Deal
}
