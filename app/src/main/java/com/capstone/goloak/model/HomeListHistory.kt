package com.capstone.goloak.model

import com.google.gson.annotations.SerializedName

data class HomeListHistory(
    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("total_trash")
    val totalTrash: Int,

    @field:SerializedName("nameTrash")
    val nameTrash: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("createAt")
    val createAt: String
)
