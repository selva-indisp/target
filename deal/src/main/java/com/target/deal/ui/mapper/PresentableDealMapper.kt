package com.target.deal.ui.mapper

import com.target.deal.domain.model.Deal
import com.target.deal.ui.model.PresentableDeal
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

class PresentableDealMapper {

    internal fun toPresentableDeals(deals: List<Deal>): PersistentList<PresentableDeal> =
        deals.map (::toPresentableDeal).toPersistentList()

    internal fun toPresentableDeal(deal: Deal): PresentableDeal {
        return PresentableDeal(
            id = deal.id,
            imageUrl = deal.imageUrl,
            title = deal.title,
            description = deal.description,
            availability = deal.availability,
            aisle = deal.aisle.uppercase(),
            fulfillment = deal.fulfillment,
            regularPrice = deal.price.regularPrice,
            salePrice = deal.price.salePrice
        )
    }
}