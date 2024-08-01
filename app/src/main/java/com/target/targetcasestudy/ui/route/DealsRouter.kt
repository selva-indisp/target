package com.target.targetcasestudy.ui.route

import androidx.navigation.NavController

interface DealsRouter {
    fun navigateToDealDetails(dealId: Int)
}

class DealsRouterImpl(
    private val navController: NavController
) : DealsRouter {
    override fun navigateToDealDetails(dealId: Int) {
        navController.navigate("${DealsRoute.DealDetail.PATH}$dealId")
    }

}