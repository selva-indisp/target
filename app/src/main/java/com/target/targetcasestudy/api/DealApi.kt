package com.target.targetcasestudy.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

internal const val BASE_URL = "https://api.target.com/mobile_case_study_deals/v1/"

interface DealApi {

  @GET("${BASE_URL}deals")
  fun retrieveDeals(): Call<DealResponse>

  @GET("${BASE_URL}deals/{dealId}")
  fun retrieveDeal(@Path("dealId") dealId: String): Call<Deal>
}
