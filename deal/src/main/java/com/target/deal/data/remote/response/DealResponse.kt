package com.target.deal.data.remote.response

import com.google.gson.annotations.SerializedName

data class DealResponse (
    val id: Int?,
    val title: String?,
    val description: String?,
    val aisle: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("regular_price")
    val regularPrice: DealPriceResponse?,
    @SerializedName("sale_price")
    val salePrice: DealPriceResponse?,
    val fulfillment: String?,
    val availability: String?
)