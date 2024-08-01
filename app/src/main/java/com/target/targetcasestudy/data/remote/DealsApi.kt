package com.target.targetcasestudy.data.remote

import com.target.targetcasestudy.data.remote.response.DealListResponse
import com.target.targetcasestudy.data.remote.response.DealResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DealsApi {

    @GET("deals")
    suspend fun retrieveDeals(): DealListResponse

    @GET("deals/{dealId}")
    suspend fun retrieveDeal(@Path("dealId") dealId: Int): DealResponse
}
