package com.target.deal.domain.repository

import com.target.core.Result
import com.target.core.NetworkFailure
import com.target.deal.domain.model.Deal

interface DealsRepository {
    suspend fun fetchDealsList(): Result<List<Deal>, NetworkFailure>
    suspend fun fetchDealDetails(dealId: Int): Result<Deal, NetworkFailure>
}