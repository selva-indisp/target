package com.target.targetcasestudy.domain.repository

import com.indisp.core.Result
import com.target.targetcasestudy.core.NetworkFailure
import com.target.targetcasestudy.domain.model.Deal

interface DealsRepository {
    suspend fun fetchDealsList(): Result<List<Deal>, NetworkFailure>
    suspend fun fetchDealDetails(dealId: Int): Result<Deal, NetworkFailure>
}