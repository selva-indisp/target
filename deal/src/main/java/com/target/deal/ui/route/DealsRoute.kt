package com.target.deal.ui.route

sealed class DealsRoute(val name: String) {
    object DealsList: DealsRoute("deals")
    object DealDetail: DealsRoute("dealDetail/{dealId}") {
       const val PATH = "dealDetail/"
    }
}