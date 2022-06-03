package com.capstone.goloak.model.response

import com.capstone.goloak.model.ProfileListPointHistory
import com.google.gson.annotations.SerializedName

data class PointHistoryResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("historyPoint")
    val listPointHistory: ArrayList<ProfileListPointHistory>
)
