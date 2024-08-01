package com.target.deal.domain.usecase

import com.target.deal.domain.repository.DealsRepository

data class GetDealDetailsUseCase (
    private val dealsRepository: DealsRepository
) {
    suspend operator fun invoke(dealId: Int) = dealsRepository.fetchDealDetails(dealId)
}