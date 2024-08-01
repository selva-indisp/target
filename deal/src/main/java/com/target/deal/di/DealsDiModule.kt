package com.target.deal.di

import androidx.navigation.NavController
import com.target.core.DefaultDispatcher
import com.target.core.ResourceProvider
import com.target.core.ResourceProviderImpl
import com.target.deal.data.DealsRepositoryImpl
import com.target.deal.data.mapper.DealsDomainMapper
import com.target.deal.data.remote.DealsApi
import com.target.deal.domain.repository.DealsRepository
import com.target.deal.domain.usecase.GetDealDetailsUseCase
import com.target.deal.domain.usecase.GetDealsUseCase
import com.target.deal.ui.DealDetailViewModel
import com.target.deal.ui.DealsListViewModel
import com.target.deal.ui.mapper.PresentableDealMapper
import com.target.deal.ui.route.DealsRouter
import com.target.deal.ui.route.DealsRouterImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dealsDiModule = module {
    viewModel<DealsListViewModel> {
        DealsListViewModel(get(), get(), com.target.core.DefaultDispatcher, get())
    }

    viewModel<DealDetailViewModel> { (dealId: Int) ->
        DealDetailViewModel(get(), get(), dealId, com.target.core.DefaultDispatcher, get())
    }

    factory { GetDealsUseCase(get()) }

    factory { GetDealDetailsUseCase(get()) }

    factory<DealsRepository> { DealsRepositoryImpl(get(), get()) }

    factory { DealsDomainMapper() }

    factory { PresentableDealMapper() }

    factory<DealsRouter> { (navController: NavController) -> DealsRouterImpl(navController) }

    factory<ResourceProvider> { ResourceProviderImpl(get()) }

    single<DealsApi> {
        Retrofit.Builder().baseUrl("https://api.target.com/mobile_case_study_deals/v1/")
            .addConverterFactory(GsonConverterFactory.create()).build().create(DealsApi::class.java)
    }
}