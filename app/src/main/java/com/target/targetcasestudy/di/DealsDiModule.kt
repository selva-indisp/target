package com.target.targetcasestudy.di

import androidx.navigation.NavController
import com.indisp.core.DefaultDispatcher
import com.target.targetcasestudy.data.DealsRepositoryImpl
import com.target.targetcasestudy.data.mapper.DealsDomainMapper
import com.target.targetcasestudy.data.remote.DealsApi
import com.target.targetcasestudy.domain.repository.DealsRepository
import com.target.targetcasestudy.domain.usecase.GetDealDetailsUseCase
import com.target.targetcasestudy.domain.usecase.GetDealsUseCase
import com.target.targetcasestudy.ui.DealDetailViewModel
import com.target.targetcasestudy.ui.DealsListViewModel
import com.target.targetcasestudy.ui.mapper.PresentableDealMapper
import com.target.targetcasestudy.ui.route.DealsRouter
import com.target.targetcasestudy.ui.route.DealsRouterImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dealsDiModule = module {
    viewModel<DealsListViewModel> {
        DealsListViewModel(get(), get(), DefaultDispatcher)
    }

    viewModel<DealDetailViewModel> { (dealId: Int) ->
        DealDetailViewModel(get(), get(), dealId, DefaultDispatcher)
    }

    factory { GetDealsUseCase(get()) }

    factory { GetDealDetailsUseCase(get()) }

    factory<DealsRepository> { DealsRepositoryImpl(get(), get()) }

    factory { DealsDomainMapper() }

    factory { PresentableDealMapper() }

    factory<DealsRouter> { (navController: NavController) -> DealsRouterImpl(navController) }

    single<DealsApi> {
        Retrofit.Builder().baseUrl("https://api.target.com/mobile_case_study_deals/v1/")
            .addConverterFactory(GsonConverterFactory.create()).build().create(DealsApi::class.java)
    }
}