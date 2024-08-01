package com.target.targetcasestudy.ui.model

data class PresentableDeal(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val availability: String,
    val aisle: String,
    val fulfillment: String,
    val regularPrice: String,
    val salePrice: String
)