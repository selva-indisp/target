package com.target.deal.data

import com.indisp.core.Result
import com.target.deal.core.NetworkFailure
import com.target.deal.data.mapper.DealsDomainMapper
import com.target.deal.data.remote.DealsApi
import com.target.deal.domain.model.Deal
import com.target.deal.domain.repository.DealsRepository
import java.io.IOException

class DealsRepositoryImpl (
    private val dealsApi: DealsApi,
    private val mapper: com.target.deal.data.mapper.DealsDomainMapper
): DealsRepository {
    override suspend fun fetchDealsList(): Result<List<Deal>, com.target.deal.core.NetworkFailure> {
        return safeApiCall {
            val response = dealsApi.retrieveDeals()
            mapper.toDealList(response)
        }
    }

    override suspend fun fetchDealDetails(dealId: Int): Result<Deal, com.target.deal.core.NetworkFailure> {
        return safeApiCall {
            val response = dealsApi.retrieveDeal(dealId)
            mapper.toDeal(response)
        }
    }

    private suspend fun <T> safeApiCall(callBlock: suspend () -> T): Result<T, com.target.deal.core.NetworkFailure> {
        return try {
            Result.Success(callBlock())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            Result.Error(com.target.deal.core.NetworkFailure.NoInternet)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.Error(com.target.deal.core.NetworkFailure.UnknownFailure(exception))
        }
    }
}