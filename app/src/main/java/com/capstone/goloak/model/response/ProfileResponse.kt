package com.capstone.goloak.model.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("fullname")
    val fullName: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("phone_number")
    val phoneNumber: String,

    @field:SerializedName("address")
    val address: String
)
