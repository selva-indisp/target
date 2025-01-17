package com.target.deal.data.mapper

import com.target.deal.data.remote.response.DealListResponse
import com.target.deal.data.remote.response.DealResponse
import com.target.deal.domain.model.Deal
import com.target.deal.domain.model.DealPrice

class DealsDomainMapper {

    fun toDealList(response: DealListResponse): List<Deal> {
        return response.deals?.map(::toDeal) ?: emptyList()
    }

    fun toDeal(response: DealResponse): Deal {
        return Deal(
            id = response.id ?: -1,
            title = response.title.orEmpty(),
            description = response.description.orEmpty(),
            imageUrl = response.imageUrl.orEmpty(),
            fulfillment = response.fulfillment.orEmpty(),
            availability = response.availability.orEmpty(),
            aisle = response.aisle.orEmpty(),
            price = toPrice(response)
        )
    }

    private fun toPrice(response: DealResponse): DealPrice {
        return DealPrice(
            regularPrice = response.regularPrice?.displayString.orEmpty(),
            salePrice = response.salePrice?.displayString.orEmpty(),
        )
    }
}