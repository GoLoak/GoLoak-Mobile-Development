package com.capstone.goloak.model.response

import android.os.Parcelable
import com.capstone.goloak.model.HomeListTrash
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("fullname")
    val fullName: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("phone_number")
    val phoneNumber: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("point")
    val point: Int? = null,

    @field:SerializedName("listTrash")
    val listTrash: ArrayList<HomeListTrash>
) : Parcelable
