package com.capstone.goloak.model.response

import com.capstone.goloak.model.HomeListHistory
import com.google.gson.annotations.SerializedName


data class HistoryResponse(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("listSelling")
    val listHistory: ArrayList<HomeListHistory>
)
