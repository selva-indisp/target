package com.target.deal.domain.usecase

import com.target.deal.domain.repository.DealsRepository

data class GetDealsUseCase (
    private val dealsRepository: DealsRepository
) {
    suspend operator fun invoke() = dealsRepository.fetchDealsList()
}