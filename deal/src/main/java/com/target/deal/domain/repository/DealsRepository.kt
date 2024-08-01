package com.target.deal.domain.repository

import com.indisp.core.Result
import com.target.deal.core.NetworkFailure
import com.target.deal.domain.model.Deal

interface DealsRepository {
    suspend fun fetchDealsList(): Result<List<Deal>, NetworkFailure>
    suspend fun fetchDealDetails(dealId: Int): Result<Deal, NetworkFailure>
}