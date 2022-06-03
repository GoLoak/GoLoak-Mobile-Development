package com.capstone.goloak.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "home_list_trash")
@Parcelize
data class HomeListTrash(
    @PrimaryKey
    @field:SerializedName("_id")
    var id: String,

    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("type")
    var type: String,

    @field:SerializedName("description")
    var description: String,

    @field:SerializedName("price")
    var price: Int,

    @field:SerializedName("image")
    var image: String
) : Parcelable
