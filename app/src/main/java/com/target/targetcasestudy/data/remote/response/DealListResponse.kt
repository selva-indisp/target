package com.target.targetcasestudy.data.remote.response

import com.google.gson.annotations.SerializedName

data class DealListResponse(
  @SerializedName("products")
  val deals: List<DealResponse>?
)