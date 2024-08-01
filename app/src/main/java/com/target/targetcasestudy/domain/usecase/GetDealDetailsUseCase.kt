package com.target.targetcasestudy.domain.usecase

import com.target.targetcasestudy.domain.repository.DealsRepository

data class GetDealDetailsUseCase (
    private val dealsRepository: DealsRepository
) {
    suspend operator fun invoke(dealId: Int) = dealsRepository.fetchDealDetails(dealId)
}