package com.capstone.goloak.model

import com.google.gson.annotations.SerializedName

data class ProfileListPointHistory(
    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("point_min")
    val pointUsed: Int,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("start_date")
    val startDate: String
)
