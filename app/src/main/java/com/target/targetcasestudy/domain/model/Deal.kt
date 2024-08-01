package com.target.targetcasestudy.domain.model

data class Deal (
    val id: Int,
    val title: String,
    val description: String,
    val aisle: String,
    val imageUrl: String,
    val fulfillment: String,
    val price: DealPrice,
    val availability: String
)