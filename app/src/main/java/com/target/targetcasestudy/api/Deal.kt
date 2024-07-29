package com.target.targetcasestudy.api

data class Deal(
  val id: String,

  val title: String,

  val aisle: String,

  val description: String,

  val salePrice: Price
)
