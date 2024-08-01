package com.target.targetcasestudy.domain.usecase

import com.target.targetcasestudy.domain.repository.DealsRepository

data class GetDealsUseCase (
    private val dealsRepository: DealsRepository
) {
    suspend operator fun invoke() = dealsRepository.fetchDealsList()
}