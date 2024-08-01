package com.target.targetcasestudy.data

import com.indisp.core.Result
import com.target.targetcasestudy.core.NetworkFailure
import com.target.targetcasestudy.data.mapper.DealsDomainMapper
import com.target.targetcasestudy.data.remote.DealsApi
import com.target.targetcasestudy.domain.model.Deal
import com.target.targetcasestudy.domain.repository.DealsRepository
import java.io.IOException

class DealsRepositoryImpl (
    private val dealsApi: DealsApi,
    private val mapper: DealsDomainMapper
): DealsRepository {
    override suspend fun fetchDealsList(): Result<List<Deal>, NetworkFailure> {
        return safeApiCall {
            val response = dealsApi.retrieveDeals()
            mapper.toDealList(response)
        }
    }

    override suspend fun fetchDealDetails(dealId: Int): Result<Deal, NetworkFailure> {
        return safeApiCall {
            val response = dealsApi.retrieveDeal(dealId)
            mapper.toDeal(response)
        }
    }

    private suspend fun <T> safeApiCall(callBlock: suspend () -> T): Result<T, NetworkFailure> {
        return try {
            Result.Success(callBlock())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            Result.Error(NetworkFailure.NoInternet)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.Error(NetworkFailure.UnknownFailure(exception))
        }
    }
}